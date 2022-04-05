package com.gmail.pavlovsv93.rxjava2_dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container, LoginFragment.newInstance())
			.commit()
	}
}