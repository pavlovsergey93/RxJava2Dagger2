package com.gmail.pavlovsv93.rxjava2dagger2.ui.forget.password

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.rxjava2dagger2.app
import com.gmail.pavlovsv93.rxjava2dagger2.databinding.FragmentForgotPasswordBinding
import com.gmail.pavlovsv93.rxjava2dagger2.ui.ViewModel
import com.gmail.pavlovsv93.rxjava2dagger2.utils.hideKeyboard
import com.gmail.pavlovsv93.rxjava2dagger2.utils.showSnackBarNoAction

class ForgetPasswordFragment : Fragment() {

	private var _binding: FragmentForgotPasswordBinding? = null
	private val binding get() = _binding!!
	private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
	private val viewModel: ForgotPasswordViewModelInterface by lazy {
		ViewModel(requireActivity().app.repo)
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
		with(viewModel) {
			progressState.unsubscribeAll()
			successState.unsubscribeAll()
			errorMessage.unsubscribeAll()
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.forgotPasswordFindButton.setOnClickListener {
			requireActivity().hideKeyboard(requireActivity())
			var findAccount = binding.forgotPasswordEditText.text.toString()
			viewModel.findAccount(findAccount)
		}
		viewModel.progressState.subscribe(handler) { progressStateShow ->
			with(binding.fragmentForgotPasswordProgressBar) {
				visibility = if (progressStateShow) {
					View.VISIBLE
				} else {
					View.GONE
				}
			}
		}
		viewModel.successState.subscribe(handler) { successState ->
			with(binding.forgotPasswordResultFindTextView) {
				if (successState != null) {
					text = "Логин: ${successState.login}\n" +
							"Пароль: ${successState.password}" +
							"\nE-mail: ${successState.email}"
				}
			}
		}
		viewModel.errorMessage.subscribe(handler) { errorMessageState ->
			if (viewModel.successState.value == null && errorMessageState != null) {
				binding.forgotPasswordEditText.showSnackBarNoAction(errorMessageState)
				binding.forgotPasswordResultFindTextView.text = null
			}
		}
	}
}
