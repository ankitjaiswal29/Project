package com.fighterdiet.interfaces

import android.view.View

interface RecyclerItemClickListener {
    fun onItemClick(operationType: Int, selectedItem: Any?, pos: Int)
}