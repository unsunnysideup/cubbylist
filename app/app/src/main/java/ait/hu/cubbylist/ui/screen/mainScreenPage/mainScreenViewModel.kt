package ait.hu.cubbylist.ui.screen.mainScreenPage
import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.data.shopItem.ShopItemDAO
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.data.shopList.ShopListDAO
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel @Inject constructor(
    val listDAO: ShopListDAO,
    val itemDAO: ShopItemDAO
) : ViewModel() {

    // add a list
    fun addList(list: ShopList) {
        viewModelScope.launch {
            listDAO.insert(list)
        }
    }

    // delete a list
    fun deleteList(list: ShopList) {
        viewModelScope.launch {
            listDAO.delete(list)
        }
    }

    // update a list
    fun editList(editedList: ShopList) {
        viewModelScope.launch {
            listDAO.update(editedList)
        }
    }

    // delete all lists
    fun clearLists() {
        viewModelScope.launch {
            listDAO.deleteAllLists()
        }
    }

    // select all lists
    fun getAllLists(): Flow<List<ShopList>> {
        return listDAO.getAllLists()
    }

    // get list count
    fun getAllListCount(): Flow<Int> {
        return listDAO.getListCount()
    }

    // add an item
    fun addItem(item: ShopItem) {
        viewModelScope.launch {
            itemDAO.insert(item)
        }
    }

    // edit an item
    fun editItem(editItem: ShopItem) {
        viewModelScope.launch {
            itemDAO.update(editItem)
        }
    }

}




