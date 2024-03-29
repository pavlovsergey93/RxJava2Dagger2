package com.gmail.pavlovsv93.rxjava2dagger2.data

import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginDAO
import com.gmail.pavlovsv93.rxjava2dagger2.domain.AccountRepositoryInterface
import com.gmail.pavlovsv93.rxjava2dagger2.utils.Callback
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AccountRepository(private val localDataSource: LoginDAO) : AccountRepositoryInterface {

	private val executor: Executor = Executors.newSingleThreadExecutor()
	//private val handler = Handler(Looper.getMainLooper())

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
//				handler.post {
				index?.let {
					callback.onSuccess(localList[index])
				} ?: callback.onSuccess(null)
//				}
			} catch (exc: Exception) {
//				handler.post {
				callback.onError(exc.toString())
//				}
			}
		}
	}

	override fun getAllAccount(callback: Callback<List<LoginEntity>>) {
		executor.execute {
			try {
				val result: List<LoginEntity> = getAllLocalAccount()
				Thread.sleep(3000)
//				handler.post {
				callback.onSuccess(result)
//				}
			} catch (exc: Exception) {
//				handler.post {
				callback.onError(exc.toString())
//				}
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
//				handler.post {
				index?.let {
					localDataSource.deleteAccount(localList[it])
					callback.onSuccess(null)
				} ?: throw IllegalArgumentException(ExceptionMessage.E405.message)
//				}

			} catch (exc: Exception) {
//				handler.post {
				callback.onError(exc.toString())
//				}
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
						throw IllegalArgumentException(ExceptionMessage.E406.message)
					}
				}
//				handler.post {
				index?.let {
					localDataSource.updateAccount(localList[index])
					callback.onSuccess(localList[index])
				} ?: throw IllegalArgumentException(ExceptionMessage.E407.message)
//				}
			} catch (exc: Exception) {
//				handler.post {
				callback.onError(exc.toString())
//				}
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
						throw IllegalArgumentException(ExceptionMessage.E402.message)
					}
					if (localList[i].email == email) {
						throw IllegalArgumentException(ExceptionMessage.E403.message)
					}
				}
				val newAccount =
					LoginEntity(uid = null, login = login, password = password, email = email)
//				handler.post {
				localDataSource.registration(newAccount)
				callback.onSuccess(newAccount)
//				}
			} catch (exc: Exception) {
//				handler.post {
				callback.onError(exc.toString())
//				}
			}
		}
	}

	override fun getCheckedLogin(login: String, email: String, callback: Callback<Boolean>) {
		executor.execute {
			try {
				var index: Int? = null
				val localList: List<LoginEntity> = getAllLocalAccount()
				for (i in localList.indices) {
					if (localList[i].login == login || localList[i].email == email) {
						index = i
						break
					}
				}
				when(index){
					null -> callback.onSuccess(false)
					else -> callback.onSuccess(true)
				}
			} catch (exc: Exception) {
				callback.onError(exc.toString())
			}
		}
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
//				handler.post {
				index?.let { callback.onSuccess(localList[index]) }
					?: throw IllegalArgumentException(ExceptionMessage.E408.message)
//				}
			} catch (exc: Exception) {
//				handler.post {
				callback.onError(exc.toString())
//				}
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
				if (index == null) {
					throw IllegalArgumentException(ExceptionMessage.E408.message)
				}
//				handler.post {
				index.let { callback.onSuccess(localList[index]) }
//				}
			} catch (exc: Exception) {
//				handler.post {
				callback.onError(exc.toString())
//				}
			}
		}
	}
}
