package com.example.techmarket.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techmarket.R
import com.example.techmarket.model.Item

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>() {
    private var data: List<Item> = listOf()

    fun setItems(data: List<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainFragmentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item) {
            with(itemView) {
                findViewById<TextView>(R.id.main_recycler_view_item_rating).text =
                    item.rating.toString()
                findViewById<TextView>(R.id.main_recycler_view_item_description).text =
                    item.description
                findViewById<TextView>(R.id.main_recycler_view_item_price).text =
                    (item.price.toString() + " ₽")
            }
        }
    }
}