package com.example.food_manager.ui.finances

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.food_manager.databinding.ActivityFinancesBinding
import com.example.food_manager.ui.cost.list.CostList
import com.example.food_manager.ui.expense.list.ExpenseList
import com.example.food_manager.ui.income.list.FinancialIncomeList

class Finances : AppCompatActivity() {
    private val binding by lazy {
        ActivityFinancesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val expensesLayout = binding.expense
        expensesLayout.setOnClickListener {
            val intent = Intent(this, ExpenseList::class.java)
            startActivity(intent)
        }

        val incomesLayout = binding.income
        incomesLayout.setOnClickListener {
            val intent = Intent(this, FinancialIncomeList::class.java)
            startActivity(intent)
        }

        val costsLayout = binding.cost
        costsLayout.setOnClickListener {
            val intent = Intent(this, CostList::class.java)
            startActivity(intent)
        }
    }
}