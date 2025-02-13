package com.battlecoach.data.local.dao

import androidx.room.*
import com.battlecoach.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserFlow(username: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUser(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET lastSyncTimestamp = :timestamp WHERE username = :username")
    suspend fun updateLastSync(username: String, timestamp: Long)
} 