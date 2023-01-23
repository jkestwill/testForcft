package com.jkestwill.test.ui.card.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkestwill.test.db.model.CardLocalOutput
import com.jkestwill.test.repository.LocalCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardViewModel @Inject constructor(
    private var localCardRepository: LocalCardRepository
) : ViewModel() {

    private var _localCardLiveData: LiveData<CardLocalOutput> = MutableLiveData()
    val localCardLiveData: LiveData<CardLocalOutput> get() = _localCardLiveData

    fun getCard(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            _localCardLiveData= localCardRepository.getById(id)
        }
    }
}