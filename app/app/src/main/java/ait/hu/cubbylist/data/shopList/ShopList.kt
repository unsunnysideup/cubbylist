package ait.hu.cubbylist.data.shopList

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ListTable")
data class ShopList(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "List Name") val listName:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "List Cost") val cost:Double,
)