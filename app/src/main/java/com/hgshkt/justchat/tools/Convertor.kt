package com.hgshkt.justchat

import java.util.*
import kotlin.collections.HashMap

fun <K, V>mapToValueList(map: HashMap<K, V>): List<V> {
    val list: List<V> = mutableListOf()
    for (value in map.values) {
        (list as MutableList).add(value)
    }
    return list
}

fun <K, V>mapToValueList(map: SortedMap<K, V>): List<V> {
    val list: List<V> = mutableListOf()
    for (value in map.values) {
        (list as MutableList).add(value)
    }
    return list
}