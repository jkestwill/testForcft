package com.jkestwill.test.ui.utils

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = doOnTextChanged { text, start, before, count -> trySend(text) }

        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}