package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Antre
import com.plugin.bigproject.models.Partners

interface DetailPartnerActivityContract {
    interface DetailPartnerView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun showDetailPartner(partner : Partners)
        fun showWaitinglist(waitingList : List<Antre>)
    }

    interface DetailParnerPresenter{
        fun getPartnerbyId( token : String,id : Int)
        fun booking(token : String, idMitra : Int, status : String)
        fun getAntre(token : String, idMitra : Int)
        fun destroy()
    }


}