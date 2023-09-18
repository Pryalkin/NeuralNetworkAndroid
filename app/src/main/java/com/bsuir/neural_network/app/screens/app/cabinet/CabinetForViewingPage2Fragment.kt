package com.bsuir.neural_network.app.screens.app.cabinet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsuir.neural_network.app.dto.ApplicationAnswerDTO
import com.bsuir.neural_network.app.dto.utils.Status
import com.bsuir.neural_network.app.views.CabinetViewModel
import com.bsuir.neural_network.databinding.FragmentCabinetForViewingPage2Binding
import kotlin.properties.Delegates

class CabinetForViewingPage2Fragment : Fragment() {

    private var pageNumber by Delegates.notNull<Int>()
    private lateinit var binding: FragmentCabinetForViewingPage2Binding
    private val viewModel by viewModels<CabinetViewModel>()
    private lateinit var adapter: ApplicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCabinetForViewingPage2Binding.inflate(inflater)
        val role: String = viewModel.getRole()
        when (pageNumber) {
            0 -> {
                configureTheAdapterForApplication(Status.INACTIVITY.name, role)
            }
            1 -> {
                configureTheAdapterForApplication(Status.PAYMENT.name, role)
            }
            2 -> {
                configureTheAdapterForApplication(Status.ACTIVITY.name, role)
            }
            3 -> {
                configureTheAdapterForApplication(Status.READY.name, role)
            }
        }
        return binding.root
    }

    private fun configureTheAdapterForApplication(name: String, role: String) {
        adapter = ApplicationAdapter(object  : AActionListener {
            override fun onDetails(applicationAnswerDTO: ApplicationAnswerDTO) {
                TODO("Not yet implemented")
            }
            override fun payment(app: ApplicationAnswerDTO) {
                viewModel.payment(app.id)
            }
            override fun accept(app: ApplicationAnswerDTO) {
               viewModel.accept(app.id)
            }
            override fun ready(app: ApplicationAnswerDTO) {
               viewModel.ready(app.id)
            }
        }, role)
        viewModel.app.observe(viewLifecycleOwner){
            adapter.applicationAnswerDTOs = it
        }
        val layoutManagerAnnouncement = LinearLayoutManager(context)
        binding.recyclerViewApplication.layoutManager = layoutManagerAnnouncement
        binding.recyclerViewApplication.adapter = adapter
        val itemAnimatorAnnouncement = binding.recyclerViewApplication.itemAnimator
        if (itemAnimatorAnnouncement is DefaultItemAnimator){
            itemAnimatorAnnouncement.supportsChangeAnimations = false
        }
        when(name){
            Status.INACTIVITY.name -> {
                viewModel.getInactivity()
            }
            Status.PAYMENT.name -> {
                viewModel.getPayment()
            }
            Status.ACTIVITY.name -> {
                viewModel.getActivity()
            }
            Status.READY.name -> {
                viewModel.getReady()
            }
        }
    }

//    private fun customizeScreen(b: Boolean, b1: Boolean, b2: Boolean, ) {
//        binding.apply {
//            if (b) seeImages.visibility = View.VISIBLE
//            else seeImages.visibility = View.GONE
//            if (b1) seeHistory.visibility = View.VISIBLE
//            else seeHistory.visibility = View.GONE
//            if (b2) sub.visibility = View.VISIBLE
//            else sub.visibility = View.GONE
//        }
//    }

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