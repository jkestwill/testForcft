package com.jkestwill.test.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jkestwill.test.db.model.CardLocalOutput

class CardDiffUtil(
    private val oldList: List<CardLocalOutput>,
    private val newList: List<CardLocalOutput>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].cardNumber == newList[newItemPosition].cardNumber &&
                oldList[oldItemPosition].brand == newList[newItemPosition].brand &&
                oldList[oldItemPosition].bank == newList[newItemPosition].bank


    }
}