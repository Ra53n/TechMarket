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

class LikeAdapter(private val controller: Controller) :
    RecyclerView.Adapter<LikeAdapter.LikeViewHolder>() {

    interface Controller {
        fun onItemClick(item: Item)
        fun onDeleteClick(item: Item)
    }

    private var data: List<Item> = listOf()

    fun setItems(data: List<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LikeViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.like_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class LikeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item) {
            itemView.findViewById<TextView>(R.id.like_recycler_view_item_description).text =
                item.description
            itemView.findViewById<ImageView>(R.id.like_recycler_view_item_image)
                .load(item.imageUrl)
            itemView.findViewById<TextView>(R.id.like_recycler_view_item_price).text =
                item.price.toString()
            itemView.findViewById<Button>(R.id.like_recycler_view_item_unlike)
                .setOnClickListener { controller.onDeleteClick(item) }
        }
    }
}