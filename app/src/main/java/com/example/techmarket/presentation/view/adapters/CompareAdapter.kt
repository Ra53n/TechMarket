package com.example.techmarket.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.R
import com.example.techmarket.data.entities.Item

class CompareAdapter(private val controller: Controller) :
    RecyclerView.Adapter<CompareAdapter.CompareViewHolder>() {

    interface Controller {
        fun onItemClick(item: Item)
        fun onDeleteItemClick(item: Item)
    }

    private var data: List<Item> = listOf()

    fun setItems(data: List<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CompareViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.compare_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: CompareViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CompareViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item) {
            with(itemView) {
                findViewById<TextView>(R.id.compare_recycler_view_item_description).text =
                    item.description
                findViewById<ImageView>(R.id.compare_recycler_view_item_image).load(item.imageUrl)
                findViewById<TextView>(R.id.compare_recycler_view_item_price).text =
                    context.resources.getString(R.string.price, item.price.toString())
                findViewById<AppCompatButton>(R.id.compare_recycler_view_item_remove).setOnClickListener {
                    controller.onDeleteItemClick(
                        item
                    )
                }
            }
        }
    }
}