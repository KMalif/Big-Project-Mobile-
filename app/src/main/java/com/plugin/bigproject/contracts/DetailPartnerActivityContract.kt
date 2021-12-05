package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Partners

interface DetailPartnerActivityContract {
    interface DetailPartnerView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun showDetailPartner(partner : Partners)
    }

    interface DetailParnerPresenter{
        fun getPartnerbyId(id : Int)
        fun destroy()
    }


}