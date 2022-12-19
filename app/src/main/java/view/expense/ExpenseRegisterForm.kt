package view.expense

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.food_manager.R

class ExpenseRegisterForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_register_form)
        val addExpenseButtom = findViewById<Button>(R.id.add_expense_btn)
        addExpenseButtom.setOnClickListener {
            val intent = Intent(this, ExpenseRegisterForm::class.java)
            startActivity(intent)
        }
    }
}