package com.example.food_manager.view.expense.list

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.food_manager.R
import com.example.food_manager.domain.ExpenseItemListModel
import com.example.food_manager.domain.adapter.ExpenseListAdapter
import com.example.food_manager.view.expense.ExpenseRegisterForm
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpenseList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)
        val expenses = ArrayList<ExpenseItemListModel>()
        val url1 = "https://s2.glbimg.com/PGe2eC7ZZDf83YQVbHlSo5yfgiI=/0x0:3024x4032/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2020/O/u/yyfpMvSKADtaARRrXWBg/img-20200724-180122364.jpg"
        val url2 = "https://s2.glbimg.com/gga75FAW8NWvei7UJimH9FPUTAA=/0x0:600x638/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2021/A/T/WV0SPZQberBnGTiORIPA/fatura-cemig.jpg"
        val url3 = "https://i.ytimg.com/vi/VK3MdRPF34Q/maxresdefault.jpg"
        expenses.add(
            ExpenseItemListModel(
                "Gás de cozinha",
                "Consumo de 2 gases de cozinha por mês",
                120.00,
                url1

            )
        )
        expenses.add(
            ExpenseItemListModel(
                "Conta de luz",
                "Uso muitos eletrodomésticos para cozinhar",
                259.88,
                url2
            )
        )
        expenses.add(
            ExpenseItemListModel(
                "Feira do mês",
                "Todos os ingredientes que comprei no início do mês",
                187.22,
                url3
            )
        )

        val adapter = ExpenseListAdapter(applicationContext, expenses)
        val list = findViewById<ListView>(R.id.expenseListItem)

        list.adapter = adapter
        val createButton = findViewById<FloatingActionButton>(R.id.createExpense)
        createButton.setOnClickListener{
            val intent = Intent(this, ExpenseRegisterForm::class.java)
            startActivity(intent)

        }
    }
}