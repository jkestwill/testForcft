package com.jkestwill.test.model.mapper

interface Mapper<I,O> {

    fun transform(i:I):O
}