package com.food.schrood.ui.fragments


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.R
import com.food.schrood.databinding.FragmentEditProfileBinding
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.ChangePasswordViewModal
import com.food.schrood.viewmodel.EditProfileViewModal
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var mContext:Context
    lateinit var viewModal: EditProfileViewModal
    lateinit var preferenceManager: PreferenceManager

    // private lateinit var loginResponse: LoginResponse
    lateinit var utilsManager: UtilsManager

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModal = ViewModelProvider(this).get(EditProfileViewModal::class.java)
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mContext=requireActivity()
        preferenceManager = PreferenceManager(mContext)
        utilsManager = UtilsManager(requireActivity())
        //   loginResponse= preferenceManager.getLoginData()!!
        setData()
        clickListener()
        return root
    }


    private fun clickListener() {
        binding.header.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.btnUpdate.setOnClickListener() {

            //  checkValidation()
        }

    }

    private fun setData() {
        binding.header.txtTitle.text = requireActivity().resources.getString(R.string.edit_profile)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}