package com.example.techmarket.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.techmarket.R
import com.example.techmarket.data.Category

class CategoryAdapter :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var data: List<Category> = listOf()

    fun setItems(data: List<Category>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.category_recycler_view_item, parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(category: Category) {
            with(itemView.findViewById<ImageView>(R.id.image)) {
                when (category) {
                    Category.Computers -> setImageResource(R.drawable.ic_baseline_computer_24)
                    Category.Smartphones -> setImageResource(R.drawable.ic_baseline_phone_iphone_24)
                    Category.Appliances -> setImageResource(R.drawable.ic_baseline_local_laundry_service_24)
                    Category.OfficeEquipment -> setImageResource(R.drawable.ic_baseline_local_printshop_24)
                }
            }
        }
    }
}