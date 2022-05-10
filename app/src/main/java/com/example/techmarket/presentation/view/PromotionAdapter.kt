package com.example.techmarket.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.R
import com.example.techmarket.data.Promotion

class PromotionAdapter(val navigateToWeb: (url: String) -> Unit) :
    RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {
    private var data: List<Promotion> = listOf()

    fun setItems(data: List<Promotion>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PromotionViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.image_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class PromotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(promo: Promotion) {
            with(itemView) {
                findViewById<ImageView>(R.id.image).load(promo.imageUrl)
                setOnClickListener { navigateToWeb(promo.sourceUrl) }
            }
        }
    }
}