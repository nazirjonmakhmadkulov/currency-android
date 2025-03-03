package com.developer.network.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
data class HistoriesDto(
    @field:ElementList(name = "Record", inline = true, required = false)
    @param:ElementList(name = "Record", inline = true, required = false)
    val history: List<HistoryDto>,

    @field:Attribute(name = "DateRange1")
    @param:Attribute(name = "DateRange1")
    val date1: String,

    @field:Attribute(name = "DateRange2")
    @param:Attribute(name = "DateRange2")
    val date2: String,

    @field:Attribute(name = "Name")
    @param:Attribute(name = "Name")
    val name: String
)

@Root(name = "Record", strict = false)
data class HistoryDto(
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