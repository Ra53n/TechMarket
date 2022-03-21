package com.example.techmarket.view

import android.text.TextUtils

fun validateForm(email: String, password: String) =
    !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
