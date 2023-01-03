package com.example.food_manager.ui.finances

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import com.example.food_manager.R
import com.example.food_manager.ui.expense.list.ExpenseList

class Finances : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finances)
        val expensesLayout = findViewById<LinearLayout>(R.id.expense)
        expensesLayout.setOnClickListener {
            val intent = Intent(this, ExpenseList::class.java)
            startActivity(intent)
        }
    }
}