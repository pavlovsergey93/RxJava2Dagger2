package com.gmail.pavlovsv93.rxjava2dagger2.ui.registration

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.app
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentRegistrationBinding
import com.gmail.pavlovsv93.rxjava2dagger2.ui.ViewModel
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage
import com.gmail.pavlovsv93.rxjava2dagger2.utils.hideKeyboard
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class RegistrationFragment : Fragment() {

	private var _binding: FragmentRegistrationBinding? = null
	private val binding get() = _binding!!
	private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
	private val viewModel: RegistrationViewModelInterface by lazy {
		ViewModel(requireActivity().app.repo)
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
		with(viewModel){
			successState.unsubscribeAll()
			errorMessage.unsubscribeAll()
			accountCheckState.unsubscribeAll()
			progressState.unsubscribeAll()
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
				viewModel.getDataAccount(accountLogin!!)
			}
		}
		if (accountLogin != null) {
			binding.registrationButton.setText(R.string.save)
		} else {
			binding.registrationButton.setText(R.string.registration)
		}

		binding.registrationButton.setOnClickListener {
			requireActivity().hideKeyboard(requireActivity())
			val login: String = binding.loginEditText.text.toString()
			val password: String = binding.passwordEditText.text.toString()
			val email: String = binding.emailEditText.text.toString()
			if (binding.passwordEditText.text.toString() == binding.repeatPasswordEditText.text.toString()) {
				if (accountLogin != null) {
					viewModel.onUpdateAccount(login, password, email)
				} else {
					viewModel.onCheckedAccount(login, email)
					if (viewModel.accountCheckState.value == false) {
						if (binding.loginEditText.text.toString() != "" && binding.emailEditText.text.toString() != "") {
							viewModel.onInsertAccount(login, password, email)
						} else {
							viewModel.errorMessage.post(ExceptionMessage.E410.message)
						}
					} else {
						viewModel.errorMessage.post(ExceptionMessage.E400.message)
					}
				}
			} else {
				viewModel.errorMessage.post(ExceptionMessage.E409.message)
			}
		}
		with(viewModel) {
			progressState.subscribe(handler) { progressStateShow ->
				with(binding.fragmentRegistrationProgressBar) {
					visibility = if (progressStateShow) {
						View.VISIBLE
					} else {
						View.GONE
					}
				}
			}
			errorMessage.subscribe(handler) { errorMessageState ->
				if (successState.value == null && errorMessageState != null) {
					binding.loginEditText.showSnackBarNoAction(errorMessageState)
				}
			}
			successState.subscribe(handler) { successState ->
				binding.loginEditText.setText(successState.login)
				binding.loginEditText.isClickable = false
				binding.passwordEditText.setText(successState.password)
				binding.repeatPasswordEditText.setText(successState.password)
				binding.emailEditText.setText(successState.email)
			}
		}
	}
}