package com.example.inventoryapp.model.room

import android.content.Context
import androidx.room.*
import com.example.inventoryapp.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun wordDao(): ProductDAO

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
