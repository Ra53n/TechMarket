package com.example.techmarket.presentation.view.catalog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.R
import com.example.techmarket.Screens.filter
import com.example.techmarket.data.Category
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class CatalogClickListenerHolder(private val context: Context) {

    init {
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE))
    }

    @Inject
    lateinit var router: Router

    val phonesClickListener = View.OnClickListener { v ->
        val popupMenu = PopupMenu(context, v, Gravity.CENTER)
        val array = context.resources.getStringArray(R.array.phones_and_gadgets)
        for (category in array) {
            popupMenu.menu.add(category)
        }
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.title.toString()){
                Category.Smartphones.category -> {
                    router.navigateTo(filter(Category.Smartphones))
                }

                Category.Tablets.category -> {
                    router.navigateTo(filter(Category.Tablets))
                }

                Category.Phototechnics.category -> {
                    router.navigateTo(filter(Category.Phototechnics))
                }
            }
            true
        }
        popupMenu.show()
    }

    val computersClickListener = View.OnClickListener { v ->
        val popupMenu = PopupMenu(context, v, Gravity.CENTER)
        val array = context.resources.getStringArray(R.array.computers)
        for (category in array) {
            popupMenu.menu.add(category)
        }
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.title.toString()){
                Category.Computers.category -> {
                    router.navigateTo(filter(Category.Computers))
                }

                Category.Processors.category -> {
                    router.navigateTo(filter(Category.Processors))
                }

                Category.GraphicsCards.category -> {
                    router.navigateTo(filter(Category.GraphicsCards))
                }
            }
            true
        }
        popupMenu.show()
    }

    val appliancesClickListener = View.OnClickListener { v ->
        val popupMenu = PopupMenu(context, v, Gravity.CENTER)
        val array = context.resources.getStringArray(R.array.appliances)
        for (category in array) {
            popupMenu.menu.add(category)
        }
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.title.toString()){
                Category.WashingMachines.category -> {
                    router.navigateTo(filter(Category.WashingMachines))
                }

                Category.Irons.category -> {
                    router.navigateTo(filter(Category.Irons))
                }

                Category.Hairdryer.category -> {
                    router.navigateTo(filter(Category.Hairdryer))
                }
            }
            true
        }
        popupMenu.show()
    }

    val officeClickListener = View.OnClickListener { v ->
        val popupMenu = PopupMenu(context, v, Gravity.CENTER)
        val array = context.resources.getStringArray(R.array.office)
        for (category in array) {
            popupMenu.menu.add(category)
        }
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.title.toString()){
                Category.Printers.category -> {
                    router.navigateTo(filter(Category.Printers))
                }
            }
            true
        }
        popupMenu.show()
    }
}