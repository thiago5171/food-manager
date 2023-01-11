package com.example.food_manager.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_manager.R
import com.example.food_manager.data.dao.IncomeDAO
import com.example.food_manager.domain.Income
import com.example.food_manager.ui.income.IncomeEditForm
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomesAdapter(private val incomes: ArrayList<Income>, private val incomesDAO: IncomeDAO) :
 RecyclerView.Adapter<IncomesAdapter.ViewHolder>(){
     class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val nameView: TextView
         val descriptionView: TextView
         val amountView: TextView
         val incomeImageView: ImageView
         val deleteButton: ImageView

         init {
             nameView = view.findViewById(R.id.income_name)
             descriptionView = view.findViewById(R.id.income_description)
             amountView = view.findViewById(R.id.income_amount)
             incomeImageView = view.findViewById(R.id.income_image)
             deleteButton = view.findViewById(R.id.income_delete_button)
         }
     }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_single_income, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameView.text = incomes[position].name
        viewHolder.descriptionView.text = incomes[position].description
        viewHolder.amountView.text = "R$ ${incomes[position].amount}"
        Glide.with(viewHolder.incomeImageView.context).
            load(Uri.parse(incomes[position].imgUri)).
                into(viewHolder.incomeImageView)

        viewHolder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, IncomeEditForm::class.java)
            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    val  income = incomesDAO.findByID(incomes[position].id)
                    intent.putExtra("income", income)
                    view.context.startActivity(intent)
                }
            }
        }

        viewHolder.deleteButton.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(viewHolder.itemView.context)
            builder.setTitle(R.string.delete_confirmation)
            builder.setPositiveButton(R.string.confirm) { dialog, _ ->
                val scope = MainScope()
                scope.launch {
                    withContext(Dispatchers.IO) {
                        incomesDAO.deleteOne(incomes[position])
                        incomes.clear()
                        incomes.addAll(incomesDAO.findAll())
                    }
                }
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, incomes.size)
                dialog.dismiss()
            }
            builder.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            builder.create().show()
        }
    }

    override fun getItemCount() = incomes.size
}