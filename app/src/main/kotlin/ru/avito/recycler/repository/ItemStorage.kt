package ru.avito.recycler.repository

import ru.avito.recycler.model.Item

// На случай нескольких реализаций
interface ItemStorage {

    fun addItem(item: Item)

    fun removeItem(id: Int)

    fun getListItems(): List<Item>

    fun getCountItems(): Int

   // fun flowListItems() : Flow<Item>
}