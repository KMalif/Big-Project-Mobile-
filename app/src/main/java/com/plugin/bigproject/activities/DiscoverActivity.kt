package com.plugin.bigproject.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.search.discover.*
import com.mapbox.search.result.SearchAddress
import com.mapbox.search.result.SearchResultType
import com.mapbox.search.ui.view.CommonSearchViewConfiguration
import com.mapbox.search.ui.view.DistanceUnitType
import com.mapbox.search.ui.view.place.SearchPlace
import com.mapbox.search.ui.view.place.SearchPlaceBottomSheetView
import com.plugin.bigproject.R
import com.plugin.bigproject.databinding.ActivityDiscoverBinding
import com.plugin.bigproject.util.*
import java.util.*
import kotlin.collections.ArrayList

class DiscoverActivity : AppCompatActivity() {

    private lateinit var discover: Discover
    private lateinit var locationEngine: LocationEngine

    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mapMarkersManager: MapMarkersManager

    private lateinit var searchPlaceView: SearchPlaceBottomSheetView

    private lateinit var binding: ActivityDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        discover = Discover.create(getString(R.string.mapbox_access_token))
        locationEngine = LocationEngineProvider.getBestLocationEngine(applicationContext)

        mapMarkersManager = MapMarkersManager(binding.mapView)
        binding.mapView.getMapboxMap().also { mapboxMap ->
            this.mapboxMap = mapboxMap

            mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
                binding.mapView.location.updateSettings {
                    enabled = true
                }

                binding.mapView.location.addOnIndicatorPositionChangedListener(object :
                    OnIndicatorPositionChangedListener {
                    override fun onIndicatorPositionChanged(point: Point) {
                        binding.mapView.getMapboxMap().setCamera(
                            CameraOptions.Builder()
                                .center(point)
                                .zoom(14.0)
                                .build()
                        )

                        binding.mapView.location.removeOnIndicatorPositionChangedListener(this)
                    }
                })
            }
        }


        binding.searchNearby.setOnClickListener {
            locationEngine.lastKnownLocation(this) { location ->
                if (location == null) {
                    return@lastKnownLocation
                }

                lifecycleScope.launchWhenStarted {
                    val response = discover.search(
                        query = DiscoverQuery.Category.COFFEE_SHOP_CAFE,
                        proximity = location,
                        options = DiscoverOptions(limit = 20)
                    )

                    response.onValue { results ->
                        mapMarkersManager.showResults(results)
                    }.onError { e ->
                        Log.d("DiscoverApiExample", "Error happened during search request", e)
                    }
                }
            }
        }


        binding.searchThisArea.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                val response = discover.search(
                    query = DiscoverQuery.Category.COFFEE_SHOP_CAFE,
                    region = mapboxMap.getCameraBoundingBox(),
                    options = DiscoverOptions(limit = 20)
                )

                response.onValue { results ->
                    mapMarkersManager.showResults(results)
                }.onError { e ->
                    Log.d("DiscoverApiExample", "Error happened during search request", e)
                }
            }
        }

        binding.searchPlaceView.apply {
            initialize(CommonSearchViewConfiguration(DistanceUnitType.IMPERIAL))
            isFavoriteButtonVisible = false
            addOnCloseClickListener {
                mapMarkersManager.adjustMarkersForClosedCard()
                binding.searchPlaceView.hide()
            }
        }

        mapMarkersManager.onResultClickListener = {
            mapMarkersManager.adjustMarkersForOpenCard()
            searchPlaceView.open(it.toSearchPlace())
            locationEngine.userDistanceTo(this@DiscoverActivity, it.coordinate) { distance ->
                distance?.let { searchPlaceView.updateDistance(distance) }
            }
        }

        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    private class MapMarkersManager(mapView: MapView) {

        private val annotations = mutableMapOf<Long, DiscoverResult>()
        private val mapboxMap: MapboxMap = mapView.getMapboxMap()
        private val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(null)
        private val pinBitmap = mapView.context.bitmapFromDrawableRes(R.drawable.red_marker)

        var onResultClickListener: ((DiscoverResult) -> Unit)? = null

        init {
            pointAnnotationManager.addClickListener {
                annotations[it.id]?.let { result ->
                    onResultClickListener?.invoke(result)
                }
                true
            }
        }

        fun clearMarkers() {
            pointAnnotationManager.deleteAll()
            annotations.clear()
        }

        fun adjustMarkersForOpenCard() {
            val coordinates = annotations.values.map { it.coordinate }
            val cameraOptions = mapboxMap.cameraForCoordinates(
                coordinates, MARKERS_INSETS_OPEN_CARD, bearing = null, pitch = null
            )
            mapboxMap.setCamera(cameraOptions)
        }

        fun adjustMarkersForClosedCard() {
            val coordinates = annotations.values.map { it.coordinate }
            val cameraOptions = mapboxMap.cameraForCoordinates(
                coordinates, MARKERS_INSETS, bearing = null, pitch = null
            )
            mapboxMap.setCamera(cameraOptions)
        }

        fun showResults(results: List<DiscoverResult>) {
            clearMarkers()
            if (results.isEmpty() || pinBitmap == null) {
                return
            }

            val coordinates = ArrayList<Point>(results.size)
            results.forEach { result ->
                val options = PointAnnotationOptions()
                    .withPoint(result.coordinate)
                    .withIconImage(pinBitmap)
                    .withIconAnchor(IconAnchor.BOTTOM)

                val annotation = pointAnnotationManager.create(options)
                annotations[annotation.id] = result
                coordinates.add(result.coordinate)
            }

            val cameraOptions = mapboxMap.cameraForCoordinates(
                coordinates, MARKERS_INSETS, bearing = null, pitch = null
            )
            mapboxMap.setCamera(cameraOptions)
        }
    }

    private companion object {

        const val PERMISSIONS_REQUEST_LOCATION = 0

        val MARKERS_BOTTOM_OFFSET = dpToPx(176).toDouble()
        val MARKERS_EDGE_OFFSET = dpToPx(64).toDouble()
        val PLACE_CARD_HEIGHT = dpToPx(300).toDouble()

        val MARKERS_INSETS = EdgeInsets(
            MARKERS_EDGE_OFFSET, MARKERS_EDGE_OFFSET, MARKERS_BOTTOM_OFFSET, MARKERS_EDGE_OFFSET
        )

        val MARKERS_INSETS_OPEN_CARD = EdgeInsets(
            MARKERS_EDGE_OFFSET, MARKERS_EDGE_OFFSET, PLACE_CARD_HEIGHT, MARKERS_EDGE_OFFSET
        )

        fun DiscoverAddress.toSearchAddress(): SearchAddress {
            return SearchAddress(
                houseNumber = houseNumber,
                street = street,
                neighborhood = neighborhood,
                locality = locality,
                postcode = postcode,
                place = place,
                district = district,
                region = region,
                country = country
            )
        }

        fun DiscoverResult.toSearchPlace(): SearchPlace {
            return SearchPlace(
                id = name + UUID.randomUUID().toString(),
                name = name,
                descriptionText = null,
                address = address.toSearchAddress(),
                resultTypes = listOf(SearchResultType.POI),
                record = null,
                coordinate = coordinate,
                routablePoints = routablePoints,
                categories = categories,
                makiIcon = makiIcon,
                metadata = null,
                distanceMeters = null,
                feedback = null,
            )
        }
    }
}