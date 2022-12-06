package domain

import java.util.*
import kotlin.collections.ArrayList

class FoodTechnicalSheet(
    private var id: UUID,
    private var name: String,
    private var description: String,
    private var price: Double,
    private var ingredients: ArrayList<Ingredient>
) {
    fun getId(): UUID {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

    fun getDescription(): String {
        return this.description
    }

    fun getPrice(): Double {
        return this.price
    }

    fun getIngredients(): ArrayList<Ingredient> {
        return this.ingredients
    }
}