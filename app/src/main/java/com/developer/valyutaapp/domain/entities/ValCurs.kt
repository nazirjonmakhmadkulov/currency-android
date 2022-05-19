package com.developer.valyutaapp.domain.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
class ValCurs {
    //@param:ElementList(inline = true, entry = "Valute", required = true)
    @get:ElementList(inline = true, required = false)
    val valute: List<Valute>? = null

    // @param:Attribute(name = "Date", required = false)
    @get:Attribute(name = "Date", required = false)
    val dates: String = ""

    //@param:Attribute(name = "name")
    @get:Attribute(name = "name")
    val name: String = ""
}