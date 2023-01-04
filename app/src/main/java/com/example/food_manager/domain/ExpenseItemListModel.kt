package com.example.food_manager.domain

class ExpenseItemListModel(
    private var id: Int,
    private var title: String,
    private var subtitle: String,
    private var price: Double,
    private var urlImage: String
) {
    fun getId(): Int {
        return this.id
    }

    fun getTitle(): String {
        return this.title
    }

    fun getSubtitle(): String {
        return this.subtitle
    }

    fun getPrice(): Double {
        return this.price
    }

    fun getUrlImage(): String {
        return this.urlImage
    }
}