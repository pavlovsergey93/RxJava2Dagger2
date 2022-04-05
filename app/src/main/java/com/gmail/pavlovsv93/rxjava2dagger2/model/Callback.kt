package com.gmail.pavlovsv93.rxjava2dagger2.model

interface Callback <T> {
	fun onSuccess(result: T?)
	fun onError(error: String)
}