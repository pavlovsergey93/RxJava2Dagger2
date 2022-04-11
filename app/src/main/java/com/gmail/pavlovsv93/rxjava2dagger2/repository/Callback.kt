package com.gmail.pavlovsv93.rxjava2dagger2.repository

interface Callback <T> {
	fun onSuccess(result: T?)
	fun onError(error: String)
}