package com.example.food_manager.data.dao

import androidx.room.*
import com.example.food_manager.domain.recipe.Ingredient
import com.example.food_manager.domain.recipe.Recipe
import com.example.food_manager.domain.recipe.RecipeIngredientCrossRef
import com.example.food_manager.domain.recipe.RecipeWithIngredients

@Dao
interface RecipeWithIngredientsDAO {
    @Insert
    fun save(recipe: Recipe): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveIngredient(join: RecipeIngredientCrossRef)

    @Transaction
    fun insertRecipe(crossRefs: List<RecipeIngredientCrossRef>, recipe: Recipe) {
        val id = save(recipe)
        for (crossRef in crossRefs) {
            crossRef.recipeID = id
            saveIngredient(crossRef)
        }
    }

    @Query("select * from recipe")
    fun findAllWithNoIngredients(): List<Recipe>

    @Delete
    fun deleteOne(recipe: Recipe)

    @Query("select * from Ingredient")
    fun findRecipesByIda(): List<Ingredient>

    @Query("select * from Ingredient inner join RecipeIngredientCrossRef r on r.ingredientID = id where recipeID = :id"   )
    fun findIngredientsById(id: Long): List<Ingredient>

    @Query("select * from Recipe where id = :id")
    fun findRecipesByIdb(id: Long): Recipe

    fun findFullRecipeById(id: Long): RecipeWithIngredients{
        return  RecipeWithIngredients(findRecipesByIdb(id),findIngredientsById(id))
    }
}
