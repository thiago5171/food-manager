package com.example.food_manager.domain

import java.util.*

class Ingredient(
    private var id: UUID,
    private var name: String,
    private var description: String,
    private var quantity: Double,
    private var unitMeasurement: String,
    private var price: Double
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

    fun getQuantity(): Double {
        return this.quantity
    }

    fun getUnitMeasurement(): String {
        return this.unitMeasurement
    }

    fun getPrice(): Double {
        return this.price
    }
}