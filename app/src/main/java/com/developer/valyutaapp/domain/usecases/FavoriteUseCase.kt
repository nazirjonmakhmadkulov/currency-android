package com.developer.valyutaapp.domain.usecases

import com.developer.valyutaapp.domain.entities.Favorite
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.repository.FavoriteLocalRepository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class FavoriteUseCase : KoinComponent {
    private val favoriteLocalRepository: FavoriteLocalRepository by inject()

    //local

    suspend fun invokeInsertLocalFavorite(favorite: Favorite) =
        favoriteLocalRepository.insertLocalFavorite(favorite)

    suspend fun invokeUpdateLocalFavorite(favorite: Favorite) =
        favoriteLocalRepository.updateLocalFavorite(favorite)

    suspend fun invokeDeleteLocalFavorite(valId: Int) =
        favoriteLocalRepository.deleteLocalFavorite(valId)

    suspend fun invokeDeleteAllLocalFavorites() = favoriteLocalRepository.deleteAllLocalFavorites()
}