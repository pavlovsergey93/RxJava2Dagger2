package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.Subscriptions

interface ForgotPasswordViewModelInterface {
	val progressState: Subscriptions<Boolean>
	val successState: Subscriptions<LoginEntity>
	val errorMessage: Subscriptions<String?>

	fun findAccount(data: String)
}