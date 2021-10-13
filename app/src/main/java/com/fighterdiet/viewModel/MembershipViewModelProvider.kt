package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.AboutPaulinNordinRepository
import com.fighterdiet.data.repository.MembershipRepository

class MembershipViewModelProvider(private val membershipRepository: MembershipRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return MembershipViewModel(membershipRepository)as T
    }
}