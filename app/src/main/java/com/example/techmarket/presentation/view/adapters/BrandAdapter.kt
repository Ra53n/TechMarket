package com.example.techmarket.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.R

class BrandAdapter :
    RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {
    private var data: List<String> = listOf()

    fun setItems(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BrandViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.brand_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class BrandViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(promo: String) {
            with(itemView) {
                findViewById<ImageView>(R.id.image).load(promo)
            }
        }
    }
}