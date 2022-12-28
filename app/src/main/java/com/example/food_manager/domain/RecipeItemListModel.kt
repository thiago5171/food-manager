package com.example.food_manager.domain

import java.util.*

class RecipeItemListModel(
    private var id: Int,
    private var tittle: String,
    private var subtitle: String,
    private var cost: Double,
    private var urlImage: String

) {

    fun getId(): Int {
        return this.id
    }

    fun getTitle(): String {
        return this.tittle
    }

    fun getSubtitle(): String {
        return this.subtitle
    }

    fun getCost(): Double {
        return this.cost
    }

    fun getUrlImage(): String {
        return this.urlImage
    }

}
