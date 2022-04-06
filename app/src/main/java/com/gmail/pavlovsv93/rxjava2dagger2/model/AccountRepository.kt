package com.gmail.pavlovsv93.rxjava2dagger2.model

import android.os.Handler
import android.os.Looper
import com.gmail.pavlovsv93.rxjava2dagger2.R
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface AccountRepositoryInterface {
	fun getAllLocalAccount(): List<LoginEntity>
	fun getAuthorization(login: String, password: String, callback: Callback<LoginEntity>)
	fun getAllAccount(callback: Callback<List<LoginEntity>>)
	fun deleteAccount(account: LoginEntity, callback: Callback<LoginEntity>)
	fun updateAccount(
		account: LoginEntity,
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

	fun getCheckedLogin(login: String, email: String) : Boolean
}

class AccountRepository(private val localDataSource: LoginDAO) : AccountRepositoryInterface {

	private val executor: Executor = Executors.newSingleThreadExecutor()
	private val handler = Handler(Looper.getMainLooper())

	override fun getAllLocalAccount(): List<LoginEntity> = localDataSource.getAllAccountData()

	override fun getAuthorization(
		login: String,
		password: String,
		callback: Callback<LoginEntity>
	) {
		executor.execute {
			try {
				var index: Int? = null
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == login && localList[i].password == password) {
						index = i
						break
					}
				}
				handler.post {
					index?.let {
						callback.onSuccess(localList[index])
					} ?: callback.onSuccess(null)
				}
			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
	}

	override fun getAllAccount(callback: Callback<List<LoginEntity>>) {
		executor.execute {
			try {
				val result: List<LoginEntity> = getAllLocalAccount()
				Thread.sleep(3000)
				handler.post {
					callback.onSuccess(result)
				}
			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
	}

	override fun deleteAccount(account: LoginEntity, callback: Callback<LoginEntity>) {

		executor.execute {
			try {
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == account.login && localList[i].email == account.email) {
						localDataSource.deleteAccount(localList[i])
						break
					}
				}
				handler.post {
					callback.onSuccess(null)
				}

			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
		val localList: List<LoginEntity> = getAllLocalAccount()
		for (i in localList.indices) {
			if (localList[i].login == account.login && localList[i].email == account.email) {
				localDataSource.deleteAccount(localList[i])
			}
		}
	}

	override fun updateAccount(
		account: LoginEntity,
		password: String?,
		email: String?,
		callback: Callback<LoginEntity>
	) {
		executor.execute {
			try {
				var updateData: LoginEntity? = null
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == account.login) {
						updateData = localList[i]
						localDataSource.updateAccount(localList[i])
						break
					}
				}
				if (password != null) {
					updateData?.password = password
				}
				if (email != null) {
					updateData?.email = email
				}
				if (updateData != null) {
					localDataSource.updateAccount(updateData)
				}
				handler.post {
					if (updateData != null) {
						callback.onSuccess(updateData)
					}
				}

			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
	}

	override fun insertAccount(
		login: String,
		password: String,
		email: String,
		callback: Callback<LoginEntity>
	) {
		executor.execute {
			try {
				Thread.sleep(3000)
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					when {
						localList[i].login == login -> {
							handler.post {
								callback.onError((R.string.error_login).toString())
							}
						}
						localList[i].email == email -> {
							handler.post {
								callback.onError((R.string.email_error).toString())
							}
						}
						else -> {
							val newAccount =
								LoginEntity(login = login, password = password, email = email)
							localDataSource.registration(newAccount)
							handler.post {
								callback.onSuccess(newAccount)
							}
						}
					}
				}
			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
	}

	override fun getCheckedLogin(login: String, email: String): Boolean {
		var index: Int? = null
		val localList: List<LoginEntity> = getAllLocalAccount()
		for (i in localList.indices) {
			if (localList[i].login == login || localList[i].email == email) {
				index = i
				break
			}
		}
		return index != null
	}


}
