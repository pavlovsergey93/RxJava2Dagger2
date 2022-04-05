package com.gmail.pavlovsv93.rxjava2dagger2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment(), Contract.LoginViewInterface{

	private var _binding : FragmentLoginBinding? = null
	private val binding get() = _binding!!

	companion object {
		fun newInstance() = LoginFragment()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentLoginBinding.inflate(inflater, container, false)
		return binding.root
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
		Snackbar.make(binding.login, error, Snackbar.LENGTH_INDEFINITE).show()
	}

	override fun showRegistration(message: String) {
		Snackbar.make(binding.login, message, Snackbar.LENGTH_SHORT).show()
	}

	override fun setRegistration() {
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container, RegistrationFragment.newInstance())
			.addToBackStack(getString(R.string.registration))
			.commit()
	}

}