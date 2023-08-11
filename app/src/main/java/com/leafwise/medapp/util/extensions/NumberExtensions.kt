package com.leafwise.medapp.util.extensions

val Int.valueIsPair: Boolean
    get() = this % 2 == 0