package com.developer.valyutaapp.data.repository

import com.developer.valyutaapp.data.local.FavoriteDao
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.domain.entities.Favorite
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.repository.FavoriteLocalRepository
import com.developer.valyutaapp.domain.repository.ValuteLocalRepository
import kotlinx.coroutines.flow.Flow

class FavoriteLocalRepositoryImpl(private val favoriteDao: FavoriteDao) : FavoriteLocalRepository {

    override suspend fun insertLocalFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    override suspend fun updateLocalFavorite(favorite: Favorite) {
        favoriteDao.updateFavorite(favorite)
    }

    override suspend fun deleteLocalFavorite(valId: Int) {
        favoriteDao.deleteFavorite(valId)
    }

    override suspend fun deleteAllLocalFavorites() {
        favoriteDao.deleteAllFavorites()
    }
}