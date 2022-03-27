package com.example.techmarket.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.techmarket.R
import com.example.techmarket.databinding.MainActivityBinding
import com.example.techmarket.view.cart.CartFragment
import com.example.techmarket.view.main.MainFragment
import com.example.techmarket.view.profile.ProfileFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    val fragment = MainFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    val fragment = CartFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindBottomNavigationView()
    }

    private fun bindBottomNavigationView() {
        binding.mainActivityNavView.apply {
            setOnItemSelectedListener(
                mOnNavigationItemSelectedListener
            )
        }
    }

}