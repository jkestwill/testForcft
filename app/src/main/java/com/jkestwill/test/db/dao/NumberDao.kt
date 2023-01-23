package com.jkestwill.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jkestwill.test.db.model.NumberLocal

@Dao
interface NumberDao {
    @Insert(entity = NumberLocal::class, onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insert(numberLocal: NumberLocal):Long
}