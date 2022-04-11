package com.gmail.pavlovsv93.rxjava2dagger2.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.app
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentRegistrationBinding
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.ui.login.LoginFragment
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class RegistrationFragment : Fragment(), RegistrationContract.RegistrationViewInterface {

	private var _binding: FragmentRegistrationBinding? = null
	private val binding get() = _binding!!
	private val presenter: RegistrationContract.RegistrationPresenterInterface by lazy {
		RegistrationPresenter(view = this, repo = requireActivity().app.repo)
	}
	private var flag: Boolean = true
	private var accountLogin: String? = null

	companion object {
		fun newInstance() = RegistrationFragment()

		const val KEY_ACCOUNT_UPDATE = "KEY_ACCOUNT_UPDATE"
		fun updateInstance(login: String): RegistrationFragment {
			return RegistrationFragment().apply {
				arguments = Bundle().apply {
					putString(KEY_ACCOUNT_UPDATE, login)
				}
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding == null
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentRegistrationBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (arguments != null) {
			accountLogin = arguments?.getString(KEY_ACCOUNT_UPDATE)
			if (accountLogin != null) {
				presenter.getDataAccount(accountLogin!!)
			}
		}
		if (accountLogin != null) {
			binding.registrationButton.setText(R.string.save)
		} else {
			binding.registrationButton.setText(R.string.registration)
		}

		binding.registrationButton.setOnClickListener {
			val login: String = binding.loginEditText.text.toString()
			val password: String = binding.passwordEditText.text.toString()
			val email: String = binding.emailEditText.text.toString()
			if (binding.passwordEditText.text.toString() == binding.repeatPasswordEditText.text.toString()) {
				if (accountLogin != null) {
					presenter.onUpdateAccount(login, password, email)
				} else {
					presenter.onCheckedAccount(login, email)
					if (!flag) {
						if (binding.loginEditText.text.toString() != "" && binding.emailEditText.text.toString() != "") {
							presenter.onInsertAccount(login, password, email)
						} else {
							showError("Не заполнены все поля!")
						}
					} else {
						showError("Логин или email уже используются!")
					}
				}
			} else {
				showError("Пароли не совпадают")
			}
		}
	}

	override fun showProgress() {
		binding.fragmentRegistrationProgressBar.isVisible = true
	}

	override fun hideProgress() {
		binding.fragmentRegistrationProgressBar.isVisible = false
	}

	override fun showError(error: String) {
		binding.loginEditText.showSnackBarNoAction(error)
	}

	override fun showSaved() {
		binding.loginEditText.showSnackBarNoAction(requireActivity().getString(R.string.saved_registration))
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container_view, LoginFragment.newInstance())
			.commit()
	}

	override fun checkedAccount(result: Boolean?) {
		result?.let { flag = result }
	}

	override fun setView(account: LoginEntity) {
		binding.loginEditText.setText(account.login)
		binding.loginEditText.isClickable = false
		binding.passwordEditText.setText(account.password)
		binding.repeatPasswordEditText.setText(account.password)
		binding.emailEditText.setText(account.email)
	}
}
