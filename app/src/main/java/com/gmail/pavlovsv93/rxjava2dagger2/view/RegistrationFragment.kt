package com.gmail.pavlovsv93.rxjava2dagger2.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.RegistrationContract
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentRegistrationBinding
import com.gmail.pavlovsv93.rxjava2dagger2.model.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.presenter.RegistrationPresenter
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction
import org.jetbrains.annotations.NotNull

class RegistrationFragment : Fragment(), RegistrationContract.RegistrationViewInterface {

	private var _binding: FragmentRegistrationBinding? = null
	private val binding get() = _binding!!
	private val presenter: RegistrationContract.RegistrationPresenterInterface =
		RegistrationPresenter(this)
	private var flag: Boolean = true

	private var accountLogin: String? = null
	private var accountUpdate: LoginEntity? = null

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
			binding.btnRegistration.setText(R.string.save)
		} else {
			binding.btnRegistration.setText(R.string.registration)
		}

		binding.btnRegistration.setOnClickListener {
			val login: String = binding.login.text.toString()
			val password: String = binding.password.text.toString()
			val email: String = binding.email.text.toString()
			if (binding.password.text.toString() == binding.password2.text.toString()) {
				if (accountLogin != null) {
					presenter.onUpdateAccount(login, password, email)
				} else {
					presenter.onCheckedAccount(login, email)
					if (!flag) {
						if (binding.login.text.toString() != "" && binding.email.text.toString() != "") {
							presenter.onInsertAccount(login, password, email)
						} else {
							showError("Не заполнены все поля!")
						}
					}else{
						showError("Логин или email уже используются!")
					}
				}
			} else {
				showError("Пароли не совпадают")
			}
		}
	}

	override fun showProgress() {
		binding.registrationProgressBar.isVisible = true
	}

	override fun hideProgress() {
		binding.registrationProgressBar.isVisible = false
	}

	override fun showError(error: String) {
		binding.login.showSnackBarNoAction(error)
	}

	override fun showSaved() {
		binding.login.showSnackBarNoAction(requireActivity().getString(R.string.saved_registration))
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container, LoginFragment.newInstance())
			.commit()
	}

	override fun checkedAccount(result: Boolean?) {
		result?.let { flag = result }
	}

	override fun setView(account: LoginEntity) {
		binding.login.setText(account.login)
		binding.login.isClickable = false
		binding.password.setText(account.password)
		binding.password2.setText(account.password)
		binding.email.setText(account.email)
	}
}
