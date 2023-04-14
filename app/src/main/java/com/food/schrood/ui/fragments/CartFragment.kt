package com.food.schrood.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogBottomAddCardBinding
import com.food.schrood.databinding.FragmentCartBinding
import com.food.schrood.model.CardData
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.CartItemAdapter
import com.food.schrood.ui.adapter.SavedCardAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.NotificationsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartFragment : Fragment() {
    companion object {
        const val Cart_LIST_REQUEST_KEY = "cart_list"
        fun newInstance(title: String): CartFragment {
            val args = Bundle()
            args.putString("title", title)
            val fragment = CartFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModal: NotificationsViewModel
    lateinit var adaper: CartItemAdapter
    lateinit var savedCardAdapter: SavedCardAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var cardList = mutableListOf<CardData>()
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NotificationsViewModel::class.java)

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        initView()
        clickListener()

        return root
    }

    private fun clickListener() {
        binding.viewHeader.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }

        binding.viewAddress.imgEdit.setOnClickListener() {
            StaticData.backStackAddFragment(
                requireActivity(),
                AddNewAddressFragment.newInstance("")
            )

        }
        binding.tvAddMoreItem.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.tvAddNewCard.setOnClickListener() {
            dialogAddCard()
        }

    }

    fun initView() {
        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.saved_address)
        dataList.clear()

        dataList.add(CommonDataItem("Creamy Burger", "", false))
        dataList.add(CommonDataItem("Mountain Dew", "", false))
        adaper =
            CartItemAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvCartList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCartList.adapter = adaper
        initPaymentCard()


    }

    private fun initPaymentCard() {
        cardList.add(CardData("visa", "Allison Smith", 0, 0, 0, "", "", 6864, false))
        cardList.add(CardData("visa", "Johan Rock", 0, 0, 0, "", "", 4464, false))
        cardList.add(CardData("visa", "Mical Joy", 0, 0, 0, "", "", 6864, false))
        cardList.add(CardData("visa", "Json Roy", 0, 0, 0, "", "", 4464, false))
        savedCardAdapter = SavedCardAdapter(
            requireActivity(),
            cardList,
            "Schrood",
            true,
            { pos, type -> onCardItemClick(pos, type) })
        binding.rvCartList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCartList.adapter = savedCardAdapter


    }

    private fun onCardItemClick(pos: Int, type: String) {

    }

    private fun onAdapterClick(pos: Int, type: String) {
        requireActivity().onBackPressed()

    }

    fun dialogAddCard() {
        val dialogBinding =
            DialogBottomAddCardBinding.inflate(LayoutInflater.from(requireActivity()), null, false)
        val dialogAddcard = BottomSheetDialog(requireActivity(), R.style.GalleryDialog)
        dialogAddcard.setContentView(dialogBinding.root)
        dialogAddcard.setCancelable(false)
        dialogBinding.cardInputWidget.setShouldShowPostalCode(false)


        dialogBinding.imgClose.setOnClickListener {
            dialogAddcard.dismiss()
        }
        dialogBinding.btnAdd.setOnClickListener {
            //  btnAdd = dialogBinding.btnAdd
            // progressBar = dialogBinding.progressBar
            if (TextUtils.isEmpty(dialogBinding.ediName.text.toString().trim())) {
                dialogBinding.ediName.requestFocus()
                utilsManager.showAlertMessageError(
                    requireActivity(),
                    requireActivity().getString(R.string.please_enter_name)
                )

            } else {
                dialogBinding.ediName.clearFocus()
                dialogAddcard.dismiss()
                cardList.add(CardData("visa", "Allison Smith", 0, 0, 0, "", "", 4464, false))

                savedCardAdapter.notifyDataSetChanged()
                // addCard(dialogBinding.ediName.text.toString(),dialogBinding.cardInputWidget)
            }


        }
        dialogAddcard.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}