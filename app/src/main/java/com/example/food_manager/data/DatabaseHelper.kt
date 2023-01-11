package com.example.food_manager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.food_manager.data.dao.ExpenseDAO
import com.example.food_manager.data.dao.IncomeDAO
import com.example.food_manager.data.dao.IngredientDAO
import com.example.food_manager.data.dao.RecipeWithIngredientsDAO
import com.example.food_manager.domain.Expense
import com.example.food_manager.domain.Income
import com.example.food_manager.domain.Ingredient
import com.example.food_manager.domain.Recipe
import com.example.food_manager.domain.RecipeIngredientCrossRef

@Database(entities = [Recipe::class, Ingredient::class, RecipeIngredientCrossRef::class, Expense::class, Income::class],
    version = 1, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun ingredientDAO(): IngredientDAO

    abstract fun recipeWithIngredientsDAO(): RecipeWithIngredientsDAO

    abstract fun expenseDAO(): ExpenseDAO

    abstract fun incomeDAO(): IncomeDAO

    companion object {
        fun getInstance(context : Context) : DatabaseHelper {
            return Room.databaseBuilder(
                context,
                DatabaseHelper::class.java,
                "food_manager.db"
            ).build()
        }
    }
}