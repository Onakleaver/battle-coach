package com.battlecoach.di

import com.battlecoach.domain.engine.ChessEngineManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EngineModule {
    @Provides
    @Singleton
    fun provideChessEngineManager(): ChessEngineManager = ChessEngineManager()
} 