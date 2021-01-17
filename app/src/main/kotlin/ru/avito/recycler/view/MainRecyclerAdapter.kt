package ru.avito.recycler.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.avito.recycler.R
import ru.avito.recycler.controller.ItemControllerImpl

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ItemViewHolder>() {

    private val controller = ItemControllerImpl
    private val list = controller.getListItems()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size

    inner class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        // Тут можно было биндить кастомную view,  Но решил оставить xml)

        private val labelTitle = item.findViewById<TextView>(R.id.labelTitle)
        private val btnRemove = item.findViewById<ImageView>(R.id.btnRemove)

        fun bind(position: Int) {

            labelTitle.text = list[position].id.toString()

            btnRemove.setOnClickListener {
                // Лочим клик, пока recycler обновит список
                it.isClickable = false

                // исользуем adapterPosition, т.к. обычный позишн асинхронный (Адаптер может не успеть обновиться)
                controller.removeItem(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

    }
}
