package com.gmail.pavlovsv93.rxjava2dagger2.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
	@PrimaryKey(autoGenerate = true)
	val uid: Int?,
	@ColumnInfo(name = "LOGIN")
	val login: String?,
	@ColumnInfo(name = "PASSWORD")
	var password: String?,
	@ColumnInfo(name = "E-MAIL")
	var email: String?
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(uid)
		parcel.writeString(login)
		parcel.writeString(password)
		parcel.writeString(email)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<LoginEntity> {
		override fun createFromParcel(parcel: Parcel): LoginEntity {
			return LoginEntity(parcel)
		}

		override fun newArray(size: Int): Array<LoginEntity?> {
			return arrayOfNulls(size)
		}
	}
}