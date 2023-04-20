package com.gmail.pavlovsv93.rxjava2dagger2.data

import com.gmail.pavlovsv93.rxjava2dagger2.domain.AccountApi

class AccountApiImpl : AccountApi {
	override fun singIn(login: String, password: String): Boolean {
		TODO("Not yet implemented")
	}

	override fun registration(login: String, password: String, email: String): Boolean {
		TODO("Not yet implemented")
	}

	override fun singOut(): Boolean {
		TODO("Not yet implemented")
	}

	override fun forgetPassword(login: String): Boolean {
		TODO("Not yet implemented")
	}
}