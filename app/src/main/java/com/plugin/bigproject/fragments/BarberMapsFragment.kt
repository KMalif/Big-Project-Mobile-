package com.plugin.bigproject.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapbox.maps.Style
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
        _binding = FragmentBarberMapsBinding.inflate(inflater, container, false)
        barberMapSetup()
        return binding.root
    }
    private fun barberMapSetup(){
        binding.BarberMap.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }


}