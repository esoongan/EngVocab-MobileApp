package com.example.engvoc_201713069_seungjinlee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vocab.*


class VocabActivity : AppCompatActivity() {
    val vocabEngFragment = VocabEngFragment()
    val vocabKorFragment = VocabKorFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocab)
        init()
    }

    private fun init() {
        val fragment = supportFragmentManager.beginTransaction()
        //fragment.addToBackStack(null)
        // 보캡 액티비티에서 프레그먼트 들어갈 자리를 frame이라하면 거긱에 프레그먼트 달아주는 구문, 초기에는 디폴트로 영어문제로 출제
        fragment.replace(R.id.frameLayout, vocabEngFragment)
        fragment.commit()
        showEng.setOnClickListener {
            if (!vocabEngFragment.isVisible){  // 현재 영어로 문제가 출제되고 있지 않을때, 영어문제 버튼을 누르면 바꿔라
                val fragment = supportFragmentManager.beginTransaction()
                //fragment.addToBackStack(null)
                // 보캡 액티비티에서 프레그먼트 들어갈 자리를 frame이라하면 거긱에 프레그먼트 달아주는 구문
                fragment.replace(R.id.frameLayout, vocabEngFragment)
                fragment.commit()
            }
        }
        showKor.setOnClickListener {
            if (!vocabKorFragment.isVisible){  // 현재 한글로 문제가 출제되고 있지 않으면 버튼을 눌렀을때 한글로 바꿔라
                val fragment = supportFragmentManager.beginTransaction()
                //fragment.addToBackStack(null)
                // 보캡 액티비티에서 프레그먼트 들어갈 자리를 frame이라하면 거긱에 프레그먼트 달아주는 구문
                fragment.replace(R.id.frameLayout, vocabKorFragment)
                fragment.commit()
            }

        }
    }


}
