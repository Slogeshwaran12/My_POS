package com.example.mypos

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var purchaseButton: Button
    private lateinit var clearButton: Button
    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var totalCostTextView: TextView  // New

    private val cartItems = mutableListOf<FoodItem>()
    private val selectedCartItems = mutableSetOf<FoodItem>()

    private lateinit var cartAdapter: CartAdapter
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cartRecyclerView = findViewById(R.id.cart_recycler_view)
        purchaseButton = findViewById(R.id.purchase_button)
        clearButton = findViewById(R.id.clear_button)
        foodRecyclerView = findViewById(R.id.recycler_view)
        totalCostTextView = findViewById(R.id.total_cost_text_view)  // Initialize

        val foodList = listOf(
            FoodItem("Pizza", "Cheesy and delicious", 10.0),
            FoodItem("Burger", "With lettuce and tomato", 7.5),
            FoodItem("Pasta", "Italian creamy pasta", 8.0),
            FoodItem("Sushi", "Fresh salmon rolls", 12.0),
            FoodItem("Salad", "Green and healthy", 6.0),
            FoodItem("Fries", "Crispy and golden", 4.0),
            FoodItem("Tacos", "Spicy and crunchy", 5.5),
            FoodItem("Ice Cream", "Cold and sweet", 3.0),
            FoodItem("Steak", "Grilled to perfection", 15.0),
            FoodItem("Sandwich", "Ham and cheese special", 6.5),
            FoodItem("Noodles", "Hot and savory", 7.0),
            FoodItem("Dumplings", "Steamed and soft", 5.0),
            FoodItem("Paneer Tikka", "Indian grilled cheese", 9.0),
            FoodItem("Chicken Wings", "Hot & spicy delight", 11.0),
            FoodItem("Falafel", "Middle Eastern favorite", 6.5)
        )

        foodAdapter = FoodAdapter(foodList) { selectedItem ->
            cartItems.add(selectedItem)
            updateCartDisplay()
        }

        foodRecyclerView.layoutManager = LinearLayoutManager(this)
        foodRecyclerView.adapter = foodAdapter

        cartAdapter = CartAdapter(cartItems, selectedCartItems)
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = cartAdapter

        purchaseButton.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                showPurchaseDialog()
            } else {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            if (selectedCartItems.isNotEmpty()) {
                cartItems.removeAll(selectedCartItems)
                selectedCartItems.clear()
                updateCartDisplay()
                Toast.makeText(this, "Selected items removed from cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No items selected to remove", Toast.LENGTH_SHORT).show()
            }
        }

        updateCartDisplay()  // initial update for total cost
    }

    private fun showPurchaseDialog() {
        AlertDialog.Builder(this)
            .setTitle("Order Placed")
            .setMessage("Your order has been placed successfully!\nTotal: ${getTotalCostString()}")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun updateCartDisplay() {
        cartAdapter.notifyDataSetChanged()
        updateTotalCost()
    }

    private fun updateTotalCost() {
        totalCostTextView.text = "Total: ${getTotalCostString()}"
    }

    private fun getTotalCostString(): String {
        val total = cartItems.sumOf { it.price }
        return "$%.2f".format(total)
    }
}
