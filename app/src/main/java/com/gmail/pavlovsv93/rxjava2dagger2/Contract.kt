package com.gmail.pavlovsv93.rxjava2dagger2

class Contract {
	interface LoginViewInterface {
		fun showProgress()
		fun hideProgress()
		fun setCheckedSing()
		fun setError(error: String)
		fun showRegistration(message: String)
		fun setRegistration()
	}

	interface LoginPresenterInterface {
		fun onAttachView(attachView: LoginViewInterface)
		fun onAuthorization(login: String, password: String): Boolean
		fun onRegistration()
		fun onForgotPassword()
	}
}