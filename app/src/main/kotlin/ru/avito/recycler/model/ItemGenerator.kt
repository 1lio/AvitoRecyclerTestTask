package ru.avito.recycler.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import ru.avito.recycler.controller.ItemControllerImpl

object ItemGenerator {

    private const val LIST_MAX_SIZE = 100  // Ограничение размера списка, сделано осознанно :)
    private const val TIME_DELAY = 5000L   // Задержка перед добавлением элемента

    // Вроде эмуляции соединения с сервиром.
    // Данные приходят через определенное время

    fun runGenerator(): Flow<Item> = flow {

        while (true) {

            // Пусть генератор знает размер списка
            if (ItemControllerImpl.getCountItems() <= LIST_MAX_SIZE) {
                delay(TIME_DELAY)
                emit(Item(ItemControllerImpl.getCountItems() + 1))
            }

        }

    }.flowOn(Dispatchers.IO)

}