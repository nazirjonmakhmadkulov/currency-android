package com.developer.valyutaapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.util.*

@Entity(tableName = "history")
data class History(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var valId: Int = 0,
    var charCode: String = "",
    var nominal: Int = 0,
    var name: String = "",
    var value: String = "",
    var dates: String = "",
)