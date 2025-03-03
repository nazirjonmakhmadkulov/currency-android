package com.developer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var dates: String = "",
    var valId: Int = 0,
    var charCode: String = "",
    var nominal: Int = 0,
    var value: String = ""
)