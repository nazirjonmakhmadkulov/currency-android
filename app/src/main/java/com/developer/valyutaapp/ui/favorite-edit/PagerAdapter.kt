package com.developer.valyutaapp.ui.`favorite-edit`

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

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
                val f = FavoritesFragment()
                f.arguments = args
                return f
            }
            1 -> {
                val f = ValutesFragment()
                f.arguments = args
                return f
            }
            else -> createFragment(position)
        }
    }
}
