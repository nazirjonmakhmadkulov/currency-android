package com.developer.valyutaapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import com.developer.valyutaapp.R
import androidx.recyclerview.widget.RecyclerView
import com.developer.valyutaapp.adapter.ValCursAdapter
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository
import com.developer.valyutaapp.model.Valute
import android.os.Bundle
import butterknife.ButterKnife
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase
import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.databinding.ActivityMainBinding
import com.developer.valyutaapp.ui.setting.SettingActivity
import com.developer.valyutaapp.ui.valute.ValuteActivity
import com.developer.valyutaapp.utils.SharedPreference
import com.developer.valyutaapp.utils.Utils
import java.util.ArrayList

class MainActivity : AppCompatActivity(R.layout.activity_main), MainViewInterface, ValCursAdapter.ClickListener {

    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)

    private val TAG = "MainActivity"

    var adapter: ValCursAdapter? = null
    var mainPresenter: MainPresenter? = null
    private var valuteRespository: ValuteRespository? = null
    var valuteList: MutableList<Valute> = ArrayList()
    var sheredPreference: SharedPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ValCursAdapter(valuteList, this@MainActivity)
        adapter!!.setClickListener(this)
        viewBinding.recyclerValCurs.adapter = adapter
        val valuteDatabase = ValuteDatabase.getInstance(this) //create db
        Log.d("Database ", valuteDatabase.toString())
        valuteRespository =
            ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()))
        sheredPreference = SharedPreference(this)
//        if (sheredPreference!!.bool == "1") {
//            startService(Intent(this, AutoService::class.java))
//        } else if (sheredPreference!!.bool == "0") {
//            stopService(Intent(this, AutoService::class.java))
//        }
        setupMVP()
        setupViews()
        getValuteList()
    }

    private fun setupMVP() {
        mainPresenter = MainPresenter(this.applicationContext, this, valuteRespository!!)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.layoutManager = LinearLayoutManager(this)
    }

    private fun getValuteList() {
        if (Utils.hasConnection(this)) {
            mainPresenter!!.valutesRemote()
        } else {
            mainPresenter!!.valutesLocal()
        }
    }

    private fun onGetAllValuteSuccess(valutes: List<Valute>) {
        valuteList.clear()
        valuteList.addAll(valutes)
        adapter?.notifyDataSetChanged()
    }

    override fun showToast(str: String?) {
        Toast.makeText(this@MainActivity, str, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        viewBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewBinding.progressBar.visibility = View.GONE
    }

    override fun displayValutes(valutes: List<Valute>?) {
        valutes?.let { onGetAllValuteSuccess(it) } ?: Log.d(TAG, "valutes response null")
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
            getValuteList()
        }
        //        else if (id == R.id.about){
//            Intent intent = new Intent(this, AboutActivity.class);
//            startActivity(intent);
//        }
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

    override fun onDestroy() {
        super.onDestroy()
    }
}