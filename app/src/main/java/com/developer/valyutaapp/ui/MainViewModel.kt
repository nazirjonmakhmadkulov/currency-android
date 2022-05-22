package com.developer.valyutaapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import com.developer.valyutaapp.domain.entities.ValCurs
import  com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.domain.entities.Favorite
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.usecases.FavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val valuteUseCase: ValuteUseCase, private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    private val _getRemoteValutes = MutableLiveData<Result<ValCurs>>(Result.Loading)
    val getRemoteValutes: LiveData<Result<ValCurs>> get() = _getRemoteValutes

    fun getRemoteValutes(date: String, exp: String) = viewModelScope.launch {
        when (val result = valuteUseCase.invokeGetRemoteValutes(date, exp)) {
            is Result.Loading -> {}
            is Result.Success -> {
                _getRemoteValutes.value = Result.Success(result.data)
            }
            is Result.Error -> {
                _getRemoteValutes.value =
                    Result.Error(result.cause, result.code, result.errorMessage)
            }
        }
    }

    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.invokeGetLocalValutes()

    fun getFavoriteLocalValutes(favorite: String): Flow<List<Valute>> =
        valuteUseCase.invokeGetFavoriteLocalValutes(favorite)

    private val _getLocalValuteById = MutableLiveData<Valute>()
    val getLocalValuteById: LiveData<Valute> get() = _getLocalValuteById

    fun getLocalValuteById(valId: Int) = viewModelScope.launch {
        _getLocalValuteById.value = valuteUseCase.invokeGetLocalValuteById(valId)
    }

    private val _getLocalValuteCount = MutableLiveData<Int>()
    val getLocalValuteCount: LiveData<Int> get() = _getLocalValuteCount

    fun getLocalValuteCount() = viewModelScope.launch {
        _getLocalValuteCount.value = valuteUseCase.invokeGetLocalValuteCount()
    }

    private val _updateLocalValute = MutableLiveData<Unit>()
    val updateLocalValute: LiveData<Unit> get() = _updateLocalValute

    fun updateLocalValute(valute: Valute) = viewModelScope.launch {
        _updateLocalValute.value = valuteUseCase.invokeUpdateLocalValute(valute)
    }

    private val _deleteLocalValute = MutableLiveData<Unit>()
    val deleteLocalValute: LiveData<Unit> get() = _deleteLocalValute

    fun deleteLocalValute(valute: Valute) = viewModelScope.launch {
        _deleteLocalValute.value = valuteUseCase.invokeDeleteLocalValute(valute)
    }

    private val _deleteAllLocalValute = MutableLiveData<Unit>()
    val deleteAllLocalValute: LiveData<Unit> get() = _deleteAllLocalValute

    fun deleteAllLocalValute() = viewModelScope.launch {
        _deleteAllLocalValute.value = valuteUseCase.invokeDeleteAllLocalValutes()
    }


    //favorite
    private val _insertLocalFavorite = MutableLiveData<Unit>()
    val insertLocalFavorite: LiveData<Unit> get() = _insertLocalFavorite

    fun insertLocalFavorite(favorite: Favorite) = viewModelScope.launch {
        _insertLocalFavorite.value = favoriteUseCase.invokeInsertLocalFavorite(favorite)
    }

    private val _updateLocalFavorite = MutableLiveData<Unit>()
    val updateLocalFavorite: LiveData<Unit> get() = _updateLocalFavorite

    fun updateLocalFavorite(favorite: Favorite) = viewModelScope.launch {
        _updateLocalFavorite.value = favoriteUseCase.invokeUpdateLocalFavorite(favorite)
    }

    private val _deleteLocalFavorite = MutableLiveData<Unit>()
    val deleteLocalFavorite: LiveData<Unit> get() = _deleteLocalFavorite

    fun deleteLocalFavorite(valId: Int) = viewModelScope.launch {
        _deleteLocalFavorite.value = favoriteUseCase.invokeDeleteLocalFavorite(valId)
    }

    private val _deleteAllLocalFavorite = MutableLiveData<Unit>()
    val deleteAllLocalFavorite: LiveData<Unit> get() = _deleteAllLocalFavorite

    fun deleteAllLocalFavorite() = viewModelScope.launch {
        _deleteAllLocalFavorite.value = favoriteUseCase.invokeDeleteAllLocalFavorites()
    }
}