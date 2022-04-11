package com.gmail.pavlovsv93.rxjava2dagger2.utils

import com.gmail.pavlovsv93.rxjava2dagger2.R

enum class ExceptionMessage(val message: String) {

	E201(R.string.saved_registration.toString()),
	E202(R.string.delete_account_state.toString()),
	E400(R.string.error_login_or_email.toString()),
	E401(R.string.error.toString()),
	E402(R.string.error_login.toString()),
	E403(R.string.email_error.toString()),
	E404(R.string.error_registration.toString()),
	E405(R.string.error_delete.toString()),
	E406(R.string.error_email_empty.toString()),
	E407(R.string.error_update.toString()),
	E408(R.string.error_empty_data.toString()),
	E409(R.string.error_password.toString()),
	E410(R.string.error_empty.toString())
}
