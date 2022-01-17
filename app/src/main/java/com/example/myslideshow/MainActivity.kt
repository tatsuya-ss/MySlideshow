package com.example.myslideshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myslideshow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // MainActivity専用クラスなのでネストで書いている
    // FragmentStateAdapterを継承したクラス
    class MyAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

        private val resources = listOf(
            R.drawable.slide00, R.drawable.slide01,
            R.drawable.slide02, R.drawable.slide03,
            R.drawable.slide04, R.drawable.slide05,
            R.drawable.slide06, R.drawable.slide07,
            R.drawable.slide08, R.drawable.slide09
        )

        // 総ページ
        override fun getItemCount(): Int {
            return resources.size
        }

        // 引数にページを受け取り、対応するフラグメントを戻り値
        override fun createFragment(position: Int): Fragment {
            return ImageFragment.newInstance(resources[position])
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupPager()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // ViewPager2とFragmentStateAdapterの関連付け
    private fun setupPager() {
        binding.pager.adapter = MyAdapter(this)
    }
}