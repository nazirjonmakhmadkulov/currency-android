package com.developer.valyutaapp.utils


fun nationalValute(nationalValute: Double, value: Double): Double {
    return nationalValute * value
}

fun foreignValute(foreignValute: Double, nationalValute: Double, value: Double): Double {
    return (foreignValute * nationalValute) / value
}