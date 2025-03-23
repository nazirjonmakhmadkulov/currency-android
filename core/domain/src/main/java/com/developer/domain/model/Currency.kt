package com.developer.domain.model

import com.developer.common.Item

data class Currency(
    val id: Int = 0,
    val valId: Int = 0,
    val charCode: String = "",
    val nominal: Int = 0,
    val name: String = "",
    val value: String = "",
    val value2: String = "",
    val dates: String = "",
    var favorite: Int = 0,
    var converter: Int = 0
): Item