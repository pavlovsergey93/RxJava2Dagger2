package com.gmail.pavlovsv93.rxjava2dagger2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.app
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentLoginBinding
import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.ui.registration.RegistrationFragment
import com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password.ForgetPasswordFragment
import com.gmail.pavlovsv93.rxjava2dagger2.utils.ExceptionMessage
import com.gmail.pavlovsv93.rxjava2dagger2.utils.hideKeyboard
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class LoginFragment : Fragment(), LoginContract.LoginViewInterface {

	private var _binding: FragmentLoginBinding? = null
	private val binding get() = _binding!!
	private val presenter: LoginContract.LoginPresenterInterface by lazy {
		LoginPresenter(view = this, repo = requireActivity().app.repo)
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
			presenter.onAuthorization(login, password)
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
			presenter.onDeleteAccount(login)
			showLayoutSing()
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
			showLayoutSing()
		}

		// Обработка нажатия "Забыл пароль"
		binding.forgotPasswordButton.setOnClickListener {
			requireActivity().hideKeyboard(requireActivity())
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container_view, ForgetPasswordFragment.newInstance())
				.addToBackStack("ForgetPasswordFragment")
				.commit()
		}

	}

	override fun showProgress() {
		binding.fragmentLoginProgressBar.isVisible = true
	}

	override fun hideProgress() {
		binding.fragmentLoginProgressBar.visibility = View.GONE
	}

	override fun setCheckedSing() {
		binding.singInButton.setOnClickListener {
			showProgress()
			it.isClickable = false
			binding.registrationButton.isVisible = false
			binding.forgotPasswordButton.isVisible = false
		}
	}

	override fun setError(error: String) {
		hideProgress()
		binding.forgotPasswordButton.isVisible = true
		binding.registrationButton.isVisible = true
		binding.singInButton.isVisible = true
		binding.loginEditText.showSnackBarNoAction(error)
	}

	override fun setRegistration() {
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container_view, RegistrationFragment.newInstance())
			.addToBackStack(getString(R.string.registration))
			.commit()
	}

	override fun showLayoutSing() {
		binding.linearLayoutSingIn.visibility = View.VISIBLE
		binding.linearLayoutSingOut.visibility = View.GONE
		binding.infoTextView.text = ""
	}

	override fun showLayoutAccount(account: LoginEntity) {
		binding.infoTextView.text = ("Логин: ${account.login} \nE-mail: ${account.email}")
		binding.linearLayoutSingOut.visibility = View.VISIBLE
		binding.linearLayoutSingIn.visibility = View.GONE
	}

	override fun setMessageState(massage: String) {
		hideProgress()
		binding.linearLayoutSingIn.showSnackBarNoAction(massage)
	}

	override fun showMessage(code: ExceptionMessage) {
		hideProgress()
		binding.linearLayoutSingIn.showSnackBarNoAction(code.message)
	}

}