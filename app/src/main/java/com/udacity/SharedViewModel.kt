package com.udacity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel()  {

    private var _radioButton = MutableLiveData<String>()
    val radioButton: LiveData<String>
        get() = _radioButton

    private var _statue = MutableLiveData<String>()
    val statue: LiveData<String>
        get() = _statue

    fun saveValues(radioButton: String,statue: String){
        _radioButton.value = radioButton
        _statue.value = statue
    }



}