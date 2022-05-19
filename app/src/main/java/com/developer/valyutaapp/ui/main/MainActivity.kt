package com.developer.valyutaapp.ui.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.developer.valyutaapp.R
import com.developer.valyutaapp.ui.adapter.ValCursAdapter
import com.developer.valyutaapp.domain.entities.Valute
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.databinding.ActivityMainBinding
import com.developer.valyutaapp.ui.setting.SettingActivity
import com.developer.valyutaapp.ui.valute.ValuteActivity
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.service.auto.AutoService
import com.developer.valyutaapp.ui.ValuteViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity(R.layout.activity_main), MainViewInterface,
    ValCursAdapter.ClickListener {

    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)
    private val viewModel by viewModel<ValuteViewModel>()
    private val prefs: SharedPreference by inject()

    var adapter: ValCursAdapter? = null

    var valuteList: MutableList<Valute> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ValCursAdapter(valuteList, this@MainActivity)
        adapter!!.setClickListener(this)
        viewBinding.recyclerValCurs.adapter = adapter

        if (prefs.getBool() == "1") {
            startService(Intent(this, AutoService::class.java))
        } else if (prefs.getBool() == "0") {
            stopService(Intent(this, AutoService::class.java))
        }

        setupViews()
        setupViewModel()
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getLocalValutes().collect {
                getAllValuteSuccess(it)
            }
        }
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllValuteSuccess(valutes: List<Valute>) {
        valuteList.clear()
        valuteList.addAll(valutes)
        adapter?.notifyDataSetChanged()
    }

    override fun showToast(s: String?) {
        Toast.makeText(this@MainActivity, s, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        viewBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewBinding.progressBar.visibility = View.GONE
    }

    override fun displayValutes(valutes: List<Valute>?) {
        valutes?.let { getAllValuteSuccess(it) } ?: Log.d("TAG", "valutes response null")
    }

    override fun displayError(e: String?) {
        showToast(e)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.setting) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.update) {
            setupViews()
            viewModel.getRemoteValutes()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun itemClicked(item: Valute?, position: Int) {
        if (item != null) {
            val intent = Intent(applicationContext, ValuteActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("click", 22)
            startActivity(intent)
        }
    }
}