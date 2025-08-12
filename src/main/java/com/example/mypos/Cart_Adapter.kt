package com.example.mypos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: List<FoodItem>,
    private val selectedItems: MutableSet<FoodItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox: CheckBox = itemView.findViewById(R.id.cart_item_checkbox)
        val name: TextView = itemView.findViewById(R.id.cart_food_name)
        val price: TextView = itemView.findViewById(R.id.cart_food_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.name.text = item.name
        holder.price.text = "$${item.price}"

        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = selectedItems.contains(item)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selectedItems.add(item) else selectedItems.remove(item)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
