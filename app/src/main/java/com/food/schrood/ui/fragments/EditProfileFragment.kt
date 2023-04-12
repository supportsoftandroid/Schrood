package com.food.schrood.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import com.food.schrood.R
import com.food.schrood.databinding.FragmentEditProfileBinding
import com.food.schrood.model.LoginResponse
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.ChangePasswordViewModal


class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModal: ChangePasswordViewModal
    lateinit var preferenceManager: PreferenceManager
   // private lateinit var loginResponse: LoginResponse
    lateinit var utilsManager: UtilsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal =  ViewModelProvider(this).get(ChangePasswordViewModal::class.java)
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
     //   loginResponse= preferenceManager.getLoginData()!!
        setData()
        clickListener()
        return root
    }




    private fun clickListener() {
        binding.header.imgBack.setOnClickListener(){
            requireActivity().onBackPressed()
        }
        binding.btnUpdate.setOnClickListener(){
                requireActivity().onBackPressed()
          //  checkValidation()
        }

    }

    private fun setData() {
        binding.header.txtTitle.text=requireActivity().resources.getString(R.string.edit_profile)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}