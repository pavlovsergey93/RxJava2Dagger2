package com.gmail.pavlovsv93.rxjava2dagger2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.LoginContract
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.RegistrationContract
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentRegistrationBinding
import com.gmail.pavlovsv93.rxjava2dagger2.model.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.presenter.LoginPresenter
import com.gmail.pavlovsv93.rxjava2dagger2.presenter.RegistrationPresenter
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class RegistrationFragment : Fragment(), RegistrationContract.RegistrationViewInterface {

	private var _binding: FragmentRegistrationBinding? = null
	private val binding get() = _binding!!
	private val presenter: RegistrationContract.RegistrationPresenterInterface =
		RegistrationPresenter()
	private var flag : Boolean = true

	private var accountUpdate: LoginEntity? = null

	companion object {
		fun newInstance() = RegistrationFragment()

		const val KEY_ACCOUNT_UPDATE = "KEY_ACCOUNT_UPDATE"

		fun updateInstance(account: LoginEntity): RegistrationFragment {
			return RegistrationFragment().apply {
				arguments = Bundle().apply {
					putParcelable(KEY_ACCOUNT_UPDATE, account)
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
		presenter.onAttachView(this)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (arguments != null) {
			accountUpdate = arguments?.getParcelable(KEY_ACCOUNT_UPDATE)
			if (accountUpdate != null) {
				binding.login.setText(accountUpdate!!.login)
				binding.login.isClickable = false
				binding.password.setText(accountUpdate!!.password)
				binding.password2.setText(accountUpdate!!.password)
				binding.email.setText(accountUpdate!!.email)
			}
		}
		if (accountUpdate != null) {
			binding.btnRegistration.setText(R.string.save)
		} else {
			binding.btnRegistration.setText(R.string.registration)
		}

		binding.btnRegistration.setOnClickListener {
			val login: String = binding.login.toString()
			val password: String = binding.password.toString()
			val email: String = binding.email.toString()
			if (binding.password.text.toString() == binding.password2.text.toString()) {
				presenter.onCheckedAccount(login, email)
				if (!flag) {
					if (binding.login.text != null && binding.email.text != null) {
						if (accountUpdate != null) {
							presenter.onUpdateAccount(accountUpdate!!, password, email)
						} else {
							presenter.onInsertAccount(login, password, email)
						}
					}
				}
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
		hideProgress()
		binding.login.showSnackBarNoAction(error)
	}

	override fun showEmpty() {
		hideProgress()
		binding.login.showSnackBarNoAction(requireActivity().getString(R.string.error_registration))
	}

	override fun showSaved() {
		hideProgress()
		binding.login.showSnackBarNoAction(requireActivity().getString(R.string.saved_registration))
	}

	override fun checkedAccount(result: Boolean?) {
		result?.let { flag = result }
	}
}
