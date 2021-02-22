package ru.avito.recycler.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

/* Тут была лишняя связка с контроллером. Использовался Dispatcher.IO, в то время как у нас шла
* интенсивная работа)) */

// Эта штука генерит асинхронные данные
object ItemGenerator {

    // Задержка перед добавлением элемента
    private const val DELAY_MIN = 1000L
    private const val DELAY_MAX = 5000L

    // Данные приходят через определенное время
    fun runGenerator(): Flow<Item> = flow {
        while (true) {

            // Пусть генератор создает Item 1 раз в случайное время
            delay(Random.nextLong(DELAY_MIN, DELAY_MAX))
            emit(Item())
        }
    }.flowOn(Dispatchers.Default)

}
