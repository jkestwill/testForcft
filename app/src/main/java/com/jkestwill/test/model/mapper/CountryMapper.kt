package com.jkestwill.test.model.mapper

import com.jkestwill.test.db.model.CountryLocal
import com.jkestwill.test.model.Country

class CountryMapper:Mapper<Country, CountryLocal> {
    override fun transform(i: Country): CountryLocal = CountryLocal(
        alpha2 = i.alpha2,
        currency = i.currency,
        emoji = i.emoji,
        latitude = i.latitude,
        longitude = i.longitude,
        name=i.name,
        numeric = i.numeric
    )


}