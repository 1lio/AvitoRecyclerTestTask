package ru.avito.recycler.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Ну вот, так немного лучше)
 *
 *  Мои контакты:
 *  email: svo_efremov@mail.ru
 *         svoefremov@ya.ru
 *
 * Telegram: @sukhovvv
 * Вячеслав
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainRecyclerView(context = this))
    }
}
