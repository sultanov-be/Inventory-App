package com.example.inventoryapp.repository

import com.example.inventoryapp.model.Product
import com.example.inventoryapp.model.room.ProductDAO
import kotlinx.coroutines.flow.Flow

class MainRepository(private val dao:ProductDAO) {

    fun getAllSearchProduct(searchWord:String): Flow<List<Product>> {
        return dao.getSearchProduct(searchWord)
    }

    fun getAllProduct(isArchive:Boolean): Flow<List<Product>> {
        return dao.getAllProduct(isArchive)
    }

    fun insertData(product:Product){ dao.insertData(product)
    }

    fun getSearchArchiveProduct(searchWord:String): Flow<List<Product>> {
        return dao.getSearchArchiveProduct(searchWord)
    }

    suspend fun deleteItem(id:Int){
        dao.deleteItem(id)
    }

    fun updateItem(product: Product){
        dao.updateItem(product)
    }
}