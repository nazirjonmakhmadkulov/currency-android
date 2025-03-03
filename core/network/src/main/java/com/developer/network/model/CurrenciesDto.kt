package com.developer.network.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
data class CurrenciesDto(
    @field:ElementList(name = "Valute", inline = true, required = false)
    @param:ElementList(name = "Valute", inline = true, required = false)
    val valute: List<CurrencyDto>,

    @field:Attribute(name = "Date")
    @param:Attribute(name = "Date")
    val dates: String,

    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String
)

@Root(name = "Valute", strict = false)
data class CurrencyDto(
    @field:Attribute(name = "ID")
    @param:Attribute(name = "ID")
    var valId: Int = 0,

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
)