package org.jiqiang.data.model

data class Item(
    val id: Int,
    val title: String,
    val subTitle: String,
    val imageId: Int,
    val source: String = "demo Source"
)