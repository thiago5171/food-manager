package view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.food_manager.R
import view.food_technical_sheet.RegisterForm

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.redirect_button)
        button.setOnClickListener {
            val intent = Intent(this, RegisterForm::class.java)
            startActivity(intent)
        }
    }
}