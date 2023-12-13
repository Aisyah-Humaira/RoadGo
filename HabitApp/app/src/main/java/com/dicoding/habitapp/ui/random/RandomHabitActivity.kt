package com.dicoding.habitapp.ui.random

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.ViewModelFactory
import com.dicoding.habitapp.utils.HABIT
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.dicoding.habitapp.ui.countdown.CountDownActivity

class RandomHabitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_habit)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val adapter = RandomHabitAdapter { habit ->
            val intent = Intent(this, CountDownActivity::class.java)
            intent.putExtra(HABIT, habit)
            startActivity(intent)
        }
        viewPager.adapter = adapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = "Habit ${position+1}"
        }.attach()

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory).get(RandomHabitViewModel::class.java)

        viewModel.priorityLevelHigh.observe(this) { pagedList ->
            // Ambil item pertama dari pagedList, karena kita hanya ingin satu item
            val habit = pagedList?.getOrNull(0)
            if (habit != null) {
                adapter.submitData(RandomHabitAdapter.PageType.HIGH, habit)
            }
        }
        viewModel.priorityLevelMedium.observe(this) { pagedList ->
            val habit = pagedList?.getOrNull(0)
            if (habit != null) {
                adapter.submitData(RandomHabitAdapter.PageType.MEDIUM, habit)
            }
        }
        viewModel.priorityLevelLow.observe(this) { pagedList ->
            val habit = pagedList?.getOrNull(0)
            if (habit != null) {
                adapter.submitData(RandomHabitAdapter.PageType.LOW, habit)
            }
        }
    }
}
