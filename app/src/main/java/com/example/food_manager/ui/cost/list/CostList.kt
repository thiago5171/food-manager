package com.example.food_manager.ui.cost.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivityCostListBinding

class CostList : AppCompatActivity() {
    private val binding by lazy {
        ActivityCostListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}