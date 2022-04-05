package com.gmail.pavlovsv93.rxjava2dagger2.presenter

import com.gmail.pavlovsv93.rxjava2dagger2.Contract

class LoginPresenter: Contract.LoginPresenterInterface {



	override fun onAttachView(view: Contract.LoginViewInterface) {
		TODO("Not yet implemented")
	}

	override fun onAuthorization(login: String, password: String): Boolean {
		TODO("Not yet implemented")
	}

	override fun onRegistration() {
		TODO("Not yet implemented")
	}

	override fun onForgotPassword() {
		TODO("Not yet implemented")
	}
}