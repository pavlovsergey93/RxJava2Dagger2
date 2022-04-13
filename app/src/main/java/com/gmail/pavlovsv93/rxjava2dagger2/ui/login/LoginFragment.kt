package com.gmail.pavlovsv93.rxjava2dagger2.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.app
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentLoginBinding
import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.ui.ViewModel
import com.gmail.pavlovsv93.rxjava2dagger2.ui.registration.RegistrationFragment
import com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password.ForgetPasswordFragment
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage
import com.gmail.pavlovsv93.rxjava2dagger2.utils.hideKeyboard
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class LoginFragment : Fragment() {

	private var _binding: FragmentLoginBinding? = null
	private val binding get() = _binding!!
	private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
	private val viewModel: LoginViewModelInterface by lazy {
		ViewModel(requireActivity().app.repo)
	}

	companion object {
		fun newInstance() = LoginFragment()

		const val KEY_ACCOUNT_SAVE = "KEY_ACCOUNT_SAVE"
		fun saveInstance(login: String): RegistrationFragment {
			return RegistrationFragment().apply {
				arguments = Bundle().apply {
					putString(KEY_ACCOUNT_SAVE, login)
				}
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.retainInstance = true
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding == null
		with(viewModel) {
			successState.unsubscribeAll()
			errorMessage.unsubscribeAll()
			showLayoutState.unsubscribeAll()
			progressState.unsubscribeAll()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentLoginBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//Обработка нажатия на "Войти"
		binding.singInButton.setOnClickListener {
			requireActivity().hideKeyboard(requireActivity())
			val login = binding.loginEditText.text.toString()
			val password = binding.passwordEditText.text.toString()
			viewModel.onAuthorization(login, password)
		}

		// Обработка нажатия на "Регистрация"
		binding.registrationButton.setOnClickListener {
			requireActivity().hideKeyboard(requireActivity())
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container_view, RegistrationFragment.newInstance())
				.addToBackStack(binding.registrationButton.text.toString())
				.commit()
		}

		//Обработка нажатия на "Удалить аккаунт"
		binding.deleteAccountButton.setOnClickListener {
			val login = binding.loginEditText.text.toString()
			viewModel.onDeleteAccount(login)
		}

		//Обработка нажатия на "Обновить аккаунт"
		binding.updateAccountButton.setOnClickListener {
			val login = binding.loginEditText.text.toString()
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container_view, RegistrationFragment.updateInstance(login))
				.addToBackStack(binding.loginEditText.text.toString())
				.commit()
		}

		//Обработка нажатия "Выход"
		binding.exitButton.setOnClickListener {
			viewModel.showLayoutState.post(false)
		}

		// Обработка нажатия "Забыл пароль"
		binding.forgotPasswordButton.setOnClickListener {
			requireActivity().hideKeyboard(requireActivity())
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container_view, ForgetPasswordFragment.newInstance())
				.addToBackStack("ForgetPasswordFragment")
				.commit()
		}
		with(viewModel) {
			progressState.subscribe(handler) { progressStateShow ->
				with(binding.fragmentLoginProgressBar) {
					visibility = if (progressStateShow) {
						View.VISIBLE
					} else {
						View.GONE
					}
				}
			}
			errorMessage.subscribe(handler) { errorMessageState ->
				if (successState.value == null && errorMessageState != null) {
					binding.linearLayoutSingIn.showSnackBarNoAction(errorMessageState)
				}
			}
			showLayoutState.subscribe(handler) { layoutState ->
				if (layoutState) {
					binding.linearLayoutSingIn.isVisible = false
					binding.linearLayoutSingOut.isVisible = true
				} else {
					binding.linearLayoutSingIn.isVisible = true
					binding.linearLayoutSingOut.isVisible = false
				}
			}
			successState.subscribe(handler) { successState ->
				if (showLayoutState.value == true && successState != null) {
					binding.infoTextView.text =
						("Логин: ${successState.login} \nE-mail: ${successState.email}")
				} else {
					binding.infoTextView.text = null
				}
			}
		}


	}


//	override fun setCheckedSing() {
//		binding.singInButton.setOnClickListener {
//			showProgress()
//			it.isClickable = false
//			binding.registrationButton.isVisible = false
//			binding.forgotPasswordButton.isVisible = false
//		}
//	}
//
//	override fun setRegistration() {
//		requireActivity().supportFragmentManager.beginTransaction()
//			.replace(R.id.fragment_container_view, RegistrationFragment.newInstance())
//			.addToBackStack(getString(R.string.registration))
//			.commit()
//	}
}