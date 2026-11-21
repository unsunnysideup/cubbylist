package ait.hu.cubbylist.data.shopItem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ait.hu.cubbylist.R
import androidx.room.ForeignKey
import ait.hu.cubbylist.data.shopList.ShopList
import androidx.room.Index

@Entity(
    tableName = "ShopItemTable",
    foreignKeys = [
        ForeignKey(
        entity = ShopList::class,
        parentColumns = ["id"],
        childColumns = ["list"],
        onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["list"])]
)

data class ShopItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    @ColumnInfo(name = "Item Name") val itemName:String,
    @ColumnInfo(name = "price") val price:Double,
    @ColumnInfo(name = "list") val listId:Int,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "category") var category:ItemCategory,
    @ColumnInfo(name = "isDone") var isDone: Boolean
)

enum class ItemCategory {
    Clothes, Electronics, Food, Furniture, Household, Stationary, Toiletries, Miscellaneous;

    fun getIcon(): Int {
        val icon = when (this) {
            Electronics -> R.drawable.electronics
            Food -> R.drawable.food
            Stationary -> R.drawable.stationary
            Clothes -> R.drawable.clothes
            Household -> R.drawable.household
            Furniture -> R.drawable.furniture
            Miscellaneous -> R.drawable.misc
            Toiletries -> R.drawable.toiletries
        }
        return icon
    }
}
