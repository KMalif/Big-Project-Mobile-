package com.plugin.bigproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.plugin.bigproject.R
import com.plugin.bigproject.contracts.DetailUserFragmentContract
import com.plugin.bigproject.databinding.FragmentDetailuserBinding
import com.plugin.bigproject.models.Profile
import com.plugin.bigproject.presenters.FragmentDetailUserPresenter
import com.plugin.bigproject.util.Constants


class DetailuserFragment : Fragment(), DetailUserFragmentContract.View {

    private var presenter : DetailUserFragmentContract.Presenter? = null
    private var _binding : FragmentDetailuserBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailuserBinding.inflate(inflater,container, false)
        presenter = FragmentDetailUserPresenter(this)
        getProfile()
        return binding.root
    }

    private fun getProfile(){
        val token = Constants.getToken(requireContext())
        presenter?.getUser(token)
    }

    override fun attachDetailtoView(profile: Profile) {
        binding.apply {
            TvName.text = profile.nama_user
            Tvuser.text = profile.username
            TvEmail.text = profile.email
            TvHp.text = profile.no_hp
            TvGender.text = profile.gender
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        binding.loading.apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loading.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }


}