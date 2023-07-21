package com.plugin.bigproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugin.bigproject.activities.DetailWishlistActivity
import com.plugin.bigproject.adapters.HistoryAdapter
import com.plugin.bigproject.adapters.WishlistListener
import com.plugin.bigproject.contracts.FragmentHistoryContract
import com.plugin.bigproject.databinding.FragmentHistoryBinding
import com.plugin.bigproject.models.History
import com.plugin.bigproject.presenters.FragmentHistoryPresenter
import com.plugin.bigproject.util.Constants
import java.io.Serializable

class HistoryFragment : Fragment(), FragmentHistoryContract.View {
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var presenter : FragmentHistoryContract.Presenter? = null
    private lateinit var historyAdapter: HistoryAdapter
    private var listHistories: List<History> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = FragmentHistoryPresenter(this)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getHistory(){
        val token = Constants.getToken(requireActivity())
        presenter?.getHistory(token)
    }

    override fun attachHistoryToRecycler(listHistory: List<History>) {
        this.listHistories = listHistory
        binding.RvHistory.apply {
            historyAdapter = HistoryAdapter(listHistory, object : WishlistListener{
                override fun onWishlistCLick(wishlist: History) {
                    startActivity(Intent(activity, DetailWishlistActivity::class.java).apply {
                        putExtra("wishlist", wishlist as Serializable)
                    })
                }

                override fun deleteWishlist(id: Int) {
                    val token = Constants.getToken(requireActivity())
                    presenter?.deleteWishlist(token, id)
                }
            })
            layoutManager = LinearLayoutManager(activity)
            adapter = historyAdapter
            historyAdapter.updateData(listHistory)
        }
    }

    override fun showLoading() {
        binding.shimmerHistory.apply {
            visibility = View.VISIBLE
            showShimmer(true)
            startShimmer()
        }
    }

    override fun hideLoading() {
        binding.shimmerHistory.apply {
            stopShimmer()
            hideShimmer()
            visibility = View.GONE
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemDelete(wishlist: History) {
        listHistories = listHistories.filterNot { it.id == wishlist.id }
        historyAdapter.updateData(listHistories)
    }


    override fun showEmpty() {
        binding.WrapEmpty.apply {
            visibility = View.VISIBLE
        }
    }

    override fun hideEmpty() {
        binding.WrapEmpty.apply {
            visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        getHistory()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}