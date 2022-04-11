package com.gmail.pavlovsv93.rxjava2dagger2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {
	private var loginFragmentFlag: Boolean = false

	companion object {
		const val SAVE_FRAGMENT_TAG = "SAVE_FRAGMENT_TAG"
		const val SAVE_FRAGMENT_ARG = "SAVE_FRAGMENT_ARG"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		if(savedInstanceState != null){
			loginFragmentFlag = savedInstanceState.getBoolean(SAVE_FRAGMENT_ARG)
		}

		if (loginFragmentFlag) {
			supportFragmentManager.findFragmentByTag(SAVE_FRAGMENT_TAG) as LoginFragment
		} else {
			loginFragmentFlag = true
			supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container, LoginFragment.newInstance(), SAVE_FRAGMENT_TAG)
				.commit()
		}
//		supportFragmentManager.beginTransaction()
//			.replace(R.id.fragment_container, LoginFragment.newInstance())
//			.commit()

	}

	override fun onRetainCustomNonConfigurationInstance(): Any? {
		return this
	}

	private fun restoreFragment(): LoginFragment {
		val fragment = getLastCustomNonConfigurationInstance() as? LoginFragment
		return fragment ?: LoginFragment()
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putBoolean(SAVE_FRAGMENT_ARG, loginFragmentFlag)
	}
}