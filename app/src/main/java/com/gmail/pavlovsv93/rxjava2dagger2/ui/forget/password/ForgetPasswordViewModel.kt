package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import com.gmail.pavlovsv93.rxjava2dagger2.domain.AccountRepositoryInterface
import com.gmail.pavlovsv93.rxjava2dagger2.domain.Callback
import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.Subscriptions

class ForgetPasswordViewModel(
	private val repo: AccountRepositoryInterface
) : ForgotPasswordViewModelInterface {
	override val progressState: Subscriptions<Boolean> = Subscriptions()
	override val successState: Subscriptions<LoginEntity> = Subscriptions()
	override val errorMessage: Subscriptions<String?> = Subscriptions()

	override fun findAccount(data: String) {
		progressState.post(true)
		repo.findAccount(data, object : Callback<LoginEntity>{
			override fun onSuccess(result: LoginEntity?) {
				progressState.post(false)
				result?.let { result->
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
}