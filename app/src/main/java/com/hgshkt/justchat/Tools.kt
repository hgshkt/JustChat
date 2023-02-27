package com.hgshkt.justchat

fun <K, V>mapToValueList(map: HashMap<K, V>): List<V> {
    val list: List<V> = mutableListOf()
    for (value in map.values) {
        (list as MutableList).add(value)
    }
    return list
}