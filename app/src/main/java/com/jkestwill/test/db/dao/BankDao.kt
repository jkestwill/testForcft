package com.jkestwill.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.jkestwill.test.db.model.BankLocal

@Dao
interface BankDao {
    @Insert(entity = BankLocal::class)
    fun insert(bankLocal: BankLocal):Long

}