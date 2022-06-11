package com.example.techmarket.presentation.view.adapters

import android.content.Context
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.R
import com.example.techmarket.data.cartDb.CartItemEntity
import com.example.techmarket.data.entities.Item
import com.example.techmarket.data.entities.User
import com.example.techmarket.data.mappers.EntityItemsMapper
import toothpick.Toothpick
import javax.inject.Inject

class CartAdapter @Inject constructor(private val controller: Controller) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    init {
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE))
    }

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var mapper: EntityItemsMapper

    interface Controller {
        fun onItemClick(item: Item)
        fun onDeleteClick(item: Item)
        fun onChangesCountClick(item: Item, increase: Boolean)
        fun onSellerChangesClick(item: Item, seller: User, price: String)
    }

    private var data: List<CartItemEntity> = listOf()

    fun setItems(data: List<CartItemEntity>) {
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
        fun bind(item: CartItemEntity) {
            itemView.findViewById<TextView>(R.id.cart_recycler_view_item_description).text =
                item.description

            itemView.findViewById<ImageView>(R.id.cart_recycler_view_item_image)
                .load(item.imageUrl)

            itemView.findViewById<TextView>(R.id.cart_recycler_view_item_price).text =
                item.price.toString()

            itemView.findViewById<Button>(R.id.cart_recycler_view_item_remove)
                .setOnClickListener { controller.onDeleteClick(mapper.convertCartItemToItem(item)) }

            itemView.findViewById<EditText>(R.id.cart_recycler_view_item_count)
                .text = Editable.Factory.getInstance().newEditable(item.count.toString())

            itemView.findViewById<Button>(R.id.cart_recycler_view_item_increase_count)
                .setOnClickListener {
                    controller.onChangesCountClick(
                        mapper.convertCartItemToItem(
                            item
                        ), true
                    )
                }

            itemView.findViewById<Button>(R.id.cart_recycler_view_item_decrease_count)
                .setOnClickListener {
                    controller.onChangesCountClick(
                        mapper.convertCartItemToItem(
                            item
                        ), false
                    )
                }

            val sellerClickListener = View.OnClickListener { v ->
                val popupMenu = PopupMenu(context, v, Gravity.CENTER)
                val array = item.sellers.values.toList().map { "${it.user?.name} - ${it.price}₽" }
                for (seller in array) {
                    popupMenu.menu.add(seller)
                }
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    val position = array.indexOf(menuItem.title)
                    val user = item.sellers.values.toList()[position].user
                    val price = item.sellers.values.toList()[position].price
                    controller.onSellerChangesClick(
                        mapper.convertCartItemToItem(item),
                        user!!,
                        price
                    )
                    true
                }
                popupMenu.show()
            }
            item.selectedSeller?.name?.let {
                itemView.findViewById<TextView>(R.id.cart_recycler_view_item_seller).text = it
            }
            itemView.findViewById<TextView>(R.id.cart_recycler_view_item_seller)
                .setOnClickListener { sellerClickListener.onClick(it) }
        }
    }
}