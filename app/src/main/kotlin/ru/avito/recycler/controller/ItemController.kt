package ru.avito.recycler.controller

import ru.avito.recycler.model.Item

// На случай нескольких реализаций
interface ItemController {

    fun addItem(item: Item)

    fun removeItem(id: Int)

    fun getListItems(): List<Item>

}