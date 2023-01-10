package com.example.food_manager.ui.expense

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityExpenseRegisterFormBinding
import com.example.food_manager.domain.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseRegisterForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityExpenseRegisterFormBinding.inflate(layoutInflater)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val saveExpenseButton = binding.saveExpenseBtn
        saveExpenseButton.setOnClickListener {
            saveExpense()
        }

        val cancelButton = binding.cancelExpenseRegisterBtn
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun saveExpense() {
        val name = binding.registerExpenseNameEdit.text.toString()
        val description = binding.registerExpenseDescriptionEdit.text.toString()
        val price = binding.registerExpensePriceEdit.text.toString().replace(',', '.').toDouble()

        val expense = Expense(
            name = name,
            description = description,
            price = price
        )

        if (expenseIsValid(expense)) {
            val dao = DatabaseHelper.getInstance(this).expenseDAO()

            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    dao.save(expense)
                }
            }

            Toast.makeText(this, "salvo com sucesso", Toast.LENGTH_SHORT).show()

            finish()
        } else {
            Toast.makeText(this, "algo deu errado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun expenseIsValid(expense: Expense): Boolean {
        return expense.name.isNotBlank() && expense.price > 0
    }
}