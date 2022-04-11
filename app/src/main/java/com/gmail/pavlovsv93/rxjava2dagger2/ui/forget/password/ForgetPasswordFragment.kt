package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.App
import com.gmail.pavlovsv93.rxjava2dagger2.app
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentForgotPasswordBinding
import com.gmail.pavlovsv93.rxjava2dagger2.data.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.repository.AccountRepositoryInterface
import com.gmail.pavlovsv93.rxjava2dagger2.utils.hideKeyboard
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class ForgetPasswordFragment : Fragment(), ForgetPasswordContract.ForgetPasswordViewInterface {

	private var _binding: FragmentForgotPasswordBinding? = null
	private val binding get() = _binding!!

	private val presenter: ForgetPasswordContract.ForgetPasswordPresenterInterface by lazy {
		ForgetPasswordPresenter(view = this,repo = requireActivity().app.repo)
	}

	companion object {
		fun newInstance() = ForgetPasswordFragment()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding == null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.forgotPasswordFindButton.setOnClickListener {
			requireActivity().hideKeyboard(requireActivity())
			var findAccount = binding.forgotPasswordEditText.text.toString()
			presenter.findAccount(findAccount)
		}
	}

	override fun showProgress() {
		binding.fragmentForgotPasswordProgressBar.isVisible = true
	}

	override fun hideProgress() {
		binding.fragmentForgotPasswordProgressBar.isVisible = false
	}

	override fun setDataAccount(account: LoginEntity) {
		binding.forgotPasswordResultFindTextView.text =
			"Логин: ${account.login}\nПароль: ${account.password}\nE-mail: ${account.email}"
	}

	override fun setError(error: String) {
		binding.forgotPasswordFindButton.showSnackBarNoAction(error)
	}
}