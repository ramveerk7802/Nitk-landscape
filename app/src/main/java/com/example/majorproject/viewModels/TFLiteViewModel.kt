package com.example.majorproject.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.majorproject.repositories.TFLiteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Path

class TFLiteViewModel(context: Context) : ViewModel(){

    private val repo = TFLiteRepo(context)
    private val _predictedResult = MutableLiveData<Pair<String,Float>>()
    val predictedResult :LiveData<Pair<String,Float>> get() = _predictedResult

    private val _isLoadModel = MutableLiveData<Boolean>(false)
    val isLoadModel :LiveData<Boolean> = _isLoadModel

    private val _isProcess = MutableLiveData<Boolean>(false)
    val isProcess : LiveData<Boolean> get() = _isProcess

    private val _historyMutableLiveData = MutableLiveData<List<Pair<String,Float>>>()
    val historyLiveData : LiveData<List<Pair<String,Float>>> get() = _historyMutableLiveData


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadModel.postValue(true)
            repo.loadModel()
            Log.d("Test","Model load SuccessFully")
            _isLoadModel.postValue(false)
        }
    }

    fun addInHistory(data : Pair<String,Float>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val currentHistory = _historyMutableLiveData.value?.toMutableList()?: mutableListOf()
                currentHistory.add(data)
                _historyMutableLiveData.postValue(currentHistory)
            }
        }
    }


    fun classifyImage(imagePath:String){
        viewModelScope.launch(Dispatchers.IO) {
            _isProcess.postValue(true)
            val result = repo.classifyImage(imagePath)
            addInHistory(result)
            _predictedResult.postValue(result)
            _isProcess.postValue(false)
        }
    }

}