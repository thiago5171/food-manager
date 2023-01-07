package com.example.food_manager.ui.expense.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityExpenseListBinding
import com.example.food_manager.domain.Expense.Expense
import com.example.food_manager.ui.adapter.ExpensesAdapter
import com.example.food_manager.ui.expense.ExpenseRegisterForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseList : AppCompatActivity() {
    private val binding by lazy {
        ActivityExpenseListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DatabaseHelper.getInstance(this)
        val dao = db.expenseDAO()
        val scope = MainScope()

        val list = binding.expenseList
        scope.launch {
            val expenses = withContext(Dispatchers.IO) {
                dao.findAll()
            }

            val expensesList = ArrayList<Expense>()
            expensesList.addAll(expenses)
            val adapter = ExpensesAdapter(expensesList, dao)

            list.layoutManager = GridLayoutManager(
                this@ExpenseList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }

        val createButton = binding.createExpense
        createButton.setOnClickListener{
            val intent = Intent(this, ExpenseRegisterForm::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val db = DatabaseHelper.getInstance(this)
        val dao = db.expenseDAO()
        val scope = MainScope()

        val list = binding.expenseList
        scope.launch {
            val expenses = withContext(Dispatchers.IO) {
                dao.findAll()
            }

            val expenseList = ArrayList<Expense>()
            expenseList.addAll(expenses)
            val adapter = ExpensesAdapter(expenseList, dao)

            list.layoutManager = GridLayoutManager(
                this@ExpenseList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }
    }
}