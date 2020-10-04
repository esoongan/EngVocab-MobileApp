package com.example.engvoc_201713069_seungjinlee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_type.*

class ChooseTypeActivity : AppCompatActivity() {

    val toEngFragment = ToEngFragment()  // 객체생성
    val toKorFragment = ToKorFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_type)
        init()
    }
    private fun init() {
        val fragment = supportFragmentManager.beginTransaction()
        //fragment.addToBackStack(null)     ( 백스택에 저장하니까 뒤로가기 누를때마다 다시 이전단어들이나, 한글과 영어보기 왓다갓다 한게 다 되돌아가서 처음화면으로 돌아갈수없음. 나는 이미
        //이전문제랑 다음문제로 가는 버튼을 만들었기 때문에 굳이 백스택에 저장할필요 없음)
        // 보캡 액티비티에서 프레그먼트 들어갈 자리를 frame이라하면 거긱에 프레그먼트 달아주는 구
        fragment.replace(R.id.frame, toEngFragment)
        fragment.commit()
        toEng_btn.setOnClickListener {
            if (!toEngFragment.isVisible) {
                val fragment = supportFragmentManager.beginTransaction()
                //fragment.addToBackStack(null)
                // 보캡 액티비티에서 프레그먼트 들어갈 자리를 frame이라하면 거긱에 프레그먼트 달아주는 구
                fragment.replace(R.id.frame, toEngFragment)
                fragment.commit()
            }
        }
        toKor_btn.setOnClickListener {
            if (!toKorFragment.isVisible) {
                val fragment = supportFragmentManager.beginTransaction()
                //fragment.addToBackStack(null)
                // 보캡 액티비티에서 프레그먼트 들어갈 자리를 frame이라하면 거긱에 프레그먼트 달아주는 구
                fragment.replace(R.id.frame, toKorFragment)
                fragment.commit()
            }

        }
        /* private fun init() {
        eng_kor_btn.setOnClickListener{  // 첫번째 버튼을 누르면 영어문제_한글보기에 해당하는 퀴즈로 연결
            val i = Intent(this, MainActivity::class.java) //단어장으로 넘어가야되니까 인텐트 생성 this여기서 mainact의 클래스로 이동
            startActivity(i)// intent를 받아서 액티비티 시작
        }
        kor_eng_btn.setOnClickListener{ // 두번째 버튼을 누르면 한글문제_영어보기에 해당하는 퀴즈로 연결
            val i = Intent(this, Main2Activity::class.java)
            startActivity(i)
        }
    }*/
    }
}
