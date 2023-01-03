package com.example.food_manager.domain

import java.util.*

class Expense(
    private var id: UUID,
    private var name: String,
    private var description: String,
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

    fun getPrice(): Double {
        return this.price
    }
}