package com.gmail.pavlovsv93.rxjava2dagger2.ui.login

import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.repository.*
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage

class LoginPresenter(
	private val view: LoginContract.LoginViewInterface,
	private val repo: AccountRepositoryInterface
) : LoginContract.LoginPresenterInterface {

	override fun onAuthorization(login: String, password: String) {
		view.showProgress()
		repo.getAuthorization(login, password, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view.hideProgress()
				if (result != null) {
					view.showLayoutAccount(result)
				} else {
					view.showLayoutSing()
					view.setError(ExceptionMessage.E401.message)
				}
			}

			override fun onError(error: String) {
				view.showLayoutSing()
				view.setError(error)
			}
		})
	}

	override fun onDeleteAccount(login: String) {
		view.showProgress()
		repo.deleteAccount(login, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view.showLayoutSing()
				view.setMessageState(ExceptionMessage.E202.message)
			}

			override fun onError(error: String) {
				view.showLayoutSing()
				view.setError(error)
			}
		})
	}
}

