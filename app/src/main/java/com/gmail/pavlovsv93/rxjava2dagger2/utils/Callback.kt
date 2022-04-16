package com.gmail.pavlovsv93.rxjava2dagger2.utils

interface Callback <T> {
	fun onSuccess(result: T?)
	fun onError(error: String)
}