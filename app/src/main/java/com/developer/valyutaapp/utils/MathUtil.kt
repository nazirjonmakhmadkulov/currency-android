package com.developer.valyutaapp.utils

fun nationalValute(nominal: Double, nationalValute: Double, value: Double): Double {
    return (nominal * nationalValute) / value
}

fun foreignValute(foreignValute: Double, nationalValute: Double, value: Double): Double {
    return (foreignValute * nationalValute) / value
}
