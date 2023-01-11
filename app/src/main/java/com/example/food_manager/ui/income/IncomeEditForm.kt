package com.example.food_manager.ui.income

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.food_manager.R
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.databinding.ActivityIncomeRegisterFormBinding
import com.example.food_manager.domain.Income
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomeEditForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityIncomeRegisterFormBinding.inflate(layoutInflater)
    }

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = binding.registerIncomeTitle
        title.text = getString(R.string.edit_income)

        val income = intent.getSerializableExtra("income") as Income?

        fillUpIncomeOnView(income)

        val pickImageButton = binding.pickIncomeImageAction
        pickImageButton.setOnClickListener {
            pickImage()
        }

        applySelectedStateToPickImageView(pickImageButton)

        val saveIncomeButton = binding.saveIncomeBtn
        saveIncomeButton.setOnClickListener {
            income?.id?.let { id -> editIncome(id) }
        }

        val cancelButton = binding.cancelIncomeRegisterBtn
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun fillUpIncomeOnView(income: Income?) {
        if (income == null) {
            finish()
        }

        val nameEdit = binding.registerIncomeNameEdit
        val descriptionEdit = binding.registerRecipeDescriptionEdit
        val amountEdit = binding.registerIncomeAmountEdit

        nameEdit.setText(income?.name)
        descriptionEdit.setText(income?.description)
        amountEdit.setText(income?.amount.toString())
        imageUri = Uri.parse(income?.imgUri)
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

    private fun editIncome(id: Long) {
        val name = binding.registerIncomeNameEdit.text.toString()
        val description = binding.registerRecipeDescriptionEdit.text.toString()
        var amount = 0.0
        val amountText = binding.registerIncomeAmountEdit.text.toString().replace(',', '.')
        if (amountText.isNotBlank()) {
            amount = amountText.toDouble()
        }
        val imgUri = imageUri.toString()

        val income = Income(
            id = id,
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
                    dao.edit(income)
                }
            }

            Toast.makeText(this, getString(R.string.successful_operation), Toast.LENGTH_SHORT).show()

            finish()
        } else {
            Toast.makeText(this, getString(R.string.data_not_processed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun incomeIsValid(income: Income): Boolean {
        return income.name.isNotBlank() && income.description.isNotBlank() &&
                income.amount > 0 && income.imgUri.isNotBlank()
    }
}