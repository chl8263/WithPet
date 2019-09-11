package com.example.withpet.util

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher : TextWatcher {

    var onAfterTextChanged: ((s: Editable?) -> Unit)? = null
    var onBeforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null
    var onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null

    override fun afterTextChanged(s: Editable?) {
        onAfterTextChanged?.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        onBeforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

}
