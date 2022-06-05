package com.example.techmarket.presentation.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.R
import com.example.techmarket.Screens.cart
import com.example.techmarket.Screens.catalog
import com.example.techmarket.Screens.like
import com.example.techmarket.Screens.main
import com.example.techmarket.Screens.profile
import com.example.techmarket.databinding.MainActivityBinding
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationBarView
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private var navigator: Navigator = AppNavigator(this, R.id.container)

    init {
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE))
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    router.replaceScreen(main())
                    return@OnItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    router.replaceScreen(cart())
                    return@OnItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    router.replaceScreen(profile())
                    return@OnItemSelectedListener true
                }
                R.id.navigation_likes -> {
                    router.replaceScreen(like())
                    return@OnItemSelectedListener true
                }

                R.id.navigation_catalog -> {
                    router.replaceScreen(catalog())
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
        router.newRootScreen(main())
    }

    private fun bindBottomNavigationView() {
        binding.mainActivityNavView.apply {
            setOnItemSelectedListener(
                mOnNavigationItemSelectedListener
            )
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        router.exit()
    }

}