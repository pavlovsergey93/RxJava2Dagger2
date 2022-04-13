package com.gmail.pavlovsv93.rxjava2dagger2.ui

import com.gmail.pavlovsv93.rxjava2dagger2.domain.AccountRepositoryInterface
import com.gmail.pavlovsv93.rxjava2dagger2.domain.Callback
import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password.ForgotPasswordViewModelInterface
import com.gmail.pavlovsv93.rxjava2dagger2.ui.login.LoginViewModelInterface
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage
import com.gmail.pavlovsv93.rxjava2dagger2.utils.Subscriptions

class ViewModel(
	private val repo: AccountRepositoryInterface,
) : ForgotPasswordViewModelInterface, LoginViewModelInterface {
	override val showLayoutState: Subscriptions<Boolean> = Subscriptions()
	override val progressState: Subscriptions<Boolean> = Subscriptions()
	override val successState: Subscriptions<LoginEntity> = Subscriptions()
	override val errorMessage: Subscriptions<String?> = Subscriptions()

	//todo ForgotPasswordViewModelInterface
	override fun findAccount(data: String) {
		progressState.post(true)
		repo.findAccount(data, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				progressState.post(false)
				result?.let { result ->
					successState.post(result)
				}
				errorMessage.post(null)
			}

			override fun onError(error: String) {
				progressState.post(false)
				successState.post(null)
				errorMessage.post(error)
			}
		})
	}

	// todo LoginViewModelInterface
	override fun onAuthorization(login: String, password: String) {
		progressState.post(true)
		repo.getAuthorization(login, password, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				progressState.post(false)
				if (result != null) {
					showLayoutState.post(true)
					successState.post(result)
				} else {
					showLayoutState.post(false)
					errorMessage.post(ExceptionMessage.E401.message)
				}
			}

			override fun onError(error: String) {
				showLayoutState.post(false)
				errorMessage.post(error)
			}
		})
	}

	override fun onDeleteAccount(login: String) {
		progressState.post(true)
		repo.deleteAccount(login, object : Callback<LoginEntity> {
			override fun onSuccess(result: LoginEntity?) {
				showLayoutState.post(false)
				progressState.post(false)
				errorMessage.post(ExceptionMessage.E202.message)
			}

			override fun onError(error: String) {
				progressState.post(false)
				showLayoutState.post(false)
				errorMessage.post(error)
			}
		})
	}
}