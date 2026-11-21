package ait.hu.cubbylist.data.shopList
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopListDAO {

    // select a list
    @Query("SELECT * from ListTable WHERE id = :id")
    fun getList(id: Int): Flow<ShopList>

    // clear all lists and its corresponding items
    @Query("DELETE from ListTable")
    suspend fun deleteAllLists()

    // add a list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: ShopList)

    // update a list
    @Update
    suspend fun update(list: ShopList)

    // delete a list
    @Delete
    suspend fun delete(list: ShopList)

    // select all lists
    @Query("SELECT * FROM ListTable")
    fun getAllLists(): Flow<List<ShopList>>

    // get total cost of a list
    @Query("SELECT SUM(price) AS totalCost FROM ShopItemTable WHERE list = :listId")
    fun getTotalCost(listId: Int): Flow<Double>

    // count lists
     @Query("SELECT COUNT(*) FROM ListTable")
     fun getListCount(): Flow<Int>
}
