package com.example.food_manager.ui.income.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivityFinancialIncomeListBinding

class FinancialIncomeList : AppCompatActivity() {
    private val binding by lazy {
        ActivityFinancialIncomeListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}