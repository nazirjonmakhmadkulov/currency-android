package com.developer.valyutaapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Entity(tableName = "valute")
@Root(name = "Valute", strict = false)
class Valute {

    //@param:Attribute(name = "ID", required = false)
    @get:Attribute(name = "ID", required = false)
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

    //@param:Element(name = "CharCode")
    @get:Element(name = "CharCode")
    lateinit var charCode: String

    //@param:Element(name = "Nominal")
    @get:Element(name = "Nominal")
    var nominal: Int = 0

    //@param:Element(name = "Name")
    @get:Element(name = "Name")
    var name: String = ""

   // @param:Element(name = "Value")
    @get:Element(name = "Value")
    lateinit var value: String

    var sortValute: Int = 0
}