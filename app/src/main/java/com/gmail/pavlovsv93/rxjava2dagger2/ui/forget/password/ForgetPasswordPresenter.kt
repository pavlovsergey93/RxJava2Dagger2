package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import com.gmail.pavlovsv93.rxjava2dagger2.domain.AccountRepositoryInterface
import com.gmail.pavlovsv93.rxjava2dagger2.domain.Callback
import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity

class ForgetPasswordPresenter(
	private var view: ForgetPasswordContract.ForgetPasswordViewInterface,
	private val repo: AccountRepositoryInterface
) : ForgetPasswordContract.ForgetPasswordPresenterInterface {

	override fun findAccount(data: String) {
		view.showProgress()
		repo.findAccount(data, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				view.hideProgress()
				result?.let { view.setDataAccount(it) }
			}

			override fun onError(error: String) {
				view.setError(error)
				view.hideProgress()
			}
		})
	}
}