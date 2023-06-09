package com.bsuir.neural_network.app.screens.app.cabinet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.neural_network.databinding.FragmentCabinetBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CabinetFragment  : Fragment() {

    private lateinit var binding: FragmentCabinetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCabinetBinding.inflate(inflater)
        val pager = binding.cabinet
        val pageAdapter: FragmentStateAdapter = CabinetAdapter(requireActivity())
        pager.adapter = pageAdapter

        val tabLayout: TabLayout = binding.tabCabinet
        val tabLayoutMediator = TabLayoutMediator(tabLayout, pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Неактивные"
                }
                1 -> {
                    tab.text = "Активные"
                }
                2 -> {
                    tab.text = "Готовые"
                }
            }
        }
        tabLayoutMediator.attach()
        return binding.root
    }


}