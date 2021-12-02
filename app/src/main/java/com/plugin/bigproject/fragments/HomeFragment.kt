package com.plugin.bigproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugin.bigproject.R
import com.plugin.bigproject.activities.DetailPartnerActivity
import com.plugin.bigproject.contracts.FragmentHomeContract
import com.plugin.bigproject.databinding.FragmentHomeBinding
import com.plugin.bigproject.models.HairCuts
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.presenters.FragmentHomePresenter


class HomeFragment : Fragment(), FragmentHomeContract.FragmentHomeView {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var presenter : FragmentHomeContract.FragmentHomePresenter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        detailPartner()
        return binding.root
    }

    private fun detailPartner(){
        binding.TvMitra.setOnClickListener {
            startActivity(Intent(activity, DetailPartnerActivity::class.java))
        }
    }

    override fun attachTrendToRecycler(listTrend: List<HairCuts>) {

    }

    override fun attachMitraToRecycler(listMitra: List<Partners>) {

    }

    override fun showLoading() {
        binding.loading.apply {
            isIndeterminate = true
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