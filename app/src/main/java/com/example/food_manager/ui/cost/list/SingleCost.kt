package com.example.food_manager.ui.cost.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivitySingleCostBinding

class SingleCost : AppCompatActivity() {
    private val binding by lazy {
        ActivitySingleCostBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}