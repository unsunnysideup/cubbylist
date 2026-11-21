package ait.hu.cubbylist.di

import ait.hu.cubbylist.data.AppDatabase
import ait.hu.cubbylist.data.shopItem.ShopItemDAO
import ait.hu.cubbylist.data.shopList.ShopListDAO
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideShopItemDao(appDatabase: AppDatabase) : ShopItemDAO {
        return appDatabase.shopItemDao()
    }

    @Provides
    fun provideShopListDao(appDatabase: AppDatabase) : ShopListDAO {
        return appDatabase.shopListDao()
    }

    @Provides
    fun provideTodoAppDatabase(
        @ApplicationContext appContext: Context
    ) : AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

}