package com.example.engvoc_201713069_seungjinlee

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
//import kotlinx.android.synthetic.main.activity_main.recyclerview
import kotlinx.android.synthetic.main.activity_my_own_quiz.*
import java.util.*
import kotlin.collections.ArrayList
import java.util.Random as Random1


class MyOwnQuizActivity : AppCompatActivity() {
    var wordsKtoE = mutableMapOf<String, String>()
    var array0 = ArrayList<String>()    // 내가 추가한 영어만 넣을 배열
    var array2 = ArrayList<String>()    // 한글
    var arrayEx = ArrayList<String>()// 보기가 들어갈 배열
    var currentNumber: Int = 0                   // 현재 상태 0으로 설정
    lateinit var adapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_own_quiz)
        init()
        setButton()
        updateUi()
    }
    private fun readFileScan(scan: Scanner){     //    readFile()에서 스캐너로 읽은 객체를 인자로 전달하면 한줄씩 읽어서 배열에 저장하는 함수
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            wordsKtoE[meaning] = word           /// 한글이 키값이 되고 해당값이 영어
            array2.add(meaning)// 보기를 만들때 보기에 해당하는 것들이 들어있는 배열
        }
        scan.close()
    }

    private fun readFile() {
        val scan1 = Scanner(openFileInput("new.txt")) //스캐너로 add.txt파일 읽어들여서 scan1객체에 전
        while(scan1.hasNextLine()){        // 여기에 따로 와일문을 쓴 이유는 내가 추가한 영어단어만 array0이라는 배열에 저장하기 위해서
            val word = scan1.nextLine()
            val meaning = scan1.nextLine()   // 사용되진 않지만 다음줄이 한글이니까 따로 빼주기위해서 필요함
            array0.add(word)
        }
        scan1.close()

        val scan2 = Scanner(openFileInput("new.txt"))   // 보기에는 내가추가한단어랑, 기존단어도 포함되야하니까 새로또읽
        readFileScan(scan2)// scan객체를 인자로 해서 함수호출
        val scan3 = Scanner(resources.openRawResource(R.raw.words))   // 스캐너로 읽어들인 words파일 scan객체로 만들어서
        readFileScan(scan3)// scan객체를 인자로 해서 함수호출
    }


    private fun updateUi() {    //버튼을 눌렀을 처리할 이벤트 함수들 모음때(다음문제로 + 보기바꾸기)
        showQuestion()
        getRandom()
    }

    private fun showQuestion() {     // 문제 보여주는 함수
        quiz_words.text = array0[currentNumber]    // 영어단어가 들어가있는 배열 array1을 순차로 보여줌
    }

    private fun setButton() {

        prev_button.setOnClickListener {
            if (currentNumber == 0) {
                Toast.makeText(this, "첫번째 문제입니다.", Toast.LENGTH_SHORT).show()
            } else {
                currentNumber = currentNumber - 1
                updateUi()
            }
        }
        next_button.setOnClickListener {    // 다음문제 버튼을 눌럿을때
            if (currentNumber == array0.size - 1) { // 만약 내가 추가한 단어가 끝나면
                Toast.makeText(this, "더이상 추가한 단어가 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                currentNumber = currentNumber + 1
                updateUi()
            }
        }
    }
    private fun getRandom() {    // 보기 만드는 함수
        arrayEx.clear()
        for (count in 0..2) {
            val random = Random1()
            val randomIndex = random.nextInt(array2.size - 1)  // 한글의 개수중에서 랜덤하게 뽑아서
            val wrongAnswer = array2[randomIndex]      // 그 랜덤한 인덱스값의 문자를 줘
            array2.removeAt(randomIndex) // 중복되면 안되니까 뽑은건 지우고
            arrayEx.add(wrongAnswer)     // 어레이 배열에 에 롱앤설을 넣어
        }
        var answer = array2[currentNumber] // 정답을 넣어줄 변수생성
        arrayEx.add(answer)  // 한글인 array2에서 현재영단어의 뜻인 정답도 넣어줘야함
        arrayEx.shuffle() // 흔들어주세요 세킷쉐킷
        adapter.notifyDataSetChanged();     // 데이터가 바뀐걸 알려줘
    }

    private fun init() {     //대신 이 함수를 쓰려면 먼저 단어를 최소 하나라도 추가해야 함 안그러면 앱 실행이 되지않음..

        readFile()
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )   // 리사이클러뷰에 레이아웃매니저 달아줌
        adapter = MyAdapter(arrayEx)                     //   마이어댑터클래스에 4가지 보기를 담은 배열데이터 전달
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            //이벤트처리
            override fun OnItemCLick(
                holder: MyAdapter.MyViewHolder,
                view: View,
                data: String,
                position: Int
            ) {
                if (wordsKtoE[data] == quiz_words.text) {    // 클릭한 값의 한글뜻과
                    Toast.makeText(applicationContext, "정답입니다!", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(applicationContext, "오답입니다!", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter // recyclerview의 어댑터에 마이어댑터로 구현한 어댑터 달아줌
    }
}
