package com.gmail.pavlovsv93.rxjava2dagger2.presenter

import com.gmail.pavlovsv93.rxjava2dagger2.Contract
import com.gmail.pavlovsv93.rxjava2dagger2.model.AccountRepository
import com.gmail.pavlovsv93.rxjava2dagger2.model.AccountRepositoryInterface
import com.gmail.pavlovsv93.rxjava2dagger2.model.AppDB

class LoginPresenter : Contract.LoginPresenterInterface {

	private var view: Contract.LoginViewInterface? = null
	private val repo: AccountRepositoryInterface = AccountRepository(AppDB.getLoginDao())


	override fun onAttachView(AttachView: Contract.LoginViewInterface) {
		this.view = AttachView
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