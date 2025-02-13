package com.battlecoach.di

import android.content.Context
import androidx.room.Room
import com.battlecoach.data.local.database.BattleCoachDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BattleCoachDatabase {
        return Room.databaseBuilder(
            context,
            BattleCoachDatabase::class.java,
            BattleCoachDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideUserDao(database: BattleCoachDatabase) = database.userDao()

    @Provides
    fun provideGameDao(database: BattleCoachDatabase) = database.gameDao()

    @Provides
    fun providePuzzleDao(database: BattleCoachDatabase) = database.puzzleDao()
} 