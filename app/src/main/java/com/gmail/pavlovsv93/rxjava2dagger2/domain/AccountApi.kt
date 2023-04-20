package com.gmail.pavlovsv93.rxjava2dagger2.domain

interface AccountApi {
	fun singIn(login: String, password: String): Boolean
	fun registration(login: String, password: String, email: String): Boolean
	fun singOut(): Boolean
	fun forgetPassword(login: String): Boolean
}