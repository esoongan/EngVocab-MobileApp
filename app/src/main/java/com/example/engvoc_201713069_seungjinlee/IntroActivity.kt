package com.example.engvoc_201713069_seungjinlee

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    val ADDVOC_REQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        init()
    }

    private fun init() {
        button.setOnClickListener{  // 첫번째 버튼을 누르면
            val i = Intent(this, ChooseTypeActivity::class.java) //단어장으로 넘어가야되니까 인텐트 생성 this여기서 mainact의 클래스로 이동
            startActivity(i)// intent를 받아서 액티비티 시작
        }
        button2.setOnClickListener{ // 두번째 버튼을 누르면
            val i = Intent(this, AddVocActivity::class.java)
            startActivityForResult(i, ADDVOC_REQUEST)
        }
        button3.setOnClickListener{// 세번째 버튼을 누르면
            val i = Intent(this, MyOwnQuizActivity::class.java)
            startActivity(i)
        }
        button4.setOnClickListener{
            val i = Intent(this, VocabActivity::class.java)
            startActivity(i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            ADDVOC_REQUEST->{
                if(resultCode== Activity.RESULT_OK){
                    val str = data?.getSerializableExtra("voc")as MyData        // voc을 키값으로 하는 mydata객체를 넘김
                    Toast.makeText(this,str.word+"단어가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

// 여기에 추가할 기능. 1. 문제를 한글로, 영어를 보기로 바꾸기. 2. 음성 출력하기 3. 단어추가한거 삭제해보기 4. 등등