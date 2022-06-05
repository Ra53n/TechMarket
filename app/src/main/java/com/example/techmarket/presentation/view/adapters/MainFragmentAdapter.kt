package com.example.techmarket.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.R
import com.example.techmarket.data.entities.Item

class MainFragmentAdapter(private val controller: Controller) :
    RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>() {

    interface Controller {
        fun onLikeItem(item: Item)
        fun onItemClick(item: Item)
        fun addToCart(item: Item)
        fun isItemLiked(item: Item): Boolean
    }

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
                findViewById<ImageView>(R.id.main_recycler_view_item_image).load(item.imageUrl)
                findViewById<TextView>(R.id.main_recycler_view_item_description).text =
                    item.description
                findViewById<TextView>(R.id.main_recycler_view_item_price).text =
                    (item.price.toString() + " ₽")
                with(findViewById<ImageView>(R.id.main_recycler_view_item_like)) {
                    setOnClickListener {
                        controller.onLikeItem(item)
                        changeItemToLiked(itemView)
                    }
                    if (controller.isItemLiked(item)) {
                        changeItemToLiked(itemView)
                    }
                }
                findViewById<Button>(R.id.main_recycler_view_item_add_to_cart).setOnClickListener {
                    controller.addToCart(
                        item
                    )
                }
                setOnClickListener { controller.onItemClick(item) }
            }
        }
    }

    private fun changeItemToLiked(view: View) {
        with(view.findViewById<ImageView>(R.id.main_recycler_view_item_like)) {
            setImageDrawable(
                resources.getDrawable(R.drawable.ic_baseline_favorite_24)
            )
            setColorFilter(resources.getColor(R.color.pink_light))
        }
    }
}