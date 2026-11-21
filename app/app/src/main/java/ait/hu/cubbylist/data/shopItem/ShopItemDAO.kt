package ait.hu.cubbylist.data.shopItem

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemDAO {

    // select all items in a list
    @Query("SELECT * FROM ShopItemTable WHERE list = :listId")
    fun getAllItems(listId: Int): Flow<List<ShopItem>>

    // select item
    @Query("SELECT * from ShopItemTable WHERE id = :id")
    fun getItem(id: Int): Flow<ShopItem>

    // add item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ShopItem)

    // update item
    @Update
    suspend fun update(item: ShopItem)

    // delete item
    @Delete
    suspend fun delete(item: ShopItem)

    // delete all items in a list
    @Query("DELETE from ShopItemTable WHERE list = :listId")
    suspend fun deleteAllItems(listId: Int)

}