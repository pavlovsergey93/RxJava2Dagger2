package com.gmail.pavlovsv93.rxjava2dagger2.domain

import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.Callback

interface AccountRepositoryInterface {
	fun getAllLocalAccount(): List<LoginEntity>
	fun getAuthorization(login: String, password: String, callback: Callback<LoginEntity>)
	fun getAllAccount(callback: Callback<List<LoginEntity>>)
	fun deleteAccount(login: String, callback: Callback<LoginEntity>)
	fun updateAccount(
		login: String,
		password: String? = null,
		email: String? = null,
		callback: Callback<LoginEntity>
	)

	fun insertAccount(
		login: String,
		password: String,
		email: String,
		callback: Callback<LoginEntity>
	)

	fun getCheckedLogin(login: String, email: String, callback: Callback<Boolean>)
	fun getAccount(login: String, callback: Callback<LoginEntity>)
	fun findAccount(data: String, callback: Callback<LoginEntity>)
}
