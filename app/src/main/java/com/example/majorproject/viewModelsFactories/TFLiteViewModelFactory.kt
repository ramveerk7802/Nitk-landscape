package com.example.majorproject.viewModelsFactories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.majorproject.viewModels.TFLiteViewModel

class TFLiteViewModelFactory(private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TFLiteViewModel::class.java)){
            return TFLiteViewModel(context) as T
        }
        throw IllegalArgumentException ("Unknown view Model class")
    }
}