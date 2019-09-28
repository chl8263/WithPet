package com.example.withpet.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * CharSequence 형태로 리턴해준다.
 */
fun AppCompatActivity.getText(text: Any?): CharSequence? {
    text?.let {
        if (it is CharSequence) return it
        return if (it is Int) this.getString(it) else it.toString()
    } ?: return null
}

/**
 *  EditText 변화감지
 */
inline fun EditText.afterTextChanged(crossinline block: (editTable: Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            block.invoke(p0)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}


/**
 * RX extensions
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.with(): Single<T> =
        this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())


/**
 * RX extensions
 * Use SchedulerProvider configuration for Flowable
 */
fun <T> Observable<T>.with(): Observable<T> =
        this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

/**
 * RX extensions
 * loading
 */
fun <T> Single<T>.progress(isProgress: MutableLiveData<Boolean>): Single<T> =
        this.doOnSubscribe { isProgress.postValue(true) }
                .doOnSuccess { isProgress.postValue(false) }
                .doOnError { isProgress.postValue(false) }
