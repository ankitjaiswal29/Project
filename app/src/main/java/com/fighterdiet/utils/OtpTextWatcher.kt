package com.fighterdiet.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class OtpTextWatcher(val etNext: EditText,  val etPrev:EditText) :TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(editable: Editable?) {
        var text=editable.toString()
        if (text.length==1){
             etNext.requestFocus()
        }else if (text.length == 0){
            etPrev.requestFocus()
        }

    }
}