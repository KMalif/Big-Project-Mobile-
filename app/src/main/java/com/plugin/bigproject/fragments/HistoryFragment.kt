package com.plugin.bigproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugin.bigproject.R
import com.plugin.bigproject.contracts.FragmentHistoryContract
import com.plugin.bigproject.contracts.FragmentNewsContract
import com.plugin.bigproject.databinding.FragmentHistoryBinding
import com.plugin.bigproject.databinding.FragmentHomeBinding
import com.plugin.bigproject.models.News
import com.plugin.bigproject.presenters.FragmentHistoryPresenter

class HistoryFragment : Fragment(), FragmentNewsContract.View {
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var presenter : FragmentHistoryContract.Presenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun attachNewsToRecycler(listNews: List<News>) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showToast(message: String) {
        TODO("Not yet implemented")
    }

}