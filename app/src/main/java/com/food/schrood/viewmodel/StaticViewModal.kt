package com.food.schrood.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


import com.food.schrood.model.CommonResponse
import com.food.schrood.model.FaqRespons
import com.food.schrood.model.TermsRespons


/*import com.food.schrood.model.FaqResponse
import com.food.schrood.model.TermsResponse
import com.food.schrood.network.Repository.FAQRepository
import com.food.schrood.network.Repository.HelpCenterRepository
import com.food.schrood.network.Repository.TermsRepository*/


class StaticViewModal : ViewModel() {
    lateinit var termsRespons: MutableLiveData<TermsRespons>
    lateinit var faqResponse: MutableLiveData<FaqRespons>
    lateinit var helpCenterResponse: MutableLiveData<CommonResponse>


/*
  fun getCallAPI(context: Context,type: String): LiveData<TermsResponse> {
      termsResponse = TermsRepository(context).getAPIResData(type)
      return termsResponse
  }
     fun getFaqAPI(context: Context,type: String): LiveData<FaqResponse> {
        faqResponse = FAQRepository(context).getAPIResData(type)
        return faqResponse
    }
    fun helpCenter(context: Context,authToken: String,name: String,email: String,message: String): LiveData<CommonResponse> {
        */
/*{
            "name":"rahul",
            "email":"rahul@gmail.com",
            "msg":"testing msg"
        }*//*

        helpCenterResponse = HelpCenterRepository(context).helpCenterAPI(authToken ,name ,email ,message )
        return helpCenterResponse
    }


*/

}