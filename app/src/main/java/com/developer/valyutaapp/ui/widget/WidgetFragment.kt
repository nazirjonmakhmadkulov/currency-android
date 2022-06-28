package com.developer.valyutaapp.ui.widget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.databinding.FragmentSortBinding

class WidgetFragment : Fragment(R.layout.fragment_widget) {

    private val viewBinding by viewBinding(FragmentSortBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {

        }
    }
}