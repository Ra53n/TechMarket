package com.example.techmarket.presentation.view.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.R
import com.example.techmarket.data.entities.Item

class CartAdapter(private val controller: Controller) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    interface Controller {
        fun onItemClick(item: Item)
        fun onDeleteClick(item: Item)
        fun onChangesCountClick(item: Item, increase: Boolean)
    }

    private var data: List<Item> = listOf()

    fun setItems(data: List<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item) {
            itemView.findViewById<TextView>(R.id.cart_recycler_view_item_description).text =
                item.description

            itemView.findViewById<ImageView>(R.id.cart_recycler_view_item_image)
                .load(item.imageUrl)

            itemView.findViewById<TextView>(R.id.cart_recycler_view_item_price).text =
                item.price.toString()

            itemView.findViewById<Button>(R.id.cart_recycler_view_item_remove)
                .setOnClickListener { controller.onDeleteClick(item) }

            itemView.findViewById<EditText>(R.id.cart_recycler_view_item_count)
                .text = Editable.Factory.getInstance().newEditable(item.count.toString())

            itemView.findViewById<Button>(R.id.cart_recycler_view_item_increase_count)
                .setOnClickListener { controller.onChangesCountClick(item, true) }

            itemView.findViewById<Button>(R.id.cart_recycler_view_item_decrease_count)
                .setOnClickListener { controller.onChangesCountClick(item,false)}

        }
    }
}