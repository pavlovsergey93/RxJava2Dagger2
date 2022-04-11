package com.gmail.pavlovsv93.rxjava2dagger2.ui.login

import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage

class LoginContract {
	interface LoginViewInterface {
		fun showProgress()
		fun hideProgress()
		fun setCheckedSing()
		fun setError(error: String)
		fun setRegistration()
		fun showLayoutSing()
		fun showLayoutAccount(account: LoginEntity)
		fun setMessageState(massage: String)
		fun showMessage(code: ExceptionMessage)
	}

	interface LoginPresenterInterface {
		fun onAuthorization(login: String, password: String)
		fun onDeleteAccount(login: String)
	}
}