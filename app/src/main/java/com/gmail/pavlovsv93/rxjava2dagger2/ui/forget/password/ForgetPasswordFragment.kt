package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.app
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentForgotPasswordBinding
import com.gmail.pavlovsv93.rxjava2dagger2.domain.ForgotPasswordViewModelInterface
import com.gmail.pavlovsv93.rxjava2dagger2.domain.room.LoginEntity
import com.gmail.pavlovsv93.rxjava2dagger2.utils.hideKeyboard
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class ForgetPasswordFragment : Fragment() {

	private var _binding: FragmentForgotPasswordBinding? = null
	private val binding get() = _binding!!

	private val viewModel: ForgotPasswordViewModelInterface by lazy {
		ForgetPasswordViewModel(requireActivity().app.repo)
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
			viewModel.findAccount(findAccount)
		}
		viewModel.progressState.subscribe { stateShow ->
			with(binding.fragmentForgotPasswordProgressBar) {
				visibility = if (stateShow == true) {
					View.VISIBLE
				} else {
					View.GONE
				}
			}
		}
		viewModel.successState.subscribe { successState ->
			with(binding.forgotPasswordResultFindTextView) {
				if (successState != null) {
					text = "Логин: ${successState.login}\n" +
							"Пароль: ${successState.password}" +
							"\nE-mail: ${successState.email}"
				}
			}
		}
		viewModel.errorMessage.subscribe { errorState ->
			if (viewModel.successState.value == null && errorState != null) {
				binding.forgotPasswordEditText.showSnackBarNoAction(errorState)
				binding.forgotPasswordResultFindTextView.text = null
			}
		}
	}
}
