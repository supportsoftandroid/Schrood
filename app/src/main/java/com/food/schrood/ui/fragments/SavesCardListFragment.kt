package com.food.schrood.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogBottomAddCardBinding
import com.food.schrood.databinding.FragmentSavecardListBinding
import com.food.schrood.model.CardData
import com.food.schrood.ui.adapter.SavedCardAdapter
import com.food.schrood.utility.Constants
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.SaveCardViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.CardParams
import com.stripe.android.model.Token
import com.stripe.android.view.CardMultilineWidget

class SavesCardListFragment : Fragment() {
    companion object {
        const val SAVED_CARD_REQUEST_KEY = "saved_card_list"
        fun newInstance(from: String, title: String): SavesCardListFragment {
            val args = Bundle()
            args.putString("from", from)
            args.putString("title", title)
            val fragment = SavesCardListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentSavecardListBinding? = null
    private val binding get() = _binding!!
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager

    // lateinit var loginResponse: LoginResponse
    lateinit var viewModel: SaveCardViewModel
    lateinit var adaper: SavedCardAdapter
    var dataList = mutableListOf<CardData>()
    private lateinit var dialogAddcard: Dialog
    lateinit var from: String
    lateinit var title: String
    lateinit var stripeKey: String
    lateinit var stripe: Stripe
    var customerStripId = ""
    var isDelete = false
    var selectedPos: Int = 0
    lateinit var btnAdd: Button
    lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SaveCardViewModel::class.java)
        _binding = FragmentSavecardListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        // loginResponse = preferenceManager.getLoginData()!!
        from = requireArguments().getString("from").toString()
        title = requireArguments().getString("title").toString()
        /*if (loginResponse.data.custom_account_id!=null){
            customerStripId=loginResponse.data.custom_account_id

        }else{
            showToast(requireActivity(),requireActivity().getString(R.string.please_update_profile))

        }*/

        if (from.equals("payment")) {
            isDelete = false
        } else {
            isDelete = false
        }
        _binding.let {
            initRecycleView()
            clickListener()
            stripeKey = Constants.STRIPE_PUBLISH_KEY
            stripe = Stripe(requireActivity(), stripeKey)

            //callCardListAPI()
        }
        return root
    }

    fun initRecycleView() {
        dataList.clear()
        dataList.add(CardData("visa", "Allison Smith", 0, 0, 0, "", "", 6864, false))
        dataList.add(CardData("visa", "Johan Rock", 0, 0, 0, "", "", 4464, false))
        dataList.add(CardData("visa", "Mical Joy", 0, 0, 0, "", "", 6864, false))
        dataList.add(CardData("visa", "Json Roy", 0, 0, 0, "", "", 4464, false))
        dataList.add(CardData("visa", "Allison Smith", 0, 0, 0, "", "", 6864, false))
        dataList.add(CardData("visa", "Johan Rock", 0, 0, 0, "", "", 4464, false))
        dataList.add(CardData("visa", "Mical Joy", 0, 0, 0, "", "", 6864, false))
        dataList.add(CardData("visa", "Json Roy", 0, 0, 0, "", "", 4464, false))
        dataList.add(CardData("visa", "Allison Smith", 0, 0, 0, "", "", 6864, false))
        dataList.add(CardData("visa", "Johan Rock", 0, 0, 0, "", "", 4464, false))
        dataList.add(CardData("visa", "Mical Joy", 0, 0, 0, "", "", 6864, false))
        dataList.add(CardData("visa", "Json Roy", 0, 0, 0, "", "", 4464, false))
        binding.viewHeader.txtTitle.text =
            if (title.length == 0) requireActivity().getString(R.string.saved_cards) else title
        //  val name=if (loginResponse.data.l_name.isEmpty())loginResponse.data.name else loginResponse.data.name +" "+loginResponse.data.l_name
        adaper = SavedCardAdapter(
            requireActivity(),
            dataList,
            "Schrood",
            isDelete,
            { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adaper
        binding.viewBody.tvMessage.visibility = View.GONE

        binding.viewBody.swipeRefreshLayout.setOnRefreshListener {
            //   callCardListAPI()
            binding.viewBody.swipeRefreshLayout.isRefreshing = false
        }

    }
/*    private fun callCardListAPI( ) {
        if (_binding!=null) {
            if (utilsManager.isNetworkConnected()) {
                binding.viewBody.tvMessage.visibility = View.VISIBLE
                binding.viewBody.tvMessage.text = requireActivity().getString(R.string.loading)
                viewModel.getCardList(
                    requireActivity(),
                    preferenceManager.getString(Constants.KEY_ACCESS_TOKEN).toString(),
                    customerStripId
                ).observe(requireActivity(), Observer { res ->
                    StaticData.printLog("saved card list res", "--->" + res)
                    dataList.clear()
                    if (res.status) {
                        dataList.addAll(res.data)
                    }
                    updateUIData()
                    stripeKey=res.STRIPE_KEY
                    if (stripeKey!=null) {
                        stripe = Stripe(requireActivity(), stripeKey)
                    }
                })
            }
        }

    }*/


    /* private fun updateUIData() {
         _binding.let {
             adaper.notifyDataSetChanged()
             if (dataList.isEmpty()){
                 binding.viewBody.tvMessage.text=requireActivity().getString(R.string.no_card_found)
             }else{
                 binding.viewBody.tvMessage.visibility=View.GONE
             }
             StaticData.printLog("card  size", "--->" + dataList.size)
         }

     }*/
    private fun onAdapterClick(pos: Int, type: String) {
        selectedPos = pos
        if (from.equals("payment")) {
            requireActivity().onBackPressed()

        } else {
            deleteAlert()
        }

    }

    private fun clickListener() {
        binding.viewHeader.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.clvAddCard.setOnClickListener() {
            dialogAddCard()
        }
    }


    private fun makePayment() {
        val bundle = Bundle()
        bundle.putString("from", "payment_cards")
        bundle.putString("card_token", dataList[selectedPos].card_token)
        bundle.putString("customer_token", customerStripId)
        parentFragmentManager.setFragmentResult(SAVED_CARD_REQUEST_KEY, bundle)
        requireActivity().onBackPressed()
    }

    fun callDeleteCardAPI() {

        /* if (utilsManager.isNetworkConnected()){
             viewModel.deleteCard(requireActivity(),
                 preferenceManager.getString(Constants.KEY_ACCESS_TOKEN), customerStripId,dataList[selectedPos].card_token
             ).observe(requireActivity(), Observer { res ->
                 StaticData.printLog("delete  card  res", "--->" + res)
                 showToast(requireActivity(),res.message)

                 if (res.status) {
                     dataList.removeAt(selectedPos)
                 }
               //   updateUIData()

             })
         }*/

    }

    private fun makePaymentAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        // builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.makePaymentMessage)

        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->

            makePayment()
        }
        //performing negative action
        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun deleteAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        // builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.deleteMessage)

        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            dataList.removeAt(selectedPos)
            adaper.notifyDataSetChanged()
            // callDeleteCardAPI()

        }
        //performing negative action
        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun dialogAddCard() {
        val dialogBinding =
            DialogBottomAddCardBinding.inflate(LayoutInflater.from(requireActivity()), null, false)
        dialogAddcard = BottomSheetDialog(requireActivity(), R.style.GalleryDialog)
        dialogAddcard.setContentView(dialogBinding.root)
        dialogAddcard.setCancelable(false)
        dialogBinding.cardInputWidget.setShouldShowPostalCode(false)


        dialogBinding.imgClose.setOnClickListener {
            dialogAddcard.dismiss()
        }
        dialogBinding.btnAdd.setOnClickListener {
            btnAdd = dialogBinding.btnAdd
            progressBar = dialogBinding.progressBar
            if (TextUtils.isEmpty(dialogBinding.ediName.text.toString().trim())) {
                dialogBinding.ediName.requestFocus()
                utilsManager.showAlertMessageError(
                    requireActivity(),
                    requireActivity().getString(R.string.please_enter_name)
                )

            } else {
                dialogBinding.ediName.clearFocus()
                dialogAddcard.dismiss()
                dataList.add(CardData("visa", "Allison Smith", 0, 0, 0, "", "", 4464, false))

                adaper.notifyDataSetChanged()
                // addCard(dialogBinding.ediName.text.toString(),dialogBinding.cardInputWidget)
            }


        }
        dialogAddcard.show()
    }

    private fun addCard(strName: String, card: CardMultilineWidget) {

        if (!card.validateAllFields()) {

            utilsManager.showAlertMessageError(
                requireActivity(),
                requireActivity().getString(R.string.invalid_card)
            )
        } else {
            if (!TextUtils.isEmpty(Constants.STRIPE_PUBLISH_KEY)) {
                showProgress()
                createToken(strName, card, card.cardParams!!)
            } else {
                utilsManager.showAlertMessageError(
                    requireActivity(), requireActivity().getString(R.string.invalid_card)
                )
            }
        }
    }

    private fun showProgress() {
        btnAdd.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

    }

    private fun hideProgress() {
        btnAdd.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

    }

    fun createToken(strName: String, card: CardMultilineWidget, cardParams: CardParams) {

        if (card != null) {

            stripe.createCardToken(card.cardParams!!, callback = object : ApiResultCallback<Token> {
                override fun onSuccess(token: Token) {
                    StaticData.printLog("Stripe Token==", token.id)
                    StaticData.printLog("Save card  card!!.id==", token.card!!.id.toString())
                    callAddCardAPI(strName, token.id)

                }

                override fun onError(e: Exception) {
                    hideProgress()
                    utilsManager.showAlertMessageError(requireActivity(), e.localizedMessage)
                }
            }
            )
        } else {
            hideProgress()
        }
    }

    fun callAddCardAPI(strName: String, cardToken: String) {
        /*   if (utilsManager.isNetworkConnected()){
               viewModel.addCard(requireActivity(),
                   preferenceManager.getString(Constants.KEY_ACCESS_TOKEN).toString(), customerStripId,cardToken,strName
               ).observe(requireActivity(), Observer { res ->
                   StaticData.printLog("saved new card  res", "--->" + res)
                   showToast(requireActivity(),res.message)
                   if (res.status) {
                       dialogAddcard.dismiss()
                      callCardListAPI()
                   }else{
                       hideProgress()
                   }


               })
           }*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}