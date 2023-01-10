package com.example.food_manager.ui.expense

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityExpenseRegisterFormBinding
import com.example.food_manager.domain.Expense.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseRegisterForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityExpenseRegisterFormBinding.inflate(layoutInflater)
    }

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pickImageButton = binding.pickExpenseImageAction

        pickImageButton.setOnClickListener {
            pickImage()
            pickImageButton.text = ""
        }

        val saveExpenseButton = binding.saveExpenseBtn
        saveExpenseButton.setOnClickListener {
            saveExpense()
        }
    }

    private fun pickImage() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            getContent.launch("image/*")
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getContent.launch("image/*")
            }
        }
    }

    private fun saveExpense() {
        val name = binding.registerExpenseNameEdit.text.toString()
        val description = binding.registerExpenseDescriptionEdit.text.toString()
        val price = binding.registerExpensePriceEdit.text.toString().replace(',', '.').toDouble()

        val expense = Expense(
            name = name,
            description = description,
            price = price,
            imgUri = imageUri.toString()
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