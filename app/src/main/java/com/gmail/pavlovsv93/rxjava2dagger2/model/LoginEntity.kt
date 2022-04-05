package com.gmail.pavlovsv93.rxjava2dagger2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
	@PrimaryKey(autoGenerate = true)
	val uid: Int? = 0,
	@ColumnInfo(name = "LOGIN")
	val login: String,
	@ColumnInfo(name = "PASSWORD")
	var password: String,
	@ColumnInfo(name = "E-MAIL")
	var email: String
)