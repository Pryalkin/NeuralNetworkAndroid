package com.bsuir.neural_network.app.screens.app.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.neural_network.app.views.HomeViewModel
import com.bsuir.neural_network.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        val role: String = viewModel.getRole()
        val pager = binding.home
        val pageAdapter: FragmentStateAdapter = HomeAdapter(requireActivity(), role)
        pager.adapter = pageAdapter

        val tabLayout: TabLayout = binding.tabHome
        val tabLayoutMediator = TabLayoutMediator(tabLayout, pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Изображения"
                }
                1 -> {
                    tab.text = "Добавить"
                }
            }
        }
        tabLayoutMediator.attach()
        return binding.root
    }

}