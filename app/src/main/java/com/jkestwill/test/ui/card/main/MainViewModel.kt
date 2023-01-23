package com.jkestwill.test.ui.card.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkestwill.test.db.model.CardLocalOutput
import com.jkestwill.test.model.Card
import com.jkestwill.test.repository.CardRepository
import com.jkestwill.test.repository.LocalCardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private var cardRepository: CardRepository,
    private var localCardRepository: LocalCardRepository
) : ViewModel() {

    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> get() = _card

    private var _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    private var _cardHistory:LiveData<List<CardLocalOutput>> = MutableLiveData()
    val cardHistory: LiveData<List<CardLocalOutput>> get() =_cardHistory


    fun search(num: String) {
        viewModelScope.launch {
            cardRepository.search(num)
                .onSuccess {
                    _card.postValue(it)
                    localCardRepository.insert(it,num)
                }
                .onFailure {
                    _error.postValue(it)
                }
        }
    }

    fun getHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _cardHistory=localCardRepository.getAll()
        }
    }


}