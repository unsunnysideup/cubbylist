package ait.hu.cubbylist.ui.screen.listScreenPage

import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.data.shopItem.ShopItemDAO
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.data.shopList.ShopListDAO
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    val itemDAO: ShopItemDAO,
    val listDAO: ShopListDAO
) : ViewModel() {

    // add an item
    fun addItem(item: ShopItem) {
        viewModelScope.launch {
            itemDAO.insert(item)
        }
    }

    // delete an item
    fun deleteItem(item: ShopItem) {
        viewModelScope.launch {
            itemDAO.delete(item)
        }
    }

    // edit an item
    fun editItem(editItem: ShopItem) {
        viewModelScope.launch {
            itemDAO.update(editItem)
        }
    }

    // delete all items
    fun clearItems(listId: Int) {
        viewModelScope.launch {
            itemDAO.deleteAllItems(listId)
        }
    }

    // get all items
    fun getAllItems(listId: Int): Flow<List<ShopItem>> {
        return itemDAO.getAllItems(listId)
    }

    // update tick status of an item
    fun changeItemState(item: ShopItem, value: Boolean) {
        val newItem = item.copy()
        newItem.isDone = value

        viewModelScope.launch { // we launch a coroutine here
            itemDAO.update(newItem)
        }
    }

    // retrieve list
    fun getList(listId: Int): Flow<ShopList> {
        return listDAO.getList(listId)
    }

    // retrieve cost of a list
    fun getListCost(listId: Int): Flow<Double> {
        return listDAO.getTotalCost(listId)
    }


}




