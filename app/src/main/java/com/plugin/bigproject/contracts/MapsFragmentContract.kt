package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Partners

interface MapsFragmentContract {
    interface Presenter{
        fun getMitra()
    }

    interface View{
        fun attachMitraLocation(listMitra : List<Partners>)
        fun showToast(message : String)
    }
}