package com.pushpender.moengageassignment.ui.homepage

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pushpender.moengageassignment.R
import com.pushpender.moengageassignment.SortOptions
import com.pushpender.moengageassignment.databinding.ActivityHomeBinding
import com.pushpender.moengageassignment.makeGone
import com.pushpender.moengageassignment.makeVisible
import com.pushpender.moengageassignment.network.ApiResult
import com.pushpender.moengageassignment.parseDate
import com.pushpender.moengageassignment.ui.ViewModelFactory
import com.pushpender.moengageassignment.ui.homepage.models.NewsArticlesModel
import com.pushpender.moengageassignment.ui.homepage.repository.NewsRepository
import com.pushpender.moengageassignment.ui.homepage.viewmodels.NewsViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.messaging.FirebaseMessaging

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: NewsAdapter

    private val viewModel by lazy {
        ViewModelProvider(
            this, ViewModelFactory(NewsRepository())
        )[NewsViewModel::class.java]
    }

    private var newsList: List<NewsArticlesModel> = listOf()

    private val filterOptionsList = listOf(
        SortOptions.NEW_TO_OLD.sortType,
        SortOptions.OLD_TO_NEW.sortType
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.showLoading = true
        getFirebaseToken()
        initObserver()
        setObservers()
        handleClickListener()
        addFilterOptions()
    }

    private fun handleClickListener() {

        binding.btnFilter.setOnClickListener {
            if (binding.filterChipLayout.isVisible) {
                binding.filterChipLayout.makeGone()
                binding.txtSortBy.makeGone()
            } else {
                binding.filterChipLayout.makeVisible()
                binding.txtSortBy.makeVisible()
            }
        }
    }

    private fun initObserver() {
        viewModel.getNews()
    }

    private fun setObservers() {

        viewModel.newsLiveData.observe(this) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    binding.showLoading = false
                    newsList = apiResult.data.articles
                    setNewsAdapter()
                }

                is ApiResult.Loading -> {
                    binding.showLoading = true
                }

                is ApiResult.Error -> {
                    binding.showLoading = false
                    Toast.makeText(this, apiResult.error.status, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addFilterOptions() {
        // Adding chips inside chipsGroup
        val chipGroupBinding = binding.filterChipLayout

        // Will Add the filter options into the chip-group with chips
        filterOptionsList.forEach { chipText ->
            val chip = Chip(this)
            chip.text = chipText
            chip.typeface = Typeface.create(ResourcesCompat.getFont(this, R.font.poppins_medium), Typeface.NORMAL)
            chip.setTextColor(ContextCompat.getColorStateList(this, R.color.chip_seleted_text_color))
            chip.chipBackgroundColor = ContextCompat.getColorStateList(this, R.color.chip_selected_states)
            chip.isCheckable = true

            chip.setOnClickListener {
                chipGroupBinding.children.forEach { view ->
                    (view as Chip).isChecked = chip.id == view.id
                }
                setNewsAdapter(applyFilter = true, sortOptions = chipText)
            }
            chipGroupBinding.addView(chip)
        }
    }

    private fun setNewsAdapter(
        applyFilter: Boolean = false,
        sortOptions: String = ""
    ) {

        if (applyFilter) {
            applyFilterAndUpdateList(sortOptions)
        }

        adapter = NewsAdapter(newsList) { url ->
            /** uncomment this code to open the news inside the application **/
            //val intent = Intent(this, WebViewActivity::class.java)
            // intent.putExtra(KEY_NEWS_URL, url)
            // startActivity(intent)

            /** If you are uncommenting the above code then comment this code **/
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.adapter = adapter
    }

    private fun applyFilterAndUpdateList(sortOptions: String) {
        when (sortOptions) {
            SortOptions.NEW_TO_OLD.sortType -> {
                newsList = newsList.sortedByDescending {
                    parseDate(it.publishedAt ?: "")
                }
            }

            SortOptions.OLD_TO_NEW.sortType -> {
                newsList = newsList.sortedBy {
                    parseDate(it.publishedAt ?: "")
                }
            }

            else -> {}
        }
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            Log.d("FCM_TOKEN", "onCreate: ${task.result}")
        }
    }
}