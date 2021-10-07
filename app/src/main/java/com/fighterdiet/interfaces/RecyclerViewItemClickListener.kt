package com.fighterdiet.interfaces

import android.view.View

interface RecyclerViewItemClickListener {
    fun onItemClick(position: Int, view: View?)
}