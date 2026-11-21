package ait.hu.cubbylist.data
import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.data.shopItem.ShopItemDAO
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.data.shopList.ShopListDAO
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ShopItem::class, ShopList::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopItemDao(): ShopItemDAO
    abstract fun shopListDao(): ShopListDAO

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, AppDatabase::class.java,
                    "ShopLists_database.db"
                )
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}