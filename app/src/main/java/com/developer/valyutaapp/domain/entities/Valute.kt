package com.developer.valyutaapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Entity(tableName = "valute")
@Root(name = "Valute", strict = false)
data class Valute(

    @field:Attribute(name = "ID")
    @param:Attribute(name = "ID")
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    @field:Element(name = "CharCode")
    @param:Element(name = "CharCode")
    var charCode: String = "",

    @field:Element(name = "Nominal")
    @param:Element(name = "Nominal")
    var nominal: Int = 0,

    @field:Element(name = "Name")
    @param:Element(name = "Name")
    var name: String = "",

    @field:Element(name = "Value")
    @param:Element(name = "Value")
    var value: String = "",

    var dates: String = "",
    var favoritesValute: Int = 0,
    var favoritesConverter: Int = 0,
)