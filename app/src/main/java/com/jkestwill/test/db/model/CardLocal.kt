package com.jkestwill.test.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "card", foreignKeys =[
    ForeignKey(entity = BankLocal::class, parentColumns = ["id"], childColumns = ["bankId"]),
    ForeignKey(entity = NumberLocal::class, parentColumns = ["id"], childColumns = ["numberId"]),
    ForeignKey(entity = CountryLocal::class, parentColumns = ["id"], childColumns = ["countryId"])
])
data class CardLocal(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val cardNumber:String,
    val bankId:Long?,
    val brand:String?,
    val countryId: Long?,
    val numberId:Long?,
    val prepaid: Boolean?,
    val scheme: String?,
    val type: String?,
    val timestamp:Long
)

@Entity(tableName="bank")
data class BankLocal(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val city: String?,
    val name: String?,
    val phone: String?,
    val url: String?
)

@Entity(tableName="number")
data class NumberLocal(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val length: Int?,
    val luhn: Boolean?
)


@Entity(tableName="country")
data class CountryLocal(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val alpha2: String?,
    val currency: String?,
    val emoji: String?,
    val latitude: Int?,
    val longitude: Int?,
    val name: String?,
    val numeric: String?
)

