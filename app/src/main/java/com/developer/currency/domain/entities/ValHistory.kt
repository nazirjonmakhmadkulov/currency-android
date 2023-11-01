package com.developer.currency.domain.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
data class ValHistory(
    @field:ElementList(name = "Record", inline = true, required = false)
    @param:ElementList(name = "Record", inline = true, required = false)
    val history: List<History>,

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