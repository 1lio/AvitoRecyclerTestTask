package ru.avito.recycler.view

import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.util.AttributeSet
import androidx.recyclerview.widget.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import ru.avito.recycler.controller.ItemControllerImpl

class MainRecyclerView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) :
    RecyclerView(context, attr) {

    private val controller = ItemControllerImpl
    private val mainRecyclerAdapter = MainRecyclerAdapter()

    init {

        // Настраиваем View
        configureView()

        // Подписываемся на зименение Item
        observeNewItems()
    }

    private fun configureView() {

        // Проверяем
        checkOrientation()

        // Используем стандартные анимации. Указываю явно))
        itemAnimator = DefaultItemAnimator()

        adapter = mainRecyclerAdapter
    }

    private fun checkOrientation() {
        // Устанавливаем кол-во столбцов в зависимости от ориентации экрана
        val spanCount = if (resources.configuration.orientation == ORIENTATION_PORTRAIT) 2 else 4
        layoutManager = GridLayoutManager(context, spanCount)
    }

    private fun observeNewItems() {

        // Запускаем корутину
        GlobalScope.launch(Dispatchers.Main) {

            // Подписываемся
            controller.subscribeAddedItemPosition().collect {
                adapter?.notifyItemInserted(it)
            }
        }

    }

}
