package com.plugin.bigproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugin.bigproject.R
import com.plugin.bigproject.adapters.BookingHistoryAdapter
import com.plugin.bigproject.contracts.FragmentBookingHistoryContract
import com.plugin.bigproject.databinding.FragmentBookinghistoryBinding
import com.plugin.bigproject.models.BookHistory
import com.plugin.bigproject.presenters.FragmentBookingHistoryPresenter
import com.plugin.bigproject.util.Constants


class BookinghistoryFragment : Fragment(), FragmentBookingHistoryContract.View {

    private lateinit var bookingHistoryAdapter: BookingHistoryAdapter
    private var presenter : FragmentBookingHistoryContract.Presenter? = null
    private var _binding : FragmentBookinghistoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookinghistoryBinding.inflate(inflater, container, false)
        presenter = FragmentBookingHistoryPresenter(this)
        getHistoryBooking()
        return binding.root
    }

    private fun getHistoryBooking(){
        val token = Constants.getToken(requireContext())
        presenter?.getBookingHistory(token)
    }

    override fun attachBookingHistory(listHistory: List<BookHistory>) {
        binding.RvHistory.apply {
            bookingHistoryAdapter = BookingHistoryAdapter(listHistory)
            layoutManager = LinearLayoutManager(activity)
            adapter = bookingHistoryAdapter
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