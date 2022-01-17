package com.example.myslideshow

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myslideshow.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private lateinit var player: MediaPlayer

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
        setupTimer()
        setupMediaPlayer()
    }

    override fun onResume() {
        super.onResume()
        player.start()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    class ViewPager2PageTransformation: ViewPager2.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            transformPager(page, position)
        }

        private fun transformPager(page: View, position: Float) {
            when {
                position < -1 -> {
                    shrinkView(page)
                }
                position <= 1 -> {
                    animateView(page, position)
                }
                else -> {
                    shrinkView(page)
                }
            }
        }

        private fun animateView(page: View, position: Float) {
            page.alpha = Math.max(0.2f, 1 - Math.abs(position))
            page.scaleX = Math.max(0.2f, 1 - Math.abs(position))
            page.scaleY = Math.max(0.2f, 1 - Math.abs(position))
        }

        private fun shrinkView(page: View) {
            page.alpha = 0.2f
            page.scaleX = 0.2f
            page.scaleY = 0.2f
        }

    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // ViewPager2とFragmentStateAdapterの関連付け
    private fun setupPager() {
        binding.pager.adapter = MyAdapter(this)
        binding.pager.setPageTransformer(ViewPager2PageTransformation())
    }

    private fun setupTimer() {
        // Handlerのインスタンスを取得
        // Looper.getMainLooper()を渡すとメインスレッド（UIスレッド）に接続できる
        val handler = Handler(Looper.getMainLooper())
        timer(period = 5000) {
            // メインスレッドに接続するhandlerを使って更新
            handler.post {
                binding.apply {
                    pager.currentItem = (pager.currentItem + 1) % 10
                }
            }
        }
    }

    private fun setupMediaPlayer() {
        player = MediaPlayer.create(this, R.raw.getdown)
        player.isLooping = true
    }

}