package com.food.schrood.ui.fragments


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import com.food.schrood.R
import com.food.schrood.databinding.FragmentChangePasswordBinding
import com.food.schrood.model.LoginResponse
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.isStrongPassword

import com.food.schrood.utility.StaticData.Companion.showToast
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.ChangePasswordViewModal


class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModal: ChangePasswordViewModal
    lateinit var preferenceManager: PreferenceManager
    private lateinit var loginResponse: LoginResponse
    lateinit var utilsManager: UtilsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(this).get(ChangePasswordViewModal::class.java)
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        // loginResponse= preferenceManager.getLoginData()!!
        setData()
        clickListener()
        return root
    }


    private fun clickListener() {
        binding.viewHeader.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.btnSubmit.setOnClickListener() {
            requireActivity().onBackPressed()
            //  checkValidation()
        }

    }

    private fun setData() {
        binding.viewHeader.txtTitle.text =
            requireActivity().resources.getString(R.string.change_password)
        StaticData.passWordEditText(requireActivity(), false, binding.edCurrentPassword)
        StaticData.passWordEditText(requireActivity(), false, binding.edNewPassword)
        StaticData.passWordEditText(requireActivity(), false, binding.edConfirmPassword)
    }

    private fun checkValidation() {

        val currentPassword = binding.edCurrentPassword.text.toString()
        val newPassword = binding.edNewPassword.text.toString()
        val confPassword = binding.edConfirmPassword.text.toString()
        if (TextUtils.isEmpty(currentPassword)) {
            showToast(
                requireActivity(),
                requireActivity().getString(R.string.enter_current_password)
            )
            binding.edCurrentPassword.requestFocus()
        } else if (TextUtils.isEmpty(newPassword)) {
            binding.edCurrentPassword.clearFocus()
            binding.edNewPassword.requestFocus()
            showToast(requireActivity(), requireActivity().getString(R.string.enter_new_password))
        } else if (!isStrongPassword(newPassword)) {
            binding.edCurrentPassword.clearFocus()
            binding.edNewPassword.requestFocus()
            showToast(
                requireActivity(),
                requireActivity().getString(R.string.strong_password_message)
            )
        } else if (newPassword.equals(currentPassword)) {
            binding.edCurrentPassword.clearFocus()
            binding.edNewPassword.requestFocus()
            showToast(
                requireActivity(),
                requireActivity().getString(R.string.current_password_and_new_password_different)
            )
        } else if (TextUtils.isEmpty(confPassword)) {
            showToast(
                requireActivity(),
                requireActivity().getString(R.string.enter_conform_password)
            )
            binding.edNewPassword.clearFocus()
            binding.edConfirmPassword.requestFocus()
        } else if (!newPassword.equals(confPassword)) {
            showToast(
                requireActivity(),
                requireActivity().getString(R.string.confirm_password_not_match)
            )
            binding.edNewPassword.clearFocus()
            binding.edConfirmPassword.requestFocus()
        } else {

            binding.edCurrentPassword.clearFocus()
            binding.edNewPassword.clearFocus()
            binding.edConfirmPassword.clearFocus()

            /* if (utilsManager.isNetworkConnected()){
                 viewModal.changePassword(
                     requireActivity(),
                     binding.edCurrentPassword.text.toString(),
                     binding.edNewPassword.text.toString(),
                     preferenceManager.getAuthToken()
                     ).observe(requireActivity(), Observer { res ->
                     printLog("change password", "--->" + res)
                     showToast(requireActivity(),res.message)
                     if (res.status) {
                         requireActivity().onBackPressed()
                     }


                 })
             }*/
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}