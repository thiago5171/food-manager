package com.example.food_manager.ui.income.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivitySingleIncomeBinding

class SingleIncome : AppCompatActivity() {
    private val binding by lazy {
        ActivitySingleIncomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}