package org.jiqiang.data.model

sealed class HomeScreenItems {

    data class ListView(val type: String = "Vertical") : HomeScreenItems()
    object Layouts : HomeScreenItems()

    val name: String
        get() = when (this) {
            is ListView -> "$type ListView"
            is Layouts -> "Layouts"
        }
}