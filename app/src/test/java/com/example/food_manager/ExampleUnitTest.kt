package com.example.food_manager

import android.content.Context
import com.example.food_manager.data.DatabaseHelper
import com.example.food_manager.domain.recipe.Recipe
import com.example.food_manager.ui.adapter.RecipesAdapter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private var recipes = ArrayList<Recipe>()
    private val context: Context = mock(Context::class.java)
    private val adapter = RecipesAdapter(
        recipes = recipes,
        recipesDAO = DatabaseHelper.getInstance(context).recipeWithIngredientsDAO()
    )

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun success_get_item_id(){
        recipes.add(
            Recipe(
                id = 0L,
                name = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                description = "Especial do artur corno",
                cost = 24.00,
                yield = 1
            )
        )
        val expected : Long = 0
        val actual = recipes[0].id
        assertEquals(expected, actual)

    }

    @Test
    fun failed_get_item_id(){
        assertNotEquals( 1, adapter.getItemId(0) )
    }


    @Test
    fun success_get_count(){
        recipes.add(
            Recipe(
                name = "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                description = "Especial do artur corno",
                cost = 24.00,
                yield = 1
            )
        )
        val expected = 1
        assertEquals(expected,adapter.itemCount )

    }

    @Test
    fun failed_get_count(){
        val expected = 1
        assertNotEquals(expected,adapter.itemCount )

    }

}