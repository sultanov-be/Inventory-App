package com.example.inventoryapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.inventoryapp.model.Product
import com.example.inventoryapp.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class MainViewModel(private val repository: MainRepository) : ViewModel() {


    fun getAllSearchProduct(searchWord:String): LiveData<List<Product>> {
        return repository.getAllSearchProduct(searchWord).asLiveData()
    }

    fun getAllProducts(isArchive:Boolean): LiveData<List<Product>> {
        return repository.getAllProduct(isArchive).asLiveData()
    }

    fun updateData(product: Product){
        val thread = Executors.newSingleThreadExecutor()
        thread.execute {
            repository.updateItem(product)
        }
        thread.shutdown()
    }
}

