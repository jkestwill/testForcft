package com.jkestwill.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.jkestwill.test.db.model.CardLocal
import com.jkestwill.test.db.model.CardLocalOutput

@Dao
interface CardDao {
    @Insert(entity = CardLocal::class, onConflict = REPLACE)
    suspend fun insert(card: CardLocal): Long

    @Query(
        "SELECT * FROM card " +
                "LEFT JOIN country ON country.id=card.countryId " +
                "LEFT JOIN bank ON bank.id=card.bankId " +
                "LEFT JOIN number ON number.id = card.numberId "+
                "ORDER BY card.timestamp DESC"
    )
    fun selectAll(): LiveData<List<CardLocalOutput>>

    @Query(
        "SELECT * FROM card " +
                "LEFT JOIN country ON country.id=card.countryId " +
                "LEFT JOIN bank ON bank.id=card.bankId " +
                "LEFT JOIN number ON number.id = card.numberId " +
                "WHERE card.id==:id "
    )
    fun selectById(id: Long): LiveData<CardLocalOutput>
}