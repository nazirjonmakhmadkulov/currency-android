package com.developer.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.favorite.databinding.FragmentPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagerFragment : Fragment(R.layout.fragment_pager) {
    private val viewBinding by viewBinding(FragmentPagerBinding::bind)
    private val args: PagerFragmentArgs by navArgs()

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