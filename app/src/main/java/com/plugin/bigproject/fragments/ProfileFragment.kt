package com.plugin.bigproject.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.plugin.bigproject.R
import com.plugin.bigproject.activities.EditProfilesActivity
import com.plugin.bigproject.activities.LoginActivity
import com.plugin.bigproject.databinding.FragmentProfileBinding
import com.plugin.bigproject.util.Constants


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        editProfiles()
        showAlertDialogue()
        return binding.root
    }

    private fun logout(){
        Constants.clearName(requireActivity())
        Constants.clearToken(requireActivity())
        startActivity(Intent(activity, LoginActivity::class.java))
    }

    private fun showAlertDialogue(){
        binding.BtnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure ?")

            builder.setPositiveButton("Yes") { dialog, which ->
                logout()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        }
    }

    private fun editProfiles(){
     binding.icEdit.setOnClickListener {
         startActivity(Intent(activity, EditProfilesActivity::class.java))
     }
    }

}