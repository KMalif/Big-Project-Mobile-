package com.plugin.bigproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugin.bigproject.activities.ChatbotActivity
import com.plugin.bigproject.activities.DetailPartnerActivity
import com.plugin.bigproject.adapters.HaircutsAdapter
import com.plugin.bigproject.adapters.PartnersAdapter
import com.plugin.bigproject.adapters.PartnersListener
import com.plugin.bigproject.contracts.FragmentHomeContract
import com.plugin.bigproject.databinding.FragmentHomeBinding
import com.plugin.bigproject.models.HairCuts
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.presenters.FragmentHomePresenter
import com.plugin.bigproject.util.Constants


class HomeFragment : Fragment(), FragmentHomeContract.FragmentHomeView {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var presenter : FragmentHomeContract.FragmentHomePresenter? = null
    private lateinit var partnerAdapter : PartnersAdapter
    private lateinit var haircutsAdapter: HaircutsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setName()
        fabChatbotlistener()
        presenter = FragmentHomePresenter(this)
        return binding.root
    }


    private fun fabChatbotlistener(){
        binding.fabChat.setOnClickListener { startActivity(Intent(activity, ChatbotActivity::class.java)) }
    }

    private fun setName(){
        val name = Constants.getName(requireActivity())
        binding.TvName.text = name
    }

    private fun getData(){
        val token = Constants.getToken(requireActivity())
        presenter?.getMitra(token)
        presenter?.getHaircuts(token)
    }

    override fun attachHaircutToRecycler(haircuts: List<HairCuts>) {
        binding.RvHaircuts.apply{
            haircutsAdapter = HaircutsAdapter(haircuts)
            val mLayout = LinearLayoutManager(activity)
            mLayout.orientation = LinearLayoutManager.HORIZONTAL
            adapter = haircutsAdapter
            layoutManager = mLayout
        }
    }

    override fun attachMitraToRecycler(listMitra: List<Partners>) {
        println("Mitra $listMitra")
        binding.RvPartners.apply {
            partnerAdapter = PartnersAdapter(listMitra, object : PartnersListener{
                override fun onParnerClick(partners: Partners) {
                    startActivity(Intent(activity, DetailPartnerActivity::class.java).apply {
                        putExtra("idPartner", partners.id_mitra)
                    })
                }
            })
            val mlayoutManager = GridLayoutManager(activity, 2)
            mlayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = mlayoutManager
            adapter = partnerAdapter
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
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

    override fun onResume() {
        super.onResume()
        getData()
    }

}