package com.developer.valyutaapp.domain.entities

import androidx.room.Entity
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Entity
@Root(name = "Valute")
class ValuteUpdate {
    @Attribute(name = "ID")
    var id = 0

    @Element(name = "CharCode")
    var charCode: String? = null

    @Element(name = "Nominal")
    var nominal = 0

    @Element(name = "Name")
    var name: String? = null

    @Element(name = "Value")
    var value: String? = null
}