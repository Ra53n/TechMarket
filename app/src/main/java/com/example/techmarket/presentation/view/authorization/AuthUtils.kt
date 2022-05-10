package com.example.techmarket.presentation.view.authorization

import android.text.TextUtils

fun validateForm(email: String, password: String) =
    !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
