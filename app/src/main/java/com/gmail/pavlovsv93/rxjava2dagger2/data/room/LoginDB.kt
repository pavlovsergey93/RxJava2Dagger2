package com.gmail.pavlovsv93.rxjava2dagger2.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LoginEntity::class], version = 1)
abstract class LoginDB : RoomDatabase() {
	abstract fun loginDao(): LoginDAO
}