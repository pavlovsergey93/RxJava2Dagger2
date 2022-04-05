package com.gmail.pavlovsv93.rxjava2dagger2.model

import androidx.room.*

@Dao
interface LoginDAO {
	@Insert
	fun registration(vararg user: LoginEntity)

	@Update
	fun updateAccount(user: LoginEntity)

	@Delete
	fun deleteAccount(user: LoginEntity)

	@Query("SELECT * FROM LoginEntity")
	fun getAllAccountData(): List<LoginEntity>
}