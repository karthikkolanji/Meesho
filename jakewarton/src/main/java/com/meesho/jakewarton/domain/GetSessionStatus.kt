package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@ViewModelScoped
class GetSessionStatus @Inject constructor(private val repository: Repository) {

    suspend fun get(): Flow<Boolean> {
        return repository.getSessionStatus().filterNotNull()
    }
}