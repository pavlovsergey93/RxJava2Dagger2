package com.gmail.pavlovsv93.rxjava2dagger2.presenter

import com.gmail.pavlovsv93.rxjava2dagger2.LoginContract
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.model.*

class LoginPresenter : LoginContract.LoginPresenterInterface {

	private var view: LoginContract.LoginViewInterface? = null
	private val repo: AccountRepositoryInterface = AccountRepository(AppDB.getLoginDao())
	private var flag = false

	override fun onAttachView(attachView: LoginContract.LoginViewInterface) {
		this.view = attachView
	}


	override fun onAuthorization(login: String, password: String) {
		view?.showProgress()
		repo.getAuthorization(login, password, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view?.hideProgress()
				if (result != null) {
					view?.showLayoutAccount(result)
				} else {
					view?.showLayoutSing()
					view?.setError(R.string.error.toString())
				}
			}

			override fun onError(error: String) {
				view?.showLayoutSing()
				view?.setError(error)
			}

		})
	}

	override fun onDeleteAccount(login: String) {
		view?.showProgress()
		repo.deleteAccount(login, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view?.showLayoutSing()
				view?.setMessageState(R.string.delete_account_state.toString())
			}

			override fun onError(error: String) {
				view?.showLayoutSing()
				view?.setError(error)
			}
		})
	}


}

