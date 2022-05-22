package com.developer.valyutaapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var favorite_valute: Int = 0,
    var favorite_converter: Int = 0
)