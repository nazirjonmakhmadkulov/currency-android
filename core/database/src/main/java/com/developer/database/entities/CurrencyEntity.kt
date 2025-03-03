package com.developer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "valute")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val valId: Int = 0,
    val charCode: String = "",
    val nominal: Int = 0,
    val name: String = "",
    val value: String = "",
    var dates: String = "",
    var favoritesValute: Int = 0,
    var favoritesConverter: Int = 0
)