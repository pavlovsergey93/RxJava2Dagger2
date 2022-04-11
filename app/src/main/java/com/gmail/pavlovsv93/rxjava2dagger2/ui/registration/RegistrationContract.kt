package com.gmail.pavlovsv93.rxjava2dagger2.ui.registration

import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage

class RegistrationContract {
	interface RegistrationViewInterface{
		fun showProgress()
		fun hideProgress()
		fun showError(error: String)
		fun showSaved(message: String)
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