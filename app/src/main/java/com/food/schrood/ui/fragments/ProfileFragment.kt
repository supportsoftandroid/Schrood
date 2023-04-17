package com.food.schrood.ui.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.food.schrood.R
import com.food.schrood.databinding.FragmentProfileBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.model.UserDetails
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.activities.StaticPagesActivity
import com.food.schrood.ui.adapter.ProfileAdapter
import com.food.schrood.utility.Constants
import com.food.schrood.utility.Constants.PROFILE_EDIT_REQUEST_KEY
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.showToast
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.ProfileViewModal
import com.github.dhaval2404.imagepicker.ImagePicker


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private lateinit var viewModal: ProfileViewModal
    private val binding get() = _binding!!
    lateinit var adaper: ProfileAdapter
    var dataList = mutableListOf<CommonDataItem>()
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    //  private lateinit var loginResponse: LoginResponse

    lateinit var userType: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        viewModal = ViewModelProvider(this).get(ProfileViewModal::class.java)
        /*  userType=preferenceManager.getString(USER_ROLE_KEY).toString()
          loginResponse= preferenceManager.getLoginData()!!*/
        setData()
        clickListener()
        // getProfileData()

        return root
    }

    private fun clickListener() {
        binding.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()

        }




        binding.imgCamera.setOnClickListener {
            //  utilsManager.showGallaryBottomModelSheet(requireActivity())
        }
        binding.tvLogout.setOnClickListener() {
            logOutFromApp()

        }
        binding.tvDelete.setOnClickListener() {
           deleteAccount()

        }
    }

    /*  private fun getProfileData() {
          if (_binding != null) {
              if (utilsManager.isNetworkConnected()) {
                  viewModal.getProfile(
                      requireActivity(),
                      preferenceManager.getString(Constants.KEY_ACCESS_TOKEN).toString()
                  ).observe(requireActivity(), Observer { res ->
                      StaticData.printLog("getprofile", "--->" + res)

                      if (res.status) {
                          loginResponse = res
                          preferenceManager.setLoginData(res)

                          setProfileData(res.data)
                      }


                  })
              }
          }
      }*/

    /*  private fun updateProfile(imagePath: String) {

          if (utilsManager.isNetworkConnected()) {
              viewModal.updateProfileImage(
                  requireActivity(),
                  preferenceManager.getString(Constants.KEY_ACCESS_TOKEN).toString(), imagePath
              ).observe(requireActivity(), Observer { res ->
                  StaticData.printLog("updare profile", "--->" + res)
                  showToast(requireActivity(), res.message)
                  if (res.status) {
                      loadImage(imagePath)
                  }


              })
          }
      }*/

    private fun setProfileData(userData: UserDetails) {

        binding.tvName.text = userData.name + " " + userData.l_name.toString()

        userData?.image?.let { loadImage(it) }
    }

    fun loadImage(pathUrl: String) {
        if (pathUrl != null) {
            Glide.with(requireActivity())
                .load(pathUrl)
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_profile_placeholder)
                        .centerCrop()
                ).into(binding.imgProfile)
        }
    }


    fun setData() {
        //   setProfileData(loginResponse.data)
        dataList.clear()

        dataList.add(CommonDataItem(getString(R.string.edit_profile), "EditProfile", false))
        dataList.add(CommonDataItem(getString(R.string.change_password), "ChangePassword", false))
        dataList.add(CommonDataItem(getString(R.string.saved_cards), "SavedCards", false))
        dataList.add(CommonDataItem(getString(R.string.saved_address), "SavedAddress", false))
        dataList.add(CommonDataItem(getString(R.string.notifications), "Notifications", false))


        dataList.add(CommonDataItem(getString(R.string.terms_and_conditions), "Terms", false))
        dataList.add(CommonDataItem(getString(R.string.user_policy), "UserPolicy", false))
        dataList.add(CommonDataItem(getString(R.string.faqs), "FAQs", false))
        dataList.add(CommonDataItem(getString(R.string.become_vendor), "BecomeVendor", false))
        dataList.add(CommonDataItem(getString(R.string.send_feedback), "SendFeedback", false))
        dataList.add(CommonDataItem(getString(R.string.about), "About", false))


        adaper = ProfileAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adaper
        setFragmentResultListener(PROFILE_EDIT_REQUEST_KEY) { key, bundle ->
            // Any type can be passed via to the bundle
            val from = bundle.getString("from").toString()
            if (from.equals("edit_profile")) {
                // getProfileData()
            }
            // Do something with the result...
        }
    }

    private fun onAdapterClick(pos: Int, type: String) {
        when (type) {
            "EditProfile" -> {
                moveToNextFragment(EditProfileFragment())
            }

            "ChangePassword" -> {
                moveToNextFragment(ChangePasswordFragment())
            }
            "SavedCards" -> {
                moveToNextFragment(
                    SavesCardListFragment.newInstance(
                        "profile",
                        dataList[pos].title
                    )
                )
            }
            "SavedAddress" -> {
                moveToNextFragment(AddressListFragment.newInstance(dataList[pos].title))
            }
            "Notifications" -> {
                moveToNextFragment(
                    NotificationsFragment()
                )
            }
            "SendFeedback" -> {
                moveToNextFragment(
                    FeedBackFragment()
                )
            }

            else -> {
                val intent = Intent(requireActivity(), StaticPagesActivity::class.java)
                intent.putExtra(StaticPagesActivity.PAGE_TYPE, type)
                intent.putExtra(StaticPagesActivity.PAGE_Title, dataList[pos].title)
                startActivity(intent)

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun moveToNextFragment(fragment: Fragment) {
        MainActivity.hideNavigationTab()
        StaticData.backStackAddFragment(requireActivity(), fragment)
    }

    private fun logOutFromApp() {
        val builder = AlertDialog.Builder(requireActivity())
        // builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.logout_message)

        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            preferenceManager.saveBoolean(Constants.KEY_CHECK_LOGIN, false)
            StaticData.logoutFromApp(requireActivity())

        }
        //performing negative action
        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun deleteAccount() {
        val builder = AlertDialog.Builder(requireActivity())
        // builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.delete_account_message)

        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            preferenceManager.saveBoolean(Constants.KEY_CHECK_LOGIN, false)
            StaticData.logoutFromApp(requireActivity())

        }
        //performing negative action
        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StaticData.IMAGE_CROP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val imagefileUri = data?.data!!
                if (imagefileUri != null) {
                    val imagefilePath = imagefileUri?.path.toString()
                    //updateProfile(imagefilePath)


                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            } else {
                showToast(requireActivity(), "Task Cancelled")
            }
        }


    }
}