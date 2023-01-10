package com.example.food_manager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_manager.R
import com.example.food_manager.data.dao.ExpenseDAO
import com.example.food_manager.domain.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpensesAdapter(private val expenses: ArrayList<Expense>, private val expensesDAO: ExpenseDAO) :
    RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val priceView: TextView
        val descriptionView: TextView
        val deleteButton: ImageView

        init {
            nameView = view.findViewById(R.id.expense_name)
            priceView = view.findViewById(R.id.expense_price)
            descriptionView = view.findViewById(R.id.expense_description)
            deleteButton = view.findViewById(R.id.expense_delete_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_single_expense, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameView.text = expenses[position].name
        viewHolder.priceView.text = "R$${expenses[position].price}"
        viewHolder.descriptionView.text = expenses[position].description

        viewHolder.deleteButton.setOnClickListener {
            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    expensesDAO.deleteOne(expenses[position])
                    expenses.clear()
                    expenses.addAll(expensesDAO.findAll())
                }
            }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
        }
    }

    override fun getItemCount() = expenses.size


}