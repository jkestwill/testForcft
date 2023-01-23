package com.jkestwill.test.model.mapper

import com.jkestwill.test.db.model.BankLocal
import com.jkestwill.test.model.Bank

class BankMapper:Mapper<Bank, BankLocal> {
    override fun transform(i: Bank): BankLocal = BankLocal(
        city = i.city,
        name = i.name,
        phone = i.phone,
        url=i.url
    )


}