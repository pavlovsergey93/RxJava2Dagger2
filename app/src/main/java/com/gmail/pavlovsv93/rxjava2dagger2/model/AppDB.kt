package com.gmail.pavlovsv93.rxjava2dagger2.model

import android.app.Application
import androidx.room.Room

class AppDB : Application() {

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
					if (db == null && instance != null) {
						db = Room.databaseBuilder(instance!!.applicationContext,LoginDB::class.java,NAME_DB)
							.allowMainThreadQueries()
							.build()
					}
				}
			}
			return db!!.loginDao()
		}
	}

	override fun onCreate() {
		super.onCreate()
		instance = this
	}
}