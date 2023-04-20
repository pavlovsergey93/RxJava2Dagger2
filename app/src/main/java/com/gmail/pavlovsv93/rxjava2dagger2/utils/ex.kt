package com.gmail.pavlovsv93.rxjava2dagger2.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBarNoAction(
	text: String,
	lenght: Int = Snackbar.LENGTH_SHORT
) {
	Snackbar.make(this, text, lenght).show()
}

fun Activity.hideKeyboard(activity: Activity) {
	val imm: InputMethodManager =
		activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
	//Find the currently focused view, so we can grab the correct window token from it.
	var view: View? = activity.currentFocus
	//If no view currently has focus, create a new one, just so we can grab a window token from it
	if (view == null) {
		view = View(activity)
	}
	imm.hideSoftInputFromWindow(view.windowToken, 0)
}