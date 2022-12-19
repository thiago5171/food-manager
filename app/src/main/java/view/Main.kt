package view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.food_manager.R
import view.expense.ExpenseRegisterForm

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val expenseLayout = findViewById<LinearLayout>(R.id.expenses)
        expenseLayout.setOnClickListener {
            val intent = Intent(this, ExpenseRegisterForm::class.java)
            startActivity(intent)
        }
    }
}