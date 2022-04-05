package com.gmail.pavlovsv93.rxjava2dagger2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
	@PrimaryKey(autoGenerate = true)
	val uid: Int,
	@ColumnInfo(name = "LOGIN")
	val login: String,
	@ColumnInfo(name = "PASSWORD")
	val password: String,
	@ColumnInfo(name = "E-MAIL")
	val email: String
)