package com.semba.geodistance.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.semba.geodistance.BR
import com.semba.geodistance.R
import com.semba.geodistance.base.BaseActivity
import com.semba.geodistance.data.models.UserData
import com.semba.geodistance.databinding.ActivityMainBinding
import com.semba.geodistance.ui.adapters.UsersAdapter
import com.semba.geodistance.utils.MarginItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var adapter: UsersAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    /**
     * Generate the desired viewModel which is based on the injected "viewModelFactory".
     * Define the navigator interface over the generated viewModel.
     */
    override fun afterInjection() {
        this.assignViewModel(viewModelFactory, MainViewModel::class.java)
        this.mViewModel.setNavigator(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUsersList()
        mViewModel.executeLogic()
    }

    /**
     * Initialize UsersAdapter
     * Define recyclerView options
     * Assign the adapter
     */
    private fun setupUsersList() {
        adapter = UsersAdapter()
        users_recyclerView?.layoutManager = LinearLayoutManager(this)
        users_recyclerView?.addItemDecoration(MarginItemDecoration(this))
        users_recyclerView?.adapter = adapter
    }

    override fun showErrorMessage(message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun validateRecyclerView(users: ArrayList<UserData>) {
        adapter?.updateItems(users)
    }

    override fun toggleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
