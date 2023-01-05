package com.example.food_manager.ui.expense.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.food_manager.databinding.ActivitySingleExpenseBinding

class SingleExpense : AppCompatActivity() {
    private val binding by lazy {
        ActivitySingleExpenseBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}