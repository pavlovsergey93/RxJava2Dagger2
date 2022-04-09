package com.gmail.pavlovsv93.rxjava2dagger2

import com.gmail.pavlovsv93.rxjava2dagger2.model.LoginEntity

class RegistrationContract {
	interface RegistrationViewInterface{
		fun showProgress()
		fun hideProgress()
		fun showError(error: String)
		fun showSaved()
		fun checkedAccount(result : Boolean?)
		fun setView(account: LoginEntity)
	}

	interface RegistrationPresenterInterface{
		fun onCheckedAccount(login: String, email: String)
		fun onInsertAccount(login: String, password: String, email: String)
		fun onUpdateAccount(login: String, password: String?, email: String?)
		fun getDataAccount(login: String)
	}
}