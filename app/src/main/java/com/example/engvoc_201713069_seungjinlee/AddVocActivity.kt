package com.example.engvoc_201713069_seungjinlee

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_voc.*
import java.io.PrintStream
import java.util.*

class AddVocActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voc)
        init()
    }

    private fun init() {
        openFileOutput("new.txt", Context.MODE_APPEND)
        addbtn.setOnClickListener {
            val word = editText.text.toString()  // 입력한 영단어를 가지고옴
            val meaning = editText2.text.toString()   // 입력한 뜻을 가지고옴
            writeFile(word, meaning)       // 사용자가 입력한 영어단어와 뜻을 파일에 쓰는 함수 만들어서 밑에서 정의
        }
        cancelbtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)   // 결과값 취소
            finish()  //앱 종료
        }
    }

    private fun writeFile(word: String, meaning: String) {        // 사용자가 입력한것 파일에 쓰는함수
        val output = PrintStream(
            openFileOutput("new.txt", Context.MODE_APPEND)
        )    // new.txt라는 파일에다가 사용자가 입력한 값을 출력할거야!
        // append라는것 파일에다가 계쏙해서 내용을 추가할거야~
        output.println(word)       // 한줄에는 영어
        output.println(meaning)    // 그다음줄에는 뜻
        output.close()
        val i = Intent()
        i.putExtra("voc", MyData(word, meaning))      // intent 객체에 voc이라는거를 넘길건데 MyData에 해당하는 객체..
        setResult(Activity.RESULT_OK, i)
        finish()
    }

}

