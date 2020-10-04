package com.example.engvoc_201713069_seungjinlee

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_to_eng.*
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class ToEngFragment : Fragment() {
    var wordsKtoE = mutableMapOf<String, String>()
    var wordsEtoK = mutableMapOf<String, String>()
    var array1 = ArrayList<String>()    // 영어
    var array2 = ArrayList<String>()    // 한글
    var arrayEx = ArrayList<String>()// 보기가 들어갈 배열
    var currentNumber: Int = 0                   // 현재 상태 0으로 설정
    lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_eng, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        setButton()
        updateUi()
    }

    private fun readFileScan(scan: Scanner){     //    readFile()에서 스캐너로 읽은 객체를 인자로 전달하면 한줄씩 읽어서 배열에 저장하는 함수
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            wordsKtoE[meaning] = word           /// 한글이 키값이 되고 해당값이 영어
            array1.add(word)   // 화면에 출력될 영어 단어 배열 --> 텍스트뷰꺼
            array2.add(meaning)// array 배열로 생성 나중에 화면에 한글 뜻 출력하려고  --> 리사이클러뷰꺼
        }
        scan.close()
    }

    private fun readFile() {
        val scan2 = Scanner(requireActivity().openFileInput("new.txt")) //스캐너로 new.txt파일 읽어들여서 scan2객체에 전
        readFileScan(scan2)//추가한 단어가 있는 new.txt파일 불러들임
        val scan = Scanner(resources.openRawResource(R.raw.words))   // 스캐너로 읽어들인 words파일 scan객체로 만들어서
        readFileScan(scan) // scan객체를 인자로 해서 함수호
    }


    private fun updateUi() {    //버튼을 눌렀을 처리할 이벤트 함수들 모음때(다음문제로 + 보기바꾸기)
        showQuestion()
        getRandom()
    }

    private fun showQuestion() {     // 문제 보여주는 함수
        quiz_words.text = array1[currentNumber]    // 영어단어가 들어가있는 배열 array1을 순차로 보여줌
    }

    private fun setButton() {

        prev_button.setOnClickListener {
            if (currentNumber == 0) {
                Toast.makeText(this.requireContext(), "첫번째 문제입니다.", Toast.LENGTH_SHORT).show()
            } else {
                currentNumber = currentNumber - 1
                updateUi()
            }
        }
        next_button.setOnClickListener {
            currentNumber = currentNumber + 1
            updateUi()
        }
    }
    private fun getRandom() {    // 보기 만드는 함수
        arrayEx.clear()
        for (count in 0..2) {
            val random = Random()
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

    private fun init() {
        val output = PrintStream(
            requireActivity().openFileOutput("new.txt", Context.MODE_APPEND)
        )           // 단어를 추가하지 않아도 일반 퀴즈는 볼 수 있게 함 (이구문을 추가안하니까 단어를 추가하지 않으면 초기에 퀴즈가 실행되지 않음)
        readFile()
        recyclerView.layoutManager = LinearLayoutManager(
            this.requireContext(),
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
                    Toast.makeText(requireContext(), "정답입니다!", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(requireContext(), "오답입니다!", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter // recyclerview의 어댑터에 마이어댑터로 구현한 어댑터 달아줌
    }

}
