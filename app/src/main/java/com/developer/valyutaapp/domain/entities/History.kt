package com.developer.valyutaapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.util.*

@Entity(tableName = "history")
@Root(name = "Record", strict = false)
data class History(

    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),

    @field:Attribute(name = "Date")
    @param:Attribute(name = "Date")
    var dates: String = "",

    @field:Attribute(name = "Id")
    @param:Attribute(name = "Id")
    var valId: Int = 0,

    @field:Element(name = "CharCode")
    @param:Element(name = "CharCode")
    var charCode: String = "",

    @field:Element(name = "Nominal")
    @param:Element(name = "Nominal")
    var nominal: Int = 0,

    @field:Element(name = "Value")
    @param:Element(name = "Value")
    var value: String = ""
)