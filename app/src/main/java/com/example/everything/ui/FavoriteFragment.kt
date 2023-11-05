package com.example.everything.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.everything.R
import com.example.everything.databinding.FragmentFavoriteBinding
import com.example.everything.util.collectInLifecycle
import com.example.everything.util.openBrowser
import com.example.everything.viewmodels.FavoriteViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoriteBinding.bind(view)

        val adapter = NewsAdapter(true, ::openBrowser, viewModel::deleteFromDb)
        binding.recyclerView.adapter = adapter
        viewModel.favorites.collectInLifecycle(viewLifecycleOwner) { list ->
            adapter.submitList(list.map { it })
        }
    }
}