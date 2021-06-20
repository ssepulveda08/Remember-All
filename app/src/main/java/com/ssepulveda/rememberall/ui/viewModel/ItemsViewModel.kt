package com.ssepulveda.rememberall.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssepulveda.rememberall.db.entity.ItemList
import com.ssepulveda.rememberall.db.repositories.ItemsRepository
import com.ssepulveda.rememberall.db.repositories.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val listRepository: ListRepository,
    private val itemRepository: ItemsRepository,
) : ViewModel() {

    private var idListApp: Long = 0

    private var _currentIdItem = MutableLiveData<ItemList>()

    fun initData(id: Long) {
        idListApp = id
    }

    fun setItemList(text: String) {
        viewModelScope.launch {
            val model = ItemList(0, idListApp, text)
            itemRepository.addNewItem(model)
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            _currentIdItem.value?.let {
                itemRepository.deleteItem(it)
            }
        }
    }

    fun updateItem(text: String) {
        viewModelScope.launch {
            _currentIdItem.value?.let {
                val item = ItemList(
                    it.itemId,
                    it.listId,
                    text
                )
                itemRepository.updateItem(item)
            }
        }
    }

    fun configIdItemEdit(itemList: ItemList? = null) {
        itemList?.let {
            _currentIdItem.postValue(itemList)
        }
    }

    /**
     * LiveData
     */

    fun getCurrentItemList(): LiveData<ItemList> = _currentIdItem

    fun onList() = listRepository.getListById(idListApp)

    fun onItemsByList() = itemRepository.getItemsByList(idListApp)
}
