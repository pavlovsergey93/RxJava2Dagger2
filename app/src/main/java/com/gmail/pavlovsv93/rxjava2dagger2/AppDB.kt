package com.gmail.pavlovsv93.rxjava2dagger2

import android.app.Application
import androidx.room.Room
import com.gmail.pavlovsv93.rxjava2dagger2.domain.LoginDAO
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginDB

class AppDB : Application() {

	override fun onCreate() {
		super.onCreate()
		instance = this
	}

	companion object {
		private var instance: AppDB? = null
		private var db: LoginDB? = null
		private const val NAME_DB = "ACCOUNT"

		fun getLoginDao(): LoginDAO {
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