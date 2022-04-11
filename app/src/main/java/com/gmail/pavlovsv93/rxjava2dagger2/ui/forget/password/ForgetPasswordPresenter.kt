package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import com.gmail.pavlovsv93.rxjava2dagger2.repository.AccountRepository
import com.gmail.pavlovsv93.rxjava2dagger2.AppDB
import com.gmail.pavlovsv93.rxjava2dagger2.repository.Callback
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity

class ForgetPasswordPresenter(private var view: ForgetPasswordContract.ForgetPasswordViewInterface) :
	ForgetPasswordContract.ForgetPasswordPresenterInterface {

	private val repo: AccountRepository = AccountRepository(AppDB.getLoginDao())

	override fun findAccount(data: String) {
		view?.showProgress()
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