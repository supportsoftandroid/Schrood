package com.food.schrood.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.education.geno.model.CardListResponse
import com.education.geno.model.CardResponse


class SaveCardViewModel : ViewModel() {


    lateinit var response: MutableLiveData<CardListResponse>
    lateinit var resAddCard: MutableLiveData<CardResponse>
    /*fun getCardList(context: Context, authToken: String,customer_token: String ): LiveData<CardListResponse> {
        response = StripCardRepository(context).getCardList(authToken,customer_token)
        return response
    }
    fun addCard(context: Context, authToken: String,customer_id: String,card_id: String,strName: String ): LiveData<CardResponse> {
        resAddCard = StripCardRepository(context).addCard(authToken,customer_id,card_id,strName)
        return resAddCard
    }fun deleteCard(context: Context, authToken: String,customer_id: String,card_id: String ): LiveData<CardResponse> {
        resAddCard = StripCardRepository(context).deleteCard(authToken,customer_id,card_id)
        return resAddCard
    }*/
}