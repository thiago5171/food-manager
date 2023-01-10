package com.example.food_manager.ui.income.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityFinancialIncomeListBinding
import com.example.food_manager.domain.Income
import com.example.food_manager.ui.adapter.IncomesAdapter
import com.example.food_manager.ui.income.IncomeRegisterForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FinancialIncomeList : AppCompatActivity() {
    private val binding by lazy {
        ActivityFinancialIncomeListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dao = DatabaseHelper.getInstance(this).incomeDAO()
        val scope = MainScope()

        val list = binding.incomesList
        scope.launch {
            val incomes = withContext(Dispatchers.IO) {
                dao.findAll()
            }

            val incomeList = ArrayList<Income>()
            incomeList.addAll(incomes)
            val adapter = IncomesAdapter(incomeList, dao)

            list.layoutManager = GridLayoutManager(
                this@FinancialIncomeList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }

        val addIncomeButton = binding.createIncome
        addIncomeButton.setOnClickListener {
            val intent = Intent(this, IncomeRegisterForm::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val dao = DatabaseHelper.getInstance(this).incomeDAO()
        val scope = MainScope()

        val list = binding.incomesList
        scope.launch {
            val incomes = withContext(Dispatchers.IO) {
                dao.findAll()
            }

            val incomeList = ArrayList<Income>()
            incomeList.addAll(incomes)
            val adapter = IncomesAdapter(incomeList, dao)

            list.layoutManager = GridLayoutManager(
                this@FinancialIncomeList, GridLayoutManager.VERTICAL)
            list.adapter = adapter
        }
    }
}