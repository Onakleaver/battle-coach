package com.battlecoach.data.local.dao

import androidx.room.*
import com.battlecoach.data.local.entities.PuzzleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PuzzleDao {
    @Query("SELECT * FROM puzzles WHERE isCompleted = 0 ORDER BY rating ASC LIMIT 1")
    suspend fun getNextPuzzle(): PuzzleEntity?

    @Query("SELECT * FROM puzzles WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedPuzzlesFlow(): Flow<List<PuzzleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPuzzles(puzzles: List<PuzzleEntity>)

    @Update
    suspend fun updatePuzzle(puzzle: PuzzleEntity)
} 