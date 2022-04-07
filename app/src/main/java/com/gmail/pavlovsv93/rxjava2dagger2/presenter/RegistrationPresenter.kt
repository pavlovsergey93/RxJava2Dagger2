package com.gmail.pavlovsv93.rxjava2dagger2.presenter

import com.gmail.pavlovsv93.rxjava2dagger2.LoginContract
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.RegistrationContract
import com.gmail.pavlovsv93.rxjava2dagger2.model.*

class RegistrationPresenter(private var view: RegistrationContract.RegistrationViewInterface?) : RegistrationContract.RegistrationPresenterInterface {

	private val repo: AccountRepositoryInterface = AccountRepository(AppDB.getLoginDao())

	override fun onInsertAccount(login: String, password: String, email: String) {
		view?.showProgress()
		repo.insertAccount(login, password, email, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view?.hideProgress()
				view?.showSaved()
			}

			override fun onError(error: String) {
				view?.showError(error)
				view?.hideProgress()
			}
		})
	}


	override fun onUpdateAccount(login: String, password: String?, email: String?) {
		view?.showProgress()
		repo.updateAccount(login, password, email, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view?.hideProgress()
				result?.let {
					view?.showSaved()
				}
			}

			override fun onError(error: String) {
				view?.hideProgress()
				view?.showError(error)
			}
		})
	}

	override fun getDataAccount(login: String){
		repo.getAccount(login, object : Callback<LoginEntity>{
			override fun onSuccess(result: LoginEntity?) {
				view?.hideProgress()
				result?.let { view?.setView(result) }
			}

			override fun onError(error: String) {
				view?.hideProgress()
				view?.showError(error)
			}
		})
	}

	override fun onCheckedAccount(login: String, email: String) {
		view?.showProgress()
		val result: Boolean = repo.getCheckedLogin(login, email)
		view?.checkedAccount(result)
		view?.hideProgress()
	}
}