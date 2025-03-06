package com.developer.domain.usecases

import com.developer.domain.repository.HistoryLocalRepository
import com.developer.domain.repository.CurrencyRemoteRepository
import javax.inject.Inject

class HistoryUseCase @Inject constructor(
    private val historyLocalRepository: HistoryLocalRepository,
    private val currencyRemoteRepository: CurrencyRemoteRepository
) {
    // remote
    suspend fun getRemoteHistories(d1: String, d2: String, cn: Int, cs: String, exp: String) =
        currencyRemoteRepository.getHistories(d1, d2, cn, cs, exp)

    // local
    fun getLocalHistories(valId: Int, day: Int) = historyLocalRepository.getLocalHistories(valId, day)
}