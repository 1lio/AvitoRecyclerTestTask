package ru.avito.recycler.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.avito.recycler.model.Item
import ru.avito.recycler.model.ItemGenerator
import kotlin.random.Random

/* Я вынес из Controller работу с хранилищем */
object ItemRepository : ItemStorage {

    private const val START_COUNT_ITEMS = 15   // Начальное кол-во Items
    private const val MAX_LIST_SIZE = 100      // Ограничение которое придумал себе сам

    private val listItem = mutableListOf<Item>()
    private val pool = mutableSetOf<Item>()

    private var itemCounter = START_COUNT_ITEMS + 1

    init {
        // Начальное наполнение списка
        (0 until START_COUNT_ITEMS).map { listItem.add(Item(it + 1)) }

        // Запускаем генератор и подхватываем новые данные
        runGenerator()
    }

    override fun addItem(item: Item) {

        if (itemCounter <= MAX_LIST_SIZE) {

            // Взяли и втюхали в случайное место в хранилище. эм.
            // Теперь из-за этого программиста все хранилище придется перебрать
            val position: Int = if (listItem.size == 0) 0 else Random.nextInt(0, listItem.size)

            if (pool.size == 0) {
                listItem.add(position, item)        // вставляем сгенереный Item
            } else {
                listItem.add(position, pool.last()) // Добавляем Item из пула
                pool.remove(pool.last())            // Удаляем из пула
            }

            Log.i(LOG_TAG, "ItemRepository: addItem : $item")

            itemCounter++
        }

    }

    override fun removeItem(id: Int) {

        // Ошибка удаляем не по ID, а по позиции в списке
        try {
            val item = listItem[id]
            listItem.remove(item)    // Удаляем из списка
            pool.add(item)           // Добавляем в пул

            Log.i(LOG_TAG, "ItemRepository: removeItem: $id")
            itemCounter--
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getListItems(): List<Item> = listItem

    // Вернем размер списка и то что лежит в пуле
    override fun getCountItems() = listItem.size + pool.size

    private fun runGenerator() {
        Log.i(LOG_TAG, "ItemRepository: runGenerator")
        GlobalScope.launch(Dispatchers.Default) {

            // Подписываемся на данные
            ItemGenerator.runGenerator().collect {

                // Добавляем Item в хранилище
                addItem(it.apply { id = getCountItems() + 1 })
            }
        }
    }


    private const val LOG_TAG = "ItemRepository"
}
