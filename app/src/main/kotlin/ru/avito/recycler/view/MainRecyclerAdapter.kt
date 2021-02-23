package ru.avito.recycler.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.avito.recycler.R
import ru.avito.recycler.model.Item

/* Тут была ссылка на контроллер. Что вызывало связанность.
*  Теперь Items лезут куда попало и тут не обойтись без DiffUtil */

class MainRecyclerAdapter(private val itemClickListener: ItemOnClick) :
    ListAdapter<Item, MainRecyclerAdapter.ItemViewHolder>(ItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = currentList.size

    override fun submitList(list: MutableList<Item>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    inner class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val labelTitle = item.findViewById<TextView>(R.id.labelTitle)
        private val btnRemove = item.findViewById<ImageView>(R.id.btnRemove)

        fun bind(position: Int) {

            labelTitle.text = currentList[position].id.toString()

            btnRemove.setOnClickListener {
                // Лочим клик, пока recycler обновит список
                it.isClickable = false
                // исользуем adapterPosition, т.к. обычный позишн асинхронный (Адаптер может не успеть обновиться)
                itemClickListener.onRemoveItem(adapterPosition)
            }
        }

    }

    private class ItemDiff : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

}
