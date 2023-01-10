package com.example.food_manager.ui.income

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityIncomeRegisterFormBinding
import com.example.food_manager.domain.income.Income
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomeRegisterForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityIncomeRegisterFormBinding.inflate(layoutInflater)
    }

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        if (uri != null) {
            val pickImageButton = binding.pickIncomeImageAction
            val newIcon = AppCompatResources.getDrawable(this,
                R.drawable.ic_baseline_check_circle_outline_24)
            pickImageButton.setCompoundDrawablesWithIntrinsicBounds(null, null,
                newIcon, null)
            pickImageButton.text = getString(R.string.image_was_selected)
            pickImageButton.setTextColor(
                AppCompatResources.getColorStateList(this,
                R.color.trendingStart))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pickImageButton = binding.pickIncomeImageAction
        pickImageButton.setOnClickListener {
            pickImage()
        }

        val saveIncomeButton = binding.saveIncomeBtn
        saveIncomeButton.setOnClickListener {
            saveIncome()
        }

        val cancelButton = binding.cancelIncomeRegisterBtn
        cancelButton.setOnClickListener {
            finish()
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

    private fun saveIncome() {
        val name = binding.registerIncomeNameEdit.text.toString()
        val description = binding.registerRecipeDescriptionEdit.text.toString()
        val amount = binding.registerIncomeAmountEdit.text.toString().replace(',', '.').toDouble()
        val imgUri = imageUri.toString()

        val income = Income(
            name = name,
            description = description,
            amount = amount,
            imgUri = imgUri
        )

        if (incomeIsValid(income)) {
            val dao = DatabaseHelper.getInstance(this).incomeDAO()

            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    dao.save(income)
                }
            }

            Toast.makeText(this, "salvo com sucesso", Toast.LENGTH_SHORT).show()

            finish()
        } else {
            Toast.makeText(this, "algo deu errado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun incomeIsValid(income: Income): Boolean {
        return income.name.isNotBlank() && income.description.isNotBlank() &&
                income.amount > 0 && income.imgUri.isNotBlank()
    }
}