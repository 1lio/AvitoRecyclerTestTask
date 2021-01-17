package ru.avito.recycler.controller

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.avito.recycler.model.Item
import ru.avito.recycler.model.ItemGenerator
import kotlin.random.Random

// Читерство) Здесь синглентон в котором хранятся списки.
object ItemControllerImpl : ItemController {

    private const val START_LIST_SIZE = 15 // Начальное кол-во Items

    private val listItem = mutableListOf<Item>()
    private val listPool = mutableListOf<Item>()

    private var updatedPosition: Int = 0

    init {

        // Начальное наполнение списка
        (0 until START_LIST_SIZE).map { listItem.add(Item(it + 1)) }

        // Подписываемся на получение сгенеренных данных
        subscribeData()
    }

    override fun addItem(item: Item) {

        val position: Int = if (listItem.size == 0) 0 else Random.nextInt(0, listItem.size)

        if (listPool.isEmpty()) {
            listItem.add(position, item)            // вставляем сгенереный Item
        } else {
            listItem.add(position, listPool[0])    // Добавляем Item из пула
            listPool.removeAt(0)             // Удаляем из пула
        }

        updatedPosition = position
    }


    // Здесь будет подписываться RecyclerView на обновление
    fun subscribeAddedItemPosition(): Flow<Int> = flow {
        while (true) {
            delay(5000)
            emit(updatedPosition)
        }
    }

    override fun removeItem(id: Int) {
        val item = listItem[id]
        listPool.add(item)      // Добавляем в пул
        listItem.remove(item)   // Удаляем из списка
    }

    override fun getListItems(): MutableList<Item> = listItem

    private fun subscribeData() {
        GlobalScope.launch {
            ItemGenerator.runGenerator().collect {
                addItem(it)
            }
        }
    }

    // Вернем размер списка и то что лежит в пуле
    fun getCountItems() = listItem.size + listPool.size
}
