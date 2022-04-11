package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity

class ForgetPasswordContract {
	interface ForgetPasswordViewInterface{
		fun showProgress()
		fun hideProgress()
		fun setDataAccount(account: LoginEntity)
		fun setError(error: String)
	}

	interface ForgetPasswordPresenterInterface{
		fun findAccount(data: String)
	}
}