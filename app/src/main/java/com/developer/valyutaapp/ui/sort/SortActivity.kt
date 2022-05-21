package com.developer.valyutaapp.ui.sort

import androidx.appcompat.app.AppCompatActivity
import com.developer.valyutaapp.R
import com.developer.valyutaapp.ui.adapter.FavoriteAdapter
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding

class SortActivity : AppCompatActivity(R.layout.activity_sort){
    private val viewBinding by viewBinding(
        com.developer.valyutaapp.databinding.ActivitySortBinding::bind,
        R.id.container
    )
    private val TAG = "SortActivity"
    var adapter: FavoriteAdapter? = null
//    var sortPresenter: SortPresenter? = null
//    private var valuteRespository: ValuteRespository? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val valuteDatabase = ValuteDatabase.getInstance(this) //create db
//        Log.d("Database ", valuteDatabase.toString())
//        valuteRespository =
//            ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()))
        setupMVP()
        setupViews()
        valuteList
    }

    private fun setupMVP() {
        ///sortPresenter = SortPresenter(this.applicationContext, this)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.layoutManager = LinearLayoutManager(this)
    }

    private val valuteList: Unit
        get() {
            //sortPresenter!!.valutes()
        }

//    override fun showToast(str: String) {
//        Toast.makeText(this@SortActivity, str, Toast.LENGTH_LONG).show()
//    }
//
//    override fun showProgressBar() {
//        viewBinding.progressBar.visibility = View.VISIBLE
//    }
//
//    override fun hideProgressBar() {
//        viewBinding.progressBar.visibility = View.GONE
//    }
//
//    override fun displayValutes(valute: List<Valute>) {
//        adapter = SortAdapter(valute, this@SortActivity)
//        adapter!!.setClickListener(this)
//        viewBinding.recyclerValCurs.adapter = adapter
//    }
//
//    override fun displayError(e: String) {
//        showToast(e)
//    }
//
//    override fun itemClicked(item: Valute?, position: Int, sortValute: Int) {
//        if (item != null) {
//            item.sortValute = sortValute
//            // sortPresenter.updateValute(item);
//        }
//    }
}