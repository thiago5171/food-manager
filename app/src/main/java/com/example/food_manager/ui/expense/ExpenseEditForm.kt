package com.example.food_manager.ui.expense

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityExpenseRegisterFormBinding
import com.example.food_manager.domain.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseEditForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityExpenseRegisterFormBinding.inflate(layoutInflater)
    }

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = binding.registerExpenseTitle
        title.text = getString(R.string.edit_expense)

        val expense = intent.getSerializableExtra("expense") as Expense?

        fillUpExpenseOnView(expense)

        val pickImageButton = binding.pickExpenseImageAction
        pickImageButton.setOnClickListener {
            pickImage()
        }

        applySelectedStateToPickImageView(pickImageButton)

        val saveExpenseButton = binding.saveExpenseBtn
        saveExpenseButton.setOnClickListener {
            expense?.id?.let { id -> editExpense(id) }
        }

        val cancelButton = binding.cancelExpenseRegisterBtn
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun fillUpExpenseOnView(expense: Expense?) {
        if (expense == null) {
            finish()
        }

        val nameEdit = binding.registerExpenseNameEdit
        val descriptionEdit = binding.registerExpenseDescriptionEdit
        val priceEdit = binding.registerExpensePriceEdit

        nameEdit.setText(expense?.name)
        descriptionEdit.setText(expense?.description)
        priceEdit.setText(expense?.price.toString())
        imageUri = Uri.parse(expense?.imgUri)
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

    private fun applySelectedStateToPickImageView(pickImageButton: TextView) {
        val newIcon = AppCompatResources.getDrawable(this,
            R.drawable.ic_baseline_check_circle_outline_24)
        pickImageButton.setCompoundDrawablesWithIntrinsicBounds(null, null,
            newIcon, null)
        pickImageButton.text = getString(R.string.image_was_selected)
        pickImageButton.setTextColor(
            AppCompatResources.getColorStateList(this,
                R.color.trendingStart))
    }

    private fun editExpense(id: Long) {
        val name = binding.registerExpenseNameEdit.text.toString()
        val description = binding.registerExpenseDescriptionEdit.text.toString()
        var price = 0.0
        val priceText = binding.registerExpensePriceEdit.text.toString().replace(',', '.')
        if (priceText.isNotBlank()) {
            price = priceText.toDouble()
        }

        val expense = Expense(
            id = id,
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

            Toast.makeText(this, getString(R.string.successful_operation), Toast.LENGTH_SHORT).show()

            finish()
        } else {
            Toast.makeText(this, getString(R.string.data_not_processed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun expenseIsValid(expense: Expense): Boolean {
        return expense.name.isNotBlank() && expense.price > 0
    }
}