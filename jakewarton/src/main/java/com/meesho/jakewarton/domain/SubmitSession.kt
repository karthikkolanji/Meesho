package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SubmitSession @Inject constructor(private val repository: Repository) {

    suspend fun submit() {
        repository.submitSession()
    }

}