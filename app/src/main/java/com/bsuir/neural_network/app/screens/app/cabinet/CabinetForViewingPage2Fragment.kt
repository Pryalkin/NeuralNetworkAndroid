package com.bsuir.neural_network.app.screens.app.cabinet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsuir.neural_network.app.dto.HistoryAnswerDTO
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.views.CabinetViewModel
import com.bsuir.neural_network.databinding.FragmentCabinetForViewingPage2Binding
import kotlin.properties.Delegates

class CabinetForViewingPage2Fragment : Fragment() {

    private var pageNumber by Delegates.notNull<Int>()
    private lateinit var binding: FragmentCabinetForViewingPage2Binding
    private val viewModel by viewModels<CabinetViewModel>()
    private lateinit var imageAdapter: ImageForUserAdapter
    private lateinit var historyAdapter: HistoryForUserAdapter

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
                configureTheAdapterForTheImageForUser()
            }
            1 -> {
                customizeScreen(b = false, b1 = true, b2 = false)
                configureTheAdapterForTheHistoryForUser()
            }
            2 -> {
                customizeScreen(b = false, b1 = false, b2 = true)
                binding.btSub.setOnClickListener {
                    viewModel.subscribe()
                }
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

    private fun configureTheAdapterForTheHistoryForUser() {
        historyAdapter = HistoryForUserAdapter(object  : HistoryForUserActionListener {
            override fun copyLink(history: HistoryAnswerDTO) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, history.url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            override fun onHistoryDetails(history: HistoryAnswerDTO) {
                Toast.makeText(requireActivity(), history.url, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.histories.observe(viewLifecycleOwner){
            historyAdapter.historyAnswerDTOs= it
        }

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerHistory.layoutManager = layoutManager
        binding.recyclerHistory.adapter = historyAdapter

        val item = binding.recyclerHistory.itemAnimator
        if (item is DefaultItemAnimator){
            item.supportsChangeAnimations = false
        }
        viewModel.getAllHistoryForUser()
    }

    private fun configureTheAdapterForTheImageForUser() {
        imageAdapter = ImageForUserAdapter(object  : ImageForUserActionListener {
            override fun copyLink(image: ImageAnswerDTO) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, image.url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        })

        viewModel.images.observe(viewLifecycleOwner){
            imageAdapter.imageAnswerDTOs = it
        }

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewImage.layoutManager = layoutManager
        binding.recyclerViewImage.adapter = imageAdapter

        val itemAnimator = binding.recyclerViewImage.itemAnimator
        if (itemAnimator is DefaultItemAnimator){
            itemAnimator.supportsChangeAnimations = false
        }
        viewModel.getAllImagesForUser()
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