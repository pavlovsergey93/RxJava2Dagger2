package com.gmail.pavlovsv93.rxjava2dagger2

import com.gmail.pavlovsv93.rxjava2dagger2.model.LoginEntity

class RegistrationContract {
	interface RegistrationViewInterface{
		fun showProgress()
		fun hideProgress()
		fun showError(error: String)
		fun showEmpty()
		fun showSaved()
		fun checkedAccount(result : Boolean?)
	}

	interface RegistrationPresenterInterface{
		fun onAttachView(attachView: RegistrationContract.RegistrationViewInterface)
		fun onCheckedAccount(login: String, email: String)
		fun onInsertAccount(login: String, password: String, email: String)
		fun onUpdateAccount(account: LoginEntity, password: String?, email: String?)
	}
}