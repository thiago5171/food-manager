package com.example.food_manager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.food_manager.data.dao.ExpenseDAO
import com.example.food_manager.data.dao.IngredientDAO
import com.example.food_manager.data.dao.RecipeWithIngredientsDAO
import com.example.food_manager.domain.Expense.Expense
import com.example.food_manager.domain.recipe.Ingredient
import com.example.food_manager.domain.recipe.Recipe
import com.example.food_manager.domain.recipe.RecipeIngredientCrossRef

@Database(entities = [Recipe::class, Ingredient::class, RecipeIngredientCrossRef::class, Expense::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun ingredientDAO(): IngredientDAO

    abstract fun recipeWithIngredientsDAO(): RecipeWithIngredientsDAO

    abstract fun expenseDAO(): ExpenseDAO

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