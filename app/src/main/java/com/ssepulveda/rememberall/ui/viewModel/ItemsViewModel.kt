package com.ssepulveda.rememberall.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssepulveda.rememberall.db.AppRepository
import com.ssepulveda.rememberall.db.entity.ItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private var idListApp: Long = 0

    private var _colorList = MutableLiveData("")

    private var _currentIdItem = MutableLiveData<ItemList>()

    fun initData(id: Long) {
        idListApp = id
    }

    fun getItemsByList() = repository.getItemsByList(idListApp)

    fun setItemList(text: String) {
        viewModelScope.launch {
            val model = ItemList(0, idListApp, text)
            repository.setItemList(model)
        }
    }

    fun getDataList() = repository.getListById(idListApp)

    fun deleteItem() {
        viewModelScope.launch {
            _currentIdItem.value?.let {
                repository.deleteItem(it)
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
                repository.updateItem(item)
            }
        }
    }


    fun colorList(): LiveData<String> = _colorList

    fun getCurrentItemList(): LiveData<ItemList> = _currentIdItem

    fun configIdItemEdit(itemList: ItemList? = null) {
        itemList?.let {
            _currentIdItem.postValue(itemList)
        }
    }
}
