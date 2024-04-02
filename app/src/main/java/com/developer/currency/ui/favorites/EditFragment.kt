package com.developer.currency.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.databinding.FragmentEditBinding
import com.google.android.material.tabs.TabLayoutMediator

class EditFragment : Fragment(R.layout.fragment_edit) {
    private val viewBinding by viewBinding(FragmentEditBinding::bind)
    private val args: EditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupTap()
    }

    private fun setupToolbar() = with(viewBinding) {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> findNavController().popBackStack()
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setupTap() = with(viewBinding) {
        val tabs = arrayOf(getString(R.string.favorites), getString(R.string.all))
        val pageAdapter = PagerAdapter(requireActivity(), tabs.size, args.favorite)
        tabViewpager.adapter = pageAdapter
        tabViewpager.offscreenPageLimit = 2
        tabViewpager.isUserInputEnabled = false
        tabViewpager.isSaveEnabled = true
        TabLayoutMediator(tabLayout, tabViewpager) { tab, pos -> tab.text = tabs[pos] }.attach()
    }
}