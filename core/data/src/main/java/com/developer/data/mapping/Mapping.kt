package com.developer.data.mapping

import com.developer.database.entities.HistoryEntity
import com.developer.database.entities.CurrencyEntity
import com.developer.domain.model.History
import com.developer.domain.model.Currency
import com.developer.network.model.HistoryDto
import com.developer.network.model.CurrencyDto

fun HistoryDto.toEntity() = HistoryEntity(
    dates = this.dates,
    valId = this.valId,
    charCode = this.charCode,
    nominal = this.nominal,
    value = this.value,
)

fun CurrencyDto.toEntity() = CurrencyEntity(
    valId = this.valId,
    charCode = this.charCode,
    nominal = this.nominal,
    name = this.name,
    value = this.value,
)

fun CurrencyDto.toModel() = Currency(
    valId = this.valId,
    charCode = this.charCode,
    nominal = this.nominal,
    name = this.name,
    value = this.value,
)

fun HistoryEntity.toModel() = History(
    id = this.id,
    dates = this.dates,
    valId = this.valId,
    charCode = this.charCode,
    nominal = this.nominal,
    value = this.value,
)

fun CurrencyEntity.toModel() = Currency(
    id = this.id,
    valId = this.valId,
    charCode = this.charCode,
    nominal = this.nominal,
    name = this.name,
    value = this.value,
    dates = this.dates,
    favoritesValute = this.favoritesValute,
    favoritesConverter = this.favoritesConverter,
)

fun Currency.toEntity() = CurrencyEntity(
    id = this.id,
    valId = this.valId,
    charCode = this.charCode,
    nominal = this.nominal,
    name = this.name,
    value = this.value,
    dates = this.dates,
    favoritesValute = this.favoritesValute,
    favoritesConverter = this.favoritesConverter,
)