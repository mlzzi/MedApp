package com.leafwise.medapp.util.extensions

fun getQuantityList(): Array<String>{
    val arrayList = arrayListOf<String>()

    for (i in 1..5){
        if (i.valueIsPair)
            arrayList.add((i / 2).toString())
        else
            arrayList.add((i.toDouble() / 2.0).toString())
    }
    for (i in 3 .. 50){
        arrayList.add(i.toString())
    }

    return arrayList.toTypedArray()
}

val Int.valueIsPair: Boolean
    get() = this % 2 == 0