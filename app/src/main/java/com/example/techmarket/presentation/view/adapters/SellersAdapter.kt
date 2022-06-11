package com.example.techmarket.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.techmarket.R
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.entities.UserPricePair

class SellersAdapter(private val item: Item, private val controller: Controller) :
    RecyclerView.Adapter<SellersAdapter.SellerViewHolder>() {
    private var data: Map<String, UserPricePair> = mapOf()

    interface Controller {
        fun addToCart(item: Item, user: User, price: String)
    }

    fun setItems(data: Map<String, UserPricePair>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SellerViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.seller_item, parent, false)
    )

    override fun onBindViewHolder(holder: SellerViewHolder, position: Int) {
        val entry = data.entries.toList()[position]
        entry.value.user?.let { holder.bind(entry.value.price, it) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class SellerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(price: String, user: User) {
            itemView.findViewById<TextView>(R.id.seller_item_seller_name).text = user.name
            itemView.findViewById<TextView>(R.id.seller_item_seller_price).text = price
            itemView.findViewById<AppCompatImageView>(R.id.seller_item_buy)
                .setOnClickListener { controller.addToCart(item, user, price) }
        }
    }
}