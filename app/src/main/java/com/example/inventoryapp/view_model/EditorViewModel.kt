package com.example.inventoryapp.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventoryapp.model.Product
import com.example.inventoryapp.repository.MainRepository
import java.util.concurrent.Executors

class EditorViewModel (private val repository:MainRepository): ViewModel() {

    val dataIsSaved = MutableLiveData<Boolean?>()
    private val thread = Executors.newSingleThreadExecutor()

    fun insertData(product: Product){
        thread.execute {
            repository.insertData(product)
        }
        thread.shutdown()
        dataIsSaved.postValue(thread.isShutdown)
    }

    fun updateData(product: Product){
        thread.execute {
            repository.updateItem(product)
        }
        thread.shutdown()
        dataIsSaved.postValue(thread.isShutdown)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("TAG", "onCleared")
    }
}