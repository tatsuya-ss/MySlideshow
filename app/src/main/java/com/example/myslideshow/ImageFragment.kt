package com.example.myslideshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myslideshow.databinding.FragmentImageBinding

private const val IMG_RES_ID = "IMG_RES_ID"

class ImageFragment : Fragment() {

    private var imageResId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 安全呼び出し演算子？とスコープ関数letを使ってnullじゃないことを確認
        arguments?.let {
            // 取り出したBundleオブジェクトはlet関数内ではitでアクセスできる
            imageResId = it.getInt(IMG_RES_ID)
        }
    }

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageResId?.let {
            binding.imageView.setImageResource(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // コンパニオンオブジェクト = スタティックメソッド
    companion object {

        // フラグメント内のImageViewに表示する画像のリソースIDを引数に取る
        // フラグメントのインスタンスを生成して返す
        @JvmStatic
        fun newInstance(imageResId: Int): ImageFragment =
            ImageFragment().apply {
                // アーギュメントに保存するデータはバンドルクラスのインスタンス
                // Bundle()はいくつかの値を「まとめる」クラス
                arguments = Bundle().apply {
                    putInt(IMG_RES_ID, imageResId)
                }
            }

        }
}