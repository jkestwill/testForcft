package com.jkestwill.test.model.mapper

import com.jkestwill.test.db.model.NumberLocal

class NumberMapper:Mapper<com.jkestwill.test.model.Number, NumberLocal> {
    override fun transform(i: com.jkestwill.test.model.Number): NumberLocal = NumberLocal(
        length = i.length,
        luhn = i.luhn
    )

}