package com.plugin.bigproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugin.bigproject.R
import com.plugin.bigproject.activities.DetailNewsActivity
import com.plugin.bigproject.adapters.NewsAdapter
import com.plugin.bigproject.adapters.NewsListener
import com.plugin.bigproject.contracts.FragmentNewsContract
import com.plugin.bigproject.databinding.FragmentNewsBinding
import com.plugin.bigproject.models.News
import com.plugin.bigproject.presenters.FragmentNewsPresenter
import com.plugin.bigproject.util.Constants


class NewsFragment : Fragment(), FragmentNewsContract.View {

    private lateinit var newsAdapter : NewsAdapter
    private var presenter : FragmentNewsContract.Presenter? = null
    private var _binding : FragmentNewsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        presenter = FragmentNewsPresenter(this)
        return binding.root
    }

    private fun getNews(){
        val token = Constants.getToken(requireActivity())
        presenter?.getNews(token)
    }

    override fun attachNewsToRecycler(listNews: List<News>) {
        binding.RVNews.apply {
            newsAdapter = NewsAdapter(listNews, object : NewsListener{
                override fun onNewsClick(news: News) {
                    startActivity(Intent(activity, DetailNewsActivity::class.java).apply {
                        putExtra("idNews", news.id_news)
                    })
                }
            })

            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

    override fun showLoading() {
        binding.shimmerNews.apply{
            visibility = View.VISIBLE
            showShimmer(true)
            startShimmer()
        }
    }

    override fun hideLoading() {
        binding.shimmerNews.apply {
            stopShimmer()
            hideShimmer()
            visibility = View.GONE
        }
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

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        getNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }


}