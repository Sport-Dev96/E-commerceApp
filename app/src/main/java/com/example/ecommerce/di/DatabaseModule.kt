package com.example.ecommerce.di

import android.content.Context
import androidx.room.Room
import com.example.ecommerce.room.CartDAO
import com.example.ecommerce.room.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Or another component if more appropriate
object DatabaseModule {

    @Provides
    @Singleton // Ensures only one instance of the database is created
    fun provideAppDatabase(@ApplicationContext appContext: Context): CartDatabase {
        return Room.databaseBuilder(
            appContext,
            CartDatabase::class.java,
            "cart_database" // Replace with your actual database name
        ).build()
    }

    @Provides
    fun provideCartDao(appDataBase: CartDatabase): CartDAO {
        return appDataBase.cartDAO() // Assuming your AppDatabase has a cartDao() method
    }
}