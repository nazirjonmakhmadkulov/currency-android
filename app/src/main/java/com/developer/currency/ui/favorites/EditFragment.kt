package com.developer.currency.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.databinding.FragmentEditBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EditFragment : Fragment(R.layout.fragment_edit) {
    private val viewBinding by viewBinding(FragmentEditBinding::bind)
    private val args: EditFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager2
    private lateinit var pageAdapter: PagerAdapter
    private lateinit var tabLayout: TabLayout

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

    private fun setupTap() {
        val tabs = arrayOf(getString(R.string.favorites), getString(R.string.all))

        pageAdapter = PagerAdapter(requireActivity(), tabs.size, args.favorite)
        viewPager = viewBinding.tabViewpager
        viewPager.adapter = this.pageAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.isUserInputEnabled = false
        viewPager.isSaveEnabled = true
        tabLayout = viewBinding.tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position -> tab.text = tabs[position] }.attach()
    }
}