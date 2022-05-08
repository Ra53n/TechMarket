package com.example.techmarket.presentation.view

import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment() {

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