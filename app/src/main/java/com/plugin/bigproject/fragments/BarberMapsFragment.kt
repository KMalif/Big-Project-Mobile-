package com.plugin.bigproject.fragments

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.plugin.bigproject.R
import com.plugin.bigproject.databinding.FragmentBarberMapsBinding

class BarberMapsFragment : Fragment() {

    private var _binding : FragmentBarberMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(requireActivity(), getString(R.string.mapbox_access_token))
        _binding = FragmentBarberMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync {
            it.setStyle(Style.TRAFFIC_DAY)

            val location = LatLng(-6.868707,109.106704)
            val barber = LatLng(-6.8777395,109.1141334)
            val position = CameraPosition.Builder()
                .target(LatLng(-6.868707,109.106704))
                .zoom(12.0)
                .tilt(4.5)
                .bearing(7.3)
                .build()

            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000)
            it.addMarker(MarkerOptions().setPosition(location).title("You"))
            it.addMarker(MarkerOptions().setPosition(barber).title("Jodi's Barbershop"))
            it.setOnMarkerClickListener(object : MapboxMap.OnMarkerClickListener{
                override fun onMarkerClick(marker: Marker): Boolean {
                    val location: Uri = Uri.parse("google.navigation:q=${marker.position.latitude}.${marker.position.longitude}&mode=d")
                    val mapIntent = Intent(Intent.ACTION_VIEW, location)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("Arahkan ke ${marker.title}")
                    builder.setMessage("Apakah kamu yakin ?")

                    builder.setPositiveButton("Ya") { dialog, which ->
                        try {
                            startActivity(mapIntent)
                        } catch (e: ActivityNotFoundException) {
                            println(e.message)
                        }
                    }
                    builder.setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                    builder.show()
                    return true
                }
            })
        }
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Navigate")
        builder.setMessage("Are you sure ?")

        builder.setPositiveButton("Yes") { dialog, which ->

        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
    }


}