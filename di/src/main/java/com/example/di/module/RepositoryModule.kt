package com.example.di.module

import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun characterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository
}
