package com.example.everything.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everything.R
import com.example.everything.data.ResponseData
import com.example.everything.databinding.FragmentHomeBinding
import com.example.everything.util.collectInLifecycle
import com.example.everything.util.hideKeyboard
import com.example.everything.util.openBrowser
import com.example.everything.util.showToast
import com.example.everything.viewmodels.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)

        val adapter = NewsAdapter(false, ::openBrowser, viewModel::addDb)

        binding.recyclerView.adapter = adapter

        val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        binding.recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (!viewModel.isLoading) {
                val visibleItemCount: Int = linearLayoutManager.childCount
                val totalItemCount: Int = linearLayoutManager.itemCount
                val pastVisibleItems: Int = linearLayoutManager.findFirstVisibleItemPosition()
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    viewModel.search(viewModel.lastQ, viewModel.lastPageNumber + 1, false)
                }
            }
        }

        viewModel.searchResult.collectInLifecycle(viewLifecycleOwner) {
            adapter.submitList(it) {
                viewModel.isLoading = false
            }
        }

        viewModel.searchError.collectInLifecycle(viewLifecycleOwner) {
            when (it) {
                is ResponseData.FailedResponse.NoInternet -> {
                    showToast(R.string.no_internet)
                }

                is ResponseData.FailedResponse.UnexpectedError -> {
                    it.error
                    showToast(R.string.unexpected_error)
                }
            }
        }

        viewModel.favoriteError.collectInLifecycle(viewLifecycleOwner) {
            showToast(R.string.is_already_favorite)
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.search(query)
                    hideKeyboard()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }
}