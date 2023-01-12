package com.example.food_manager.ui.graphs

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityIncomesVsExpensesBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomesVsExpenses : AppCompatActivity() {
    private val binding by lazy {
        ActivityIncomesVsExpensesBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DatabaseHelper.getInstance(this)

        val expensesDAO = db.expenseDAO()
        val incomesDAO = db.incomeDAO()

        val scope = MainScope()
        scope.launch {
            var incomesSum = 0.0
            var expensesSum = 0.0

            val incomeList = withContext(Dispatchers.IO) {
                incomesDAO.findAll()
            }
            for (income in incomeList) {
                incomesSum += income.amount
            }

            val expenseList = withContext(Dispatchers.IO) {
                expensesDAO.findAll()
            }
            for (expense in expenseList) {
                expensesSum += expense.price
            }

            val chart = binding.incomesVsExpensesGraph

            chart.setUsePercentValues(true)
            chart.description.isEnabled = false
            chart.setExtraOffsets(5f, 10f, 5f, 5f)
            chart.dragDecelerationFrictionCoef = 0.95f
            chart.isDrawHoleEnabled = true
            chart.setHoleColor(Color.WHITE)
            chart.setTransparentCircleColor(Color.WHITE)
            chart.setTransparentCircleAlpha(110)
            chart.holeRadius = 58f
            chart.transparentCircleRadius = 61f
            chart.setDrawCenterText(true)
            chart.rotationAngle = 0f
            chart.isRotationEnabled = true
            chart.isHighlightPerTapEnabled = true
            chart.animateY(1400, Easing.EaseInOutQuad)
            chart.legend.isEnabled = false
            chart.setEntryLabelColor(Color.WHITE)
            chart.setEntryLabelTextSize(12f)

            val entries = ArrayList<PieEntry>()
            entries.add(PieEntry(incomesSum.toFloat()))
            entries.add(PieEntry(expensesSum.toFloat()))

            val dataSet = PieDataSet(entries, getString(R.string.incomes_vs_expenses))
            dataSet.setDrawIcons(false)
            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f

            val colors = arrayListOf(Color.CYAN, Color.RED)
            dataSet.colors = colors

            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(15f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.WHITE)

            chart.data = data

            chart.highlightValues(null)
            chart.invalidate()
        }
    }
}