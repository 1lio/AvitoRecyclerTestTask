package ru.avito.recycler.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/** Я не доволен своим решением. С одной стороны можно было использовать viewModel и было бы по красоте,
 *  но мне не хочется тащить лишние либы. Поэтому тут типа MVC. Еще есть пара леких косыков (статики).
 *
 *  Мои контакты
 *
 *  Telegram: @sukhovvv
 *  E-mail: svoefremov@ya.ru
 *  Вячеслав
 *
 *  */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainRecyclerView(context = this))
    }
}