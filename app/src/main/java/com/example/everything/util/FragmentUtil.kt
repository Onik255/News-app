package com.example.everything.util

import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.everything.R

fun Fragment.hideKeyboard() {
    requireActivity().getSystemService(InputMethodManager::class.java)
        ?.hideSoftInputFromWindow(requireView().windowToken, 0)
}

fun Fragment.openBrowser(url: String) {
    if (url.startsWith("http://") || url.startsWith("https://")) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    } else {
        showToast(R.string.wrong_url)
    }
}

fun Fragment.showToast(@IdRes textId: Int) = Toast.makeText(requireContext(), textId, Toast.LENGTH_SHORT).show()