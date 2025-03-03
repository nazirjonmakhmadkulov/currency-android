package com.developer.domain.model

import com.developer.common.Item

data class Currency(
    var id: Int = 0,
    var valId: Int = 0,
    var charCode: String = "",
    var nominal: Int = 0,
    var name: String = "",
    var value: String = "",
    var dates: String = "",
    var favoritesValute: Int = 0,
    var favoritesConverter: Int = 0
): Item