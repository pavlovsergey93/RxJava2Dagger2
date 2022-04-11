package com.gmail.pavlovsv93.rxjava2dagger2.domain

import androidx.room.*
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity

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