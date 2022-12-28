package com.example.food_manager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.food_manager.domain.RecipeItemListModel
import com.example.food_manager.domain.adapter.RecipeListAdapter
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
    var recipes = ArrayList<RecipeItemListModel>()
    var url =
        "https://assets.unileversolutions.com/recipes-v2/214590.jpg"
    val context = mock(Context::class.java)
    val adapter = RecipeListAdapter(context,recipes = recipes)

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun success_get_item_id(){
        recipes.add(
            RecipeItemListModel(
                1,
                "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )
        val expected : Long = 0
        assertEquals(expected,adapter.getItemId(0) )

    }

    @Test
    fun failed_get_item_id(){
        assertNotEquals( 1, adapter.getItemId(0) )

    }


    @Test
    fun success_get_count(){
        recipes.add(
            RecipeItemListModel(
                1,
                "Torta de frango sem queijoTorta de frango sem queijoTorta de frango sem queijo",
                "Especial do artur corno",
                24.00,
                url
            )
        )
        val expected = 1
        assertEquals(expected,adapter.getCount() )

    }

    @Test
    fun failed_get_count(){
        val expected = 1
        assertNotEquals(expected,adapter.getCount() )

    }

}