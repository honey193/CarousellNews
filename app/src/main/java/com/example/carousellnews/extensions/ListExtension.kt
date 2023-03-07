package com.example.carousellnews.extensions

fun <T> List<T>?.isSortedListAreEqual(list: List<T>): Boolean =
    if (this?.size != list.size) false
    else this.zip(list).all { (x, y) -> x == y }
