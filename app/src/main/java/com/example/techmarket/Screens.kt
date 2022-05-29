package com.example.techmarket

import com.example.techmarket.data.entities.Category
import com.example.techmarket.data.entities.Item
import com.example.techmarket.presentation.view.addItem.AddItemFragment
import com.example.techmarket.presentation.view.authorization.ControllerHolder
import com.example.techmarket.presentation.view.authorization.login.LoginFragment
import com.example.techmarket.presentation.view.authorization.registration.RegistrationFragment
import com.example.techmarket.presentation.view.cart.CartFragment
import com.example.techmarket.presentation.view.catalog.CatalogFragment
import com.example.techmarket.presentation.view.compare.CompareFragment
import com.example.techmarket.presentation.view.details.DetailsFragment
import com.example.techmarket.presentation.view.filter.FilterFragment
import com.example.techmarket.presentation.view.like.LikeFragment
import com.example.techmarket.presentation.view.main.MainFragment
import com.example.techmarket.presentation.view.profile.ProfileFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    private val controllerHolder = ControllerHolder()

    fun main() = FragmentScreen {
        MainFragment.newInstance()
    }

    fun cart() = FragmentScreen {
        CartFragment.newInstance()
    }

    fun profile() = FragmentScreen {
        ProfileFragment.newInstance()
    }

    fun login() = FragmentScreen {
        LoginFragment.newInstance(controllerHolder)
    }

    fun registration() = FragmentScreen {
        RegistrationFragment.newInstance(controllerHolder)
    }

    fun add() = FragmentScreen {
        AddItemFragment.newInstance()
    }

    fun catalog() = FragmentScreen {
        CatalogFragment.newInstance()
    }

    fun filter(category: Category) = FragmentScreen {
        FilterFragment.newInstance(category)
    }

    fun like() = FragmentScreen {
        LikeFragment.newInstance()
    }

    fun details(item: Item) = FragmentScreen {
        DetailsFragment.newInstance(item)
    }

    fun compare() = FragmentScreen{
        CompareFragment.newInstance()
    }
}