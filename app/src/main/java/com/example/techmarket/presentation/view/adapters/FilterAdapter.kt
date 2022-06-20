package com.example.techmarket.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.R
import com.example.techmarket.data.entities.Item

class FilterAdapter(private val controller: Controller) :
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    interface Controller {
        fun likeItem(item: Item)
        fun deleteItemFromCompare(item: Item)
        fun addItemToCompare(item: Item)
        fun isItemContainsCompare(item: Item): Boolean
        fun isItemLiked(item: Item): Boolean
        fun onItemClick(item: Item)
    }

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
            itemView.findViewById<TextView>(R.id.catalog_recycler_view_item_description).text =
                item.description
            itemView.findViewById<ImageView>(R.id.catalog_recycler_view_item_image)
                .load(item.imageUrl)
            itemView.findViewById<AppCompatTextView>(R.id.catalog_recycler_view_item_rating).text =
                if (item.rating.isNotEmpty())
                    item.rating.values.average().toString() else "Н/Р"
            itemView.findViewById<TextView>(R.id.catalog_recycler_view_item_price).text =
                item.price.toString()
            with(itemView.findViewById<AppCompatImageView>(R.id.catalog_recycler_view_item_like)) {
                changeColorToLikedItem(item)
                setOnClickListener {
                    controller.likeItem(item)
                    changeColorToLikedItem(item)
                }
            }
            itemView.setOnClickListener { controller.onItemClick(item) }
            with(itemView.findViewById<CheckBox>(R.id.catalog_recycler_view_item_compare)) {
                isChecked = controller.isItemContainsCompare(item)
                setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        controller.addItemToCompare(item)
                    } else {
                        controller.deleteItemFromCompare(item)
                    }
                }
            }
        }

        private fun changeColorToLikedItem(item: Item) {
            with(itemView.findViewById<AppCompatImageView>(R.id.catalog_recycler_view_item_like)) {
                if (controller.isItemLiked(item)) {
                    setColorFilter(resources.getColor(R.color.pink_light))
                } else {
                    setColorFilter(resources.getColor(R.color.gray))
                }
            }
        }
    }
}