package com.example.food_manager.ui.graphs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.databinding.ActivityGraphsBinding

class Graphs : AppCompatActivity() {
    private val binding by lazy {
        ActivityGraphsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val incomesVsExpensesLayout = binding.incomesVsExpenses
        incomesVsExpensesLayout.setOnClickListener {
            val intent = Intent(this, IncomesVsExpenses::class.java)
            startActivity(intent)
        }
    }
}