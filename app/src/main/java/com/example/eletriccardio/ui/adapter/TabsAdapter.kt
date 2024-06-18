package com.example.eletriccardio.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eletriccardio.ui.CarFragment
import com.example.eletriccardio.ui.FavoritosFragment

class TabsAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
         return when(position){
              0 -> CarFragment()
              1 -> FavoritosFragment()
              else -> CarFragment()
          }
    }
}