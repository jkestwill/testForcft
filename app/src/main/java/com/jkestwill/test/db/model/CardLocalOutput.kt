package com.jkestwill.test.db.model

import androidx.room.Relation
import java.io.Serializable

class CardLocalOutput {
    var id = 0L
    var brand: String? = null
    var cardNumber: String? = null
    var timestamp:Long = 0
    var prepaid: Boolean? = null
    var scheme: String? = null
    var type: String? = null
    @Relation(entity = CountryLocal::class, parentColumn = "id", entityColumn = "id")
    var country: CountryLocal? = null
    @Relation(entity = NumberLocal::class, parentColumn = "id", entityColumn = "id")
    var number: NumberLocal? = null
    @Relation(entity = BankLocal::class, parentColumn = "id", entityColumn = "id")
    var bank: BankLocal? = null

    override fun toString(): String {
        return "CardSas(id=$id, bank=$bank, brand=$brand, country=$country, number=$number, prepaid=$prepaid, scheme=$scheme, type=$type)"
    }


}