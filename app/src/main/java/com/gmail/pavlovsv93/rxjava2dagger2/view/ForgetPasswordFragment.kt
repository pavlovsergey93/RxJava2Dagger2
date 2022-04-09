package com.gmail.pavlovsv93.rxjava2dagger2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.ForgetPasswordContract
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentForgotPasswordBinding
import com.gmail.pavlovsv93.rxjava2dagger2.model.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.presenter.ForgetPasswordPresenter
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class ForgetPasswordFragment : Fragment(), ForgetPasswordContract.ForgetPasswordViewInterface {

	private var _binding: FragmentForgotPasswordBinding? = null
	private val binding get() = _binding!!
	val presenter: ForgetPasswordContract.ForgetPasswordPresenterInterface = ForgetPasswordPresenter(this)

	companion object{
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
		binding.ffpBtnFind.setOnClickListener {
			var findAccount = binding.ffpEditText.text.toString()
			presenter.findAccount(findAccount)
		}
	}

	override fun showProgress() {
		binding.ffpProgressBar.isVisible = true
	}

	override fun hideProgress() {
		binding.ffpProgressBar.isVisible = false
	}

	override fun setDataAccount(account: LoginEntity) {
		binding.ffpTextView.text = "Логин: ${account.login}\nПароль: ${account.password}\nE-mail: ${account.email}"
	}

	override fun setError(error: String) {
		binding.ffpBtnFind.showSnackBarNoAction(error)
	}
}