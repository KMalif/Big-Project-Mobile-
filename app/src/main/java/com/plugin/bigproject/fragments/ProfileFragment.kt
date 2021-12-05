package com.plugin.bigproject.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.plugin.bigproject.activities.EditProfilesActivity
import com.plugin.bigproject.activities.LoginActivity
import com.plugin.bigproject.contracts.FragmentProfileContract
import com.plugin.bigproject.databinding.FragmentProfileBinding
import com.plugin.bigproject.models.User
import com.plugin.bigproject.presenters.FragmentProfilePresenter
import com.plugin.bigproject.util.Constants


class ProfileFragment : Fragment(), FragmentProfileContract.View {

    private var presenter : FragmentProfileContract.Presenter? = null
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        showAlertDialogue()
        presenter = FragmentProfilePresenter(this)
        return binding.root
    }

    private fun logout(){
        Constants.clearName(requireActivity())
        Constants.clearToken(requireActivity())
        Constants.clearId(requireActivity())
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

    private fun editProfiles(user: User){
     binding.icEdit.setOnClickListener {
         startActivity(Intent(activity, EditProfilesActivity::class.java).apply {
             putExtra("Name", user.name)
             putExtra("Username", user.username)
             putExtra("Email", user.email)
         })
     }
    }

    override fun showProfiletoView(user: User) {
        binding.apply {
            TvName.text = user.name
            TvEmail.text = user.email
            TvUsername.text = user.username
        }
        editProfiles(user)
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun getProfile() {
        val id = Constants.getId(requireActivity())
        presenter?.getUserById(id)
    }

    override fun onResume() {
        super.onResume()
        getProfile()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}