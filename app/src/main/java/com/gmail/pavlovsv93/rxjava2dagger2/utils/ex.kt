package com.gmail.pavlovsv93.rxjava2dagger2.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBarNoAction(
	text: String,
	lenght: Int = Snackbar.LENGTH_SHORT
) {
	Snackbar.make(this, text, lenght).show()
}