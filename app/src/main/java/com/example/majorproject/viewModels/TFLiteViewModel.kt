package com.example.majorproject.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadModel.postValue(true)
            repo.loadModel()
            Log.d("Test","Model load SuccessFully")
            _isLoadModel.postValue(false)
        }
    }
//    fun resetResult(){
//        _predictedResult.postValue(null)
//    }
//    fun classifyImage(imagePath:String,onFailure:(String)->Unit){
//        viewModelScope.launch {
//            _isProcess.postValue(true)
//            Log.d("Test","Classify function called in View Model")
//
//            val result = withContext(Dispatchers.IO){
//                Log.d("Test","Classify function called in Dispatcher")
//                repo.runInference(imagePath = imagePath)
//
//            }
//            Log.d("Test","Classify function called in after run inference")
//            if(result==null){
//                _isProcess.postValue(false)
//                onFailure("Not detected")
//            }
//            result?.let {
//                Log.d("Test","Classify function called in result")
//                _predictedResult.postValue(it)
//            }
//            _isProcess.postValue(false)
//
//        }
//    }

    fun classifyImage(imagePath:String){
        viewModelScope.launch(Dispatchers.IO) {
            _isProcess.postValue(true)
            val result = repo.classifyImage(imagePath)
            _predictedResult.postValue(result)
            _isProcess.postValue(false)
        }
    }

}