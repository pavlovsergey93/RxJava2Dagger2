package com.gmail.pavlovsv93.rxjava2dagger2.ui.registration

import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.Subscriptions

interface RegistrationViewModelInterface {
	val progressState: Subscriptions<Boolean>
	val accountCheckState: Subscriptions<Boolean>
	val successState: Subscriptions<LoginEntity>
	val errorMessage: Subscriptions<String?>

	fun onCheckedAccount(login: String, email: String)
	fun onInsertAccount(login: String, password: String, email: String)
	fun onUpdateAccount(login: String, password: String?, email: String?)
	fun getDataAccount(login: String)
}