package com.plugin.bigproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugin.bigproject.R
import com.plugin.bigproject.activities.EditProfilesActivity
import com.plugin.bigproject.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        editProfiles()
        return binding.root
    }

    private fun editProfiles(){
     binding.icEdit.setOnClickListener {
         startActivity(Intent(activity, EditProfilesActivity::class.java))
     }
    }

}