package com.developer.valyutaapp.domain.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "ValCurs", strict = false)
data class ValCurs(
    @field:ElementList(name = "Valute", inline = true, required = false)
    @param:ElementList(name = "Valute",  inline = true, required = false)
    val valute: List<Valute>,

    @field:Attribute(name = "Date")
    @param:Attribute(name = "Date")
    val dates: String,

    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String,
)