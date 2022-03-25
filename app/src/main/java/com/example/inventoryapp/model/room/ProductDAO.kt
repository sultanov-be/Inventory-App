package com.example.inventoryapp.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.inventoryapp.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData (product: Product)

    @Query("DELETE FROM product_table")
    fun deleteAll()

    @Query("SELECT * FROM product_table WHERE name LIKE :searchWord AND isArchive = :isArchive ORDER BY name ASC")
    fun getSearchProduct(searchWord:String, isArchive: Boolean = false):Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE isArchive = :isArchive ORDER BY name ASC")
    fun getAllProduct(isArchive:Boolean):Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE name LIKE :searchWord AND isArchive = :isArchive ORDER BY name ASC")
    fun getSearchArchiveProduct(searchWord:String, isArchive: Boolean = true):Flow<List<Product>>

    @Query("DELETE FROM product_table WHERE id = :id")
    suspend fun deleteItem(id:Int)

    @Update(entity = Product::class)
    fun updateItem(product: Product)
}