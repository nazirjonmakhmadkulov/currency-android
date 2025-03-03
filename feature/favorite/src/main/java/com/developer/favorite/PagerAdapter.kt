package com.developer.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.developer.favorite.currency.CurrencyFragment
import com.developer.favorite.favorite.FavoritesFragment

class PagerAdapter(
    fragment: FragmentActivity,
    private val itemCount: Int,
    private val favorite: String
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = itemCount
    override fun createFragment(position: Int): Fragment {
        val args = Bundle()
        args.putString("favorite", favorite)
        return when (position) {
            0 -> {
                val fragment = FavoritesFragment()
                fragment.arguments = args
                fragment
            }
            1 -> {
                val fragment = CurrencyFragment()
                fragment.arguments = args
                fragment
            }
            else -> createFragment(position)
        }
    }
}