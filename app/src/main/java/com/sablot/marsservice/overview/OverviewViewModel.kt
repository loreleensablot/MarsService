package com.sablot.marsservice.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sablot.marsservice.network.MarsApi
import com.sablot.marsservice.network.MarsProperty
import com.sablot.marsservice.network.Type
import kotlinx.coroutines.launch


class OverviewViewModel : ViewModel() {

    private val _properties = MutableLiveData<List<MarsProperty>>()
    private val _response = MutableLiveData<String>()
    private val _status = MutableLiveData<MarsApiStatus>()

    val status: LiveData<MarsApiStatus>
        get() = _status

    val response: LiveData<String>
        get() = _response

    val properties : LiveData<List<MarsProperty>>
        get() = _properties

    init {
        getMarsRealEstateProperties(Type.ALL)
    }

    private fun getMarsRealEstateProperties(type: Type) {
        _status.value = MarsApiStatus.LOADING
        viewModelScope.launch {
            try {
                _properties.value = MarsApi.retrofitService.getProperties(type.text)
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateFilter(type: Type) {
        getMarsRealEstateProperties(type)
    }

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()
    val navigateToSelectedProperty: MutableLiveData<MarsProperty?>
        get() = _navigateToSelectedProperty

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}

enum class MarsApiStatus {
    LOADING,
    ERROR,
    DONE
}