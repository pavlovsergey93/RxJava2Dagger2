package com.gmail.pavlovsv93.rxjava2dagger2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.R
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentLoginBinding
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.ui.registration.RegistrationFragment
import com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password.ForgetPasswordFragment
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class LoginFragment : Fragment(), LoginContract.LoginViewInterface {

	private var _binding: FragmentLoginBinding? = null
	private val binding get() = _binding!!
	private val presenter: LoginContract.LoginPresenterInterface = LoginPresenter()

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
		presenter.onAttachView(this)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//Обработка нажатия на "Войти"
		binding.singIn.setOnClickListener {
			val login = binding.login.text.toString()
			val password = binding.password.text.toString()
			presenter.onAuthorization(login, password)
		}

		// Обработка нажатия на "Регистрация"
		binding.registration.setOnClickListener {
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container, RegistrationFragment.newInstance())
				.addToBackStack(binding.registration.text.toString())
				.commit()
		}

		//Обработка нажатия на "Удалить аккаунт"
		binding.deleteAccount.setOnClickListener {
			val login = binding.login.text.toString()
			presenter.onDeleteAccount(login)
			showLayoutSing()
		}

		//Обработка нажатия на "Обновить аккаунт"
		binding.updateAccount.setOnClickListener {
			val login = binding.login.text.toString()
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container, RegistrationFragment.updateInstance(login))
				.addToBackStack("RegistrationFragment")
				.commit()
		}
		//Обработка нажатия "Выход"
		binding.exit.setOnClickListener {
			showLayoutSing()
		}

		// Обработка нажатия "Забыл пароль"
		binding.forgotPassword.setOnClickListener {
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container, ForgetPasswordFragment.newInstance())
				.addToBackStack("ForgetPasswordFragment")
				.commit()
		}

	}

	override fun showProgress() {
		binding.progressBar.isVisible = true
	}

	override fun hideProgress() {
		binding.progressBar.visibility = View.GONE
	}

	override fun setCheckedSing() {
		binding.singIn.setOnClickListener {
			showProgress()
			it.isClickable = false
			binding.registration.isVisible = false
			binding.forgotPassword.isVisible = false
		}
	}

	override fun setError(error: String) {
		hideProgress()
		binding.forgotPassword.isVisible = true
		binding.registration.isVisible = true
		binding.singIn.isVisible = true
		binding.login.showSnackBarNoAction(error)
	}

	override fun setRegistration() {
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container, RegistrationFragment.newInstance())
			.addToBackStack(getString(R.string.registration))
			.commit()
	}

	override fun showLayoutSing() {
		binding.llSingIn.visibility = View.VISIBLE
		binding.llSingOut.visibility = View.GONE
		binding.textView.text = ""
	}

	override fun showLayoutAccount(account: LoginEntity) {
		binding.textView.text = ("Логин: ${account.login} \nE-mail: ${account.email}")
		binding.llSingOut.visibility = View.VISIBLE
		binding.llSingIn.visibility = View.GONE
	}

	override fun setMessageState(massage: String) {
		hideProgress()
		binding.llSingIn.showSnackBarNoAction(massage)
	}

}