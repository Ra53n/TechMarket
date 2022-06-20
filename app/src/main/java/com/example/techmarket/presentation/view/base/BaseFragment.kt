package com.example.techmarket.presentation.view.base

import android.widget.ProgressBar
import android.widget.Toast
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

    fun itemAddedToCart(){
        Toast.makeText(context,"Товар был добавлен в крозину!",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        hideProgressBar()
    }
}