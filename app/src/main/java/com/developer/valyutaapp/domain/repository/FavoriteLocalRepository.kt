package com.developer.valyutaapp.domain.repository

import com.developer.valyutaapp.domain.entities.Favorite

interface FavoriteLocalRepository {
    suspend fun insertLocalFavorite(favorite: Favorite)
    suspend fun updateLocalFavorite(favorite: Favorite)
    suspend fun deleteLocalFavorite(valId: Int)
    suspend fun deleteAllLocalFavorites()
}