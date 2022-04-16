package com.gmail.pavlovsv93.rxjava2dagger2

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginDAO
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginDB
import com.gmail.pavlovsv93.rxjava2dagger2.data.AccountRepository
import com.gmail.pavlovsv93.rxjava2dagger2.domain.AccountRepositoryInterface

class App : Application() {

	val repo: AccountRepositoryInterface by lazy {
		AccountRepository(getLoginDao())
	}

	override fun onCreate() {
		super.onCreate()
		instance = this
	}

	companion object {
		private var instance: App? = null
		private var db: LoginDB? = null
		private const val NAME_DB = "ACCOUNT"

		private fun getLoginDao(): LoginDAO {
			if (db == null) {
				synchronized(LoginDB::class.java) {
					if (instance == null) {
						throw IllegalAccessException("Application is null")
					}
					db = Room.databaseBuilder(
						instance!!.applicationContext,
						LoginDB::class.java,
						NAME_DB
					)
						.allowMainThreadQueries()
						.build()
				}
			}
			return db!!.loginDao()
		}
	}
}

val Context.app: App
	get() = this.applicationContext as App