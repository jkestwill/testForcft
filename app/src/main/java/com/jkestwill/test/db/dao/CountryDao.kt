package com.jkestwill.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jkestwill.test.db.model.CountryLocal

@Dao
interface CountryDao {
    @Insert(entity = CountryLocal::class, onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insert(country: CountryLocal):Long

}