package com.bsuir.neural_network.app.screens.app.cabinet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bsuir.neural_network.app.views.CabinetViewModel
import com.bsuir.neural_network.databinding.FragmentCabinetForViewingPage2Binding
import kotlin.properties.Delegates

class CabinetForViewingPage2Fragment : Fragment() {

    private var pageNumber by Delegates.notNull<Int>()
    private lateinit var binding: FragmentCabinetForViewingPage2Binding
    private val viewModel by viewModels<CabinetViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCabinetForViewingPage2Binding.inflate(inflater)

        when (pageNumber) {
            0 -> {
                customizeScreen(b = true, b1 = false, b2 = false)
            }
            1 -> {
                customizeScreen(b = false, b1 = true, b2 = false)
            }
            2 -> {
                customizeScreen(b = false, b1 = false, b2 = true)
            }
        }
        return binding.root
    }

    private fun customizeScreen(b: Boolean, b1: Boolean, b2: Boolean) {
        binding.apply {
            if (b) seeImages.visibility = View.VISIBLE
            else seeImages.visibility = View.GONE
            if (b1) seeHistory.visibility = View.VISIBLE
            else seeHistory.visibility = View.GONE
            if (b2) sub.visibility = View.VISIBLE
            else sub.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(page: Int): CabinetForViewingPage2Fragment {
            val fragment: CabinetForViewingPage2Fragment = CabinetForViewingPage2Fragment()
            val args = Bundle()
            args.putInt("num", page)
            fragment.arguments = args
            return fragment
        }
    }
}