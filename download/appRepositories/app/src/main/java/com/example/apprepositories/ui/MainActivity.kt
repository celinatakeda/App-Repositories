package com.example.apprepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.apprepositories.R
import com.example.apprepositories.core.createDialog
import com.example.apprepositories.core.createProgressDialog
import com.example.apprepositories.core.hideSoftKeyboard
import com.example.apprepositories.databinding.ActivityMainBinding
import com.example.apprepositories.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.rvRepos.adapter = adapter

        viewModel.repos.observe(this) {
            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepoList(it) }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextChange: $newText")
        return false
    }

    companion object {
        private const val TAG = "TAG"
    }

}