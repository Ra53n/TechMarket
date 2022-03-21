package com.example.techmarket.view

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private var progressBar: ProgressBar? = null

    fun setProgressBar(bar: ProgressBar) {
        progressBar = bar
    }

    fun showProgressBar() {
        progressBar?.visibility = ProgressBar.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = ProgressBar.GONE
    }

    override fun onStop() {
        super.onStop()
        hideProgressBar()
    }
}