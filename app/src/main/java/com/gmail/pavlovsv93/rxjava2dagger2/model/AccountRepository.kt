package com.gmail.pavlovsv93.rxjava2dagger2.model

import android.os.Handler
import android.os.Looper
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.Executors

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

	fun getCheckedLogin(login: String, email: String): Boolean
	fun getAccount(login: String, callback: Callback<LoginEntity>)
	fun findAccount(data: String, callback: Callback<LoginEntity>)
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

	override fun deleteAccount(login: String, callback: Callback<LoginEntity>) {
		executor.execute {
			try {
				var index: Int? = null
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == login) {
						index = i
						break
					}
				}
				handler.post {
					index?.let {
						localDataSource.deleteAccount(localList[it])
						callback.onSuccess(null)
					} ?: throw IllegalArgumentException("Ошибка удаления")
				}

			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
	}

	override fun updateAccount(
		login: String,
		password: String?,
		email: String?,
		callback: Callback<LoginEntity>
	) {
		executor.execute {
			try {
				var index: Int? = null
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == login) {
						index = i
						break
					}
				}
				if (index != null) {
					if (password != "") {
						localList[index].password = password
					}
					if (email != "") {
						localList[index].email = email
					} else {
						throw IllegalArgumentException("Email не задан или используется")
					}
				}
				handler.post {
					index?.let {
						localDataSource.updateAccount(localList[index])
						callback.onSuccess(localList[index])
					} ?: throw IllegalArgumentException("Ошибка обновления")
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
					if (localList[i].login == login) {
						throw IllegalArgumentException("Такой логин уже существует")
					}
					if (localList[i].email == email) {
						throw IllegalArgumentException("На этот E-mail зарегистрирован аккаунт")
					}
				}
				val newAccount =
					LoginEntity(uid = null, login = login, password = password, email = email)
				handler.post {
					localDataSource.registration(newAccount)
					callback.onSuccess(newAccount)
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

	override fun getAccount(login: String, callback: Callback<LoginEntity>) {
		executor.execute {
			try {
				var index: Int? = null

				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == login) {
						index = i
						break
					}
				}
				handler.post {
					index?.let { callback.onSuccess(localList[index]) }
						?: throw IllegalArgumentException("Данные не найдены")
				}
			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
	}

	override fun findAccount(data: String, callback: Callback<LoginEntity>) {
		executor.execute {
			try {
				var index: Int? = null
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == data || localList[i].email == data) {
						index = i
						break
					}
				}
				if (index == null){
					throw IllegalArgumentException("Данные не найдены")
				}
				handler.post {
					index.let { callback.onSuccess(localList[index]) }
				}
			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
	}
}
