package com.example.techmarket.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.R
import com.example.techmarket.data.Item
import com.example.techmarket.databinding.AddItemFragmentBinding
import com.example.techmarket.databinding.CatalogRecyclerViewItemBinding

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var data: List<Item> = listOf()

    fun setItems(data: List<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.catalog_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class FilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item) {
            itemView.findViewById<TextView>(R.id.catalog_recycler_view_item_description).text = item.description
            itemView.findViewById<ImageView>(R.id.catalog_recycler_view_item_image).load(item.imageUrl)
            itemView.findViewById<TextView>(R.id.catalog_recycler_view_item_price).text = item.price.toString()
        }
    }
}