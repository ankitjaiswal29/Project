package com.fighterdiet.interfaces

import android.view.View

interface RecyclerItemClickListener {
    fun onItemClick(position: Int, selectedItem: Any?)
}