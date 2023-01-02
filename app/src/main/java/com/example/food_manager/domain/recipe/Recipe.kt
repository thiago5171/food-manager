package com.example.food_manager.domain.recipe

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id : Long = 0L,
    val name: String,
    val description: String,
    val price: Double,
    val yield: Int,
) : Serializable