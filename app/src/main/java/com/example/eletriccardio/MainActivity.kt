package com.example.eletriccardio

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.eletriccardio.databinding.ActivityMainBinding
import com.example.eletriccardio.ui.adapter.CarAdapter
import com.example.eletriccardio.ui.adapter.TabsAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var bindind : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)
        setupTabs()
    }

    private fun setupTabs() {
        val tabsAdapter = TabsAdapter(this)
        bindind.viewPager.adapter = tabsAdapter

        val tabLayout  = bindind.tabLayout
        tabLayout.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    bindind.viewPager.currentItem = it.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })

        bindind.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }





}