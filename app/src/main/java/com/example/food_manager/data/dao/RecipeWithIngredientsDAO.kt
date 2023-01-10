package com.example.food_manager.data.dao

import androidx.room.*
import com.example.food_manager.domain.Ingredient
import com.example.food_manager.domain.Recipe
import com.example.food_manager.domain.RecipeIngredientCrossRef
import com.example.food_manager.domain.RecipeWithIngredients

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

    @Query("delete from ingredient where id = :id")
    fun delete(id: Long)

    @Transaction
    fun editOne(recipe: Recipe, crossRefs: List<RecipeIngredientCrossRef>) {
        delete(recipe.id)
        insertRecipe(crossRefs,  recipe)
    }

    @Query("select * from Ingredient")
    fun findRecipesById(): List<Ingredient>

    @Query("select * from Ingredient inner join RecipeIngredientCrossRef r on r.ingredientID = id where recipeID = :id"   )
    fun findIngredientsById(id: Long): List<Ingredient>

    @Query("select * from Recipe where id = :id")
    fun findRecipesByIdb(id: Long): Recipe

    fun findFullRecipeById(id: Long): RecipeWithIngredients {
        return  RecipeWithIngredients(findRecipesByIdb(id),findIngredientsById(id))
    }
}
