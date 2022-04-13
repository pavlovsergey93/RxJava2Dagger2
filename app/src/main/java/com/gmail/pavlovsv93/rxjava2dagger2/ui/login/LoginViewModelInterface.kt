package com.gmail.pavlovsv93.rxjava2dagger2.ui.login

import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.Subscriptions

interface LoginViewModelInterface {
	val progressState: Subscriptions<Boolean>
	val showLayoutState: Subscriptions<Boolean>
	val successState: Subscriptions<LoginEntity>
	val errorMessage: Subscriptions<String?>

	fun onAuthorization(login: String, password: String)
	fun onDeleteAccount(login: String)
}