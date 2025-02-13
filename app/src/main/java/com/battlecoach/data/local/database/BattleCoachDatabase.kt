package com.battlecoach.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.battlecoach.data.local.dao.*
import com.battlecoach.data.local.entities.*
import com.battlecoach.data.local.converters.Converters

@Database(
    entities = [
        UserEntity::class,
        GameEntity::class,
        PuzzleEntity::class,
        ProgressEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class BattleCoachDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun puzzleDao(): PuzzleDao
    abstract fun progressDao(): ProgressDao

    companion object {
        const val DATABASE_NAME = "battlecoach.db"
    }
} 