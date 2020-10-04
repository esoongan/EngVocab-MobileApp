package com.example.engvoc_201713069_seungjinlee

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_vocab_eng.*
import kotlinx.android.synthetic.main.row.*
import java.util.*
import kotlin.collections.ArrayList


class VocabEngFragment : Fragment() {
    var words = mutableMapOf<String, String>()
    var array = ArrayList<String>()
    var array2 = ArrayList<String>()

    lateinit var fragadapter:MyFragAdapter // MyFragAdapter를 상속받는 객체 생성
    lateinit var tts:TextToSpeech // TextToSpeech클래스를 상속받는 객체 생성
    var isTtsSReady = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocab_eng, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        tts = TextToSpeech(this.requireContext(), TextToSpeech.OnInitListener {
            isTtsSReady = true
            tts.language = Locale.US
        })
        readFile()
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(),
            LinearLayoutManager.VERTICAL, false)
        fragadapter = MyFragAdapter(array, array2)
        fragadapter.ItemClickListener = object :MyFragAdapter.OnItemClickListener{
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun OnItemClick(
                holder: MyFragAdapter.MyViewHolder,
                view: View,
                data: String,
                position: Int
            ) {
                if(isTtsSReady && textView2.visibility == View.GONE)
                    tts.speak(data, TextToSpeech.QUEUE_FLUSH, null, null)

                if(holder.textView2.visibility == View.VISIBLE)
                    holder.textView2.visibility = View.GONE
                else
                    holder.textView2.visibility = View.VISIBLE
            }
        }
        recyclerView.adapter = fragadapter
        val simpleCallback = object:ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                fragadapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                fragadapter.removeItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun readFile(){
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            words[word] = meaning
            array.add(word)
            array2.add(meaning)
        }
        scan.close()
    }
}
