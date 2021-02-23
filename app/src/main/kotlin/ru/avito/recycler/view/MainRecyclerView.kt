package ru.avito.recycler.view

import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.util.AttributeSet
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.avito.recycler.viewmodel.ItemsViewModel

class MainRecyclerView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) :
    RecyclerView(context, attr) {

    private val mainRecyclerAdapter = MainRecyclerAdapter(onClickOrder())

    // оч плохо
    private val activity = context as MainActivity

    private val viewModel: ItemsViewModel = ViewModelProvider(activity)[ItemsViewModel::class.java]

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
        viewModel.observeList(activity, {
            mainRecyclerAdapter.submitList(it.toMutableList())
        })
    }

    private fun onClickOrder(): ItemOnClick = object : ItemOnClick {
        override fun onRemoveItem(id: Int) {
            viewModel.removeItem(id)
        }
    }
}
