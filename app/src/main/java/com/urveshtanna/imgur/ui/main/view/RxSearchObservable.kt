package com.urveshtanna.imgur.ui.main.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxSearchObservable {

    companion object {

        @JvmStatic
        public fun fromView(searchView: SearchView?): Observable<String> {

            val publishSubject = PublishSubject.create<String>()
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    publishSubject.onComplete()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null) {
                        publishSubject.onNext("")
                    } else {
                        publishSubject.onNext(newText)
                    }
                    return true
                }

            })

            return publishSubject
        }

        @JvmStatic
        public fun fromView(searchView: EditText): Observable<String> {

            val publishSubject = PublishSubject.create<String>()
            searchView.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(p0: Editable?) {
                    publishSubject.onNext(searchView.text.toString())
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

            })

            return publishSubject
        }

    }

}