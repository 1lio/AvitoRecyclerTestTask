package ru.avito.recycler.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.avito.recycler.model.Item
import ru.avito.recycler.repository.ItemStorage
import javax.inject.Inject

/* До этого тут выполнял роль хранилища, что наверное не есть правильно.
 * В данном случае контроллер отвечает за предоставление списка и положение item
 */

@HiltViewModel
class ItemsViewModel @Inject constructor(private val repository: ItemStorage) : ViewModel() {

    private val itemFlow: MutableLiveData<List<Item>> = MutableLiveData()

    init {
        checkRepository()
    }

    fun removeItem(itemId: Int) {
        repository.removeItem(itemId)                  // Удаляем элемент
        itemFlow.postValue(repository.getListItems())  // Сразу просим новый список
    }

    fun observeList(owner: LifecycleOwner, observer: Observer<List<Item>>) {
        itemFlow.observe(owner, observer)
    }

    private fun checkRepository() {

        viewModelScope.launch {

            while (true) {
                delay(1000L)
                itemFlow.value = repository.getListItems()
            }

        }

    }

}
