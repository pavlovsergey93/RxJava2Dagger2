package com.gmail.pavlovsv93.rxjava2dagger2.ui.registration

import com.gmail.pavlovsv93.rxjava2dagger2.domain.AccountRepositoryInterface
import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.domain.Callback
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage

class RegistrationPresenter(
	private var view: RegistrationContract.RegistrationViewInterface,
	private val repo: AccountRepositoryInterface
) : RegistrationContract.RegistrationPresenterInterface {

	override fun onInsertAccount(login: String, password: String, email: String) {
		view.showProgress()
		repo.insertAccount(login, password, email, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view.hideProgress()
				view.showSaved(ExceptionMessage.E201.message)
			}

			override fun onError(error: String) {
				view.showError(error)
				view.hideProgress()
			}
		})
	}

	override fun onUpdateAccount(login: String, password: String?, email: String?) {
		view.showProgress()
		repo.updateAccount(login, password, email, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view.hideProgress()
				result?.let {
					view.showSaved(ExceptionMessage.E201.message)
				}
			}

			override fun onError(error: String) {
				view.hideProgress()
				view.showError(error)
			}
		})
	}

	override fun getDataAccount(login: String) {
		repo.getAccount(login, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view.hideProgress()
				result?.let { view.setView(result) }
			}

			override fun onError(error: String) {
				view.hideProgress()
				view.showError(error)
			}
		})
	}

	override fun onCheckedAccount(login: String, email: String) {
		view.showProgress()
		val result: Boolean = repo.getCheckedLogin(login, email)
		view.checkedAccount(result)
		view.hideProgress()
	}
}