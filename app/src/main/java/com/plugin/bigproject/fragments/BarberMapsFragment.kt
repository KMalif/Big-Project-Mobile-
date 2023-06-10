package com.plugin.bigproject.fragments

import android.Manifest
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.plugin.bigproject.R
import com.plugin.bigproject.databinding.FragmentBarberMapsBinding
import com.plugin.bigproject.util.*
import java.util.*
import kotlin.collections.ArrayList

class BarberMapsFragment : Fragment() {

    private lateinit var discover: Discover
    private lateinit var locationEngine: LocationEngine
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mapMarkersManager: MapMarkersManager


    private var _binding: FragmentBarberMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarberMapsBinding.inflate(inflater, container, false)
        setupMap()
        return binding.root

    }

    private fun setupMap(){
        discover = Discover.create(getString(R.string.mapbox_access_token))
        locationEngine = LocationEngineProvider.getBestLocationEngine(requireActivity())

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
            locationEngine.lastKnownLocation(requireActivity()) { location ->
                if (location == null) {
                    return@lastKnownLocation
                }
                val query = DiscoverQuery.Category.create("rumah_sakit")
                val newlocation = Point.fromLngLat(38.92249177571266, -77.02489150281114)
                lifecycleScope.launchWhenStarted {
                    val response = discover.search(
                        query = DiscoverQuery.Category.COFFEE_SHOP_CAFE,
                        proximity = location,
                        options = DiscoverOptions(limit = 10)
                    )

                    response.onValue { results ->
                        mapMarkersManager.showResults(results)
                        println("Result ${results}")
                    }.onError { e ->
                        Log.d("DiscoverApiExample", "Error happened during search request", e)
                    }
                }
            }
        }

//        binding.searchThisArea.setOnClickListener {
//            lifecycleScope.launchWhenStarted {
//                val response = discover.search(
//                    query = DiscoverQuery.Category.COFFEE_SHOP_CAFE,
//                    region = mapboxMap.getCameraBoundingBox(),
//                    options = DiscoverOptions(limit = 20)
//                )
//
//                response.onValue { results ->
//                    mapMarkersManager.showResults(results)
//                    println("Result area ${results}")
//                }.onError { e ->
//                    Log.d("DiscoverApiExample", "Error happened during search request", e)
//                }
//            }
//        }
        binding.searchPlaceView.apply {
            initialize(CommonSearchViewConfiguration(DistanceUnitType.IMPERIAL))
            isFavoriteButtonVisible = false
            addOnCloseClickListener {
                mapMarkersManager.adjustMarkersForClosedCard()
                hide()
            }
        }

        mapMarkersManager.onResultClickListener = { result ->
            mapMarkersManager.adjustMarkersForOpenCard()
            binding.searchPlaceView.open(result.toSearchPlace())
            locationEngine.userDistanceTo(requireActivity(), result.coordinate) { distance ->
                distance?.let { binding.searchPlaceView.updateDistance(distance) }
            }
        }

        if (!requireContext().isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                requireActivity(),
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