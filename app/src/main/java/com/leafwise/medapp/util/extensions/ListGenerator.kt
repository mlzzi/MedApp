package com.leafwise.medapp.util.extensions

@Suppress("MagicNumber")
object ListGenerator {
    fun generateQuantityList(maxNumber: Int): Array<String>{
        val arrayList = arrayListOf<String>()

        for (i in 1..5){
            if (i.valueIsPair)
                arrayList.add((i / 2).toString())
            else
                arrayList.add((i.toDouble() / 2.0).toString())
        }
        for (i in 3 .. maxNumber){
            arrayList.add(i.toString())
        }

        return arrayList.toTypedArray()
    }
}