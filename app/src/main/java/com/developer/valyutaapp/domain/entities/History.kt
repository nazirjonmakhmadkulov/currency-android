package com.developer.valyutaapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var charCode: String = "",
    var nominal: Int = 0,
    var name: String = "",
    var value: String = "",
    var dates: String = "",
)