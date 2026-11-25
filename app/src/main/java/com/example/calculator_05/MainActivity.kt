package com.example.calculator_05

import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //ประกาศตัวแปร
    private lateinit var  display: TextView //แสดงผลลัพ
    private  var current = "" //คำที่กำลังพิมพ์อยู๋
    private var  operator = ""
    private var  firstNumber = 0.0
    private var  secondNumber = 0.0
    private var  result = 0.0
    private var  isNewOperation = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อม textview เข้้ากับ layout
        display = findViewById(R.id.txedisplay)

        //กลุ่มปุ่มตัวเลข 0-9
        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
        )

        //กำหนด event ให้ปุ่มตัวเลขทุกปุ่ม
        for (id in numberButton){
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }
        //เชื่อโยงปุ่มคำนวน
        findViewById<Button>(R.id.btnplus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnDele).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnMulj).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { operatorClicked("/") }
        findViewById<Button>(R.id.btnMod).setOnClickListener { operatorClicked("%") }


        //เชื่อมโยงปุ่มพิเศษ = AC . +-
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnAC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btnAA).setOnClickListener { toogleSign() }
        findViewById<Button>(R.id.btnDel).setOnClickListener { backSpace() }
    }
    //เมื่อกดปุ่มตัวเลข
    private  fun  numberClicked(value: String){
        if (isNewOperation){
            display.text = ""
            isNewOperation = false
        }
        current += value
        display.text = current
    }
    //เมื่อกดปุ่มเคร่องหมาย
    private fun operatorClicked(op: String){
        if (current.isNotEmpty()){
            firstNumber = current.toDouble()
            operator = op
            current = ""
        }
    }
    private fun calculateResult(){
        if (current.isNotEmpty()){
            secondNumber = current.toDouble()
            result = when (operator){
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                "%" -> if (secondNumber != 0.0) firstNumber % secondNumber else Double.NaN
                else -> 0.0
            }
            //ถ้าหารด้วย 0
            display.text = if (result.isNaN()) "Cannot divied by zero" else result.toString()
            current = result.toString()
            isNewOperation = true
        }
    }

    private fun clearAll(){
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        isNewOperation = true
    }

    //ฟังก์ชั่น dot

    private  fun addDot(){
        if (!current.contains(".")){
            if (current.isEmpty()) current = "0" //ถ้ายังไม่มีเลข ให้ขึ้นต้นด้วย 0
            current += "."
            display.text = current
        }
    }

    private fun toogleSign(){
        if (current.isNotEmpty() && current != "0"){
            current = if (current.startsWith("-")) {
                current.substring(1)
            } else {
                "-$current"
            }
            display.text = current
        }
    }
    //ay'dN=yjox6j,][mu]t9y;
    private fun backSpace(){
        //ถ้าจอกำลังแสดงผลสัพธ์อยู่ จะไทม่ให้ลบทีละตัว
       if (isNewOperation) return

        if (current.isNotEmpty()){
            current = current.drop(1) //ตัดตัวสุดท้ายออก 1 ตัว

            if (current == "-" || current.isEmpty()) {
                current = ""
                display.text = "0"
                } else {
                    display.text = current
            }
        }


    }
}