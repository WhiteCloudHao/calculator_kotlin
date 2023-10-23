package com.example.Calculator

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.color.utilities.MaterialDynamicColors.background
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView;
    lateinit var output: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        output = findViewById(R.id.textView2)
    }
    fun buttonClicked(view: View) {
        val buttonId = view.id
        val buttonText = (view as Button).text.toString()
        Log.d("ButtonClicked", "Button ID: $buttonId, Text: $buttonText")
        var  backgroundColorTintLong = 0xFFB5B3B3
        val newTint = ColorStateList.valueOf(Color.BLUE)
        (view as Button).backgroundTintList = newTint
        when (buttonText) {
            "CE"-> {
                clearCurrentOperand()
            }
            "C" -> {
                textView.text = "";
            }
            "BS" -> {
                backSpace()
            }
            "/" -> {
                textView.text = addToInputText(buttonText);
            }
            "7" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "8" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "9" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "x" -> {
                textView.text = addToInputText(buttonText);
            }
            "4" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "5" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "6" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "-" -> {
                textView.text = addToInputText(buttonText);
            }
            "1" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "2" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "3" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "+" -> {
                textView.text = addToInputText(buttonText);
            }
            "+/-" -> {
                signReversal()
            }
            "0" -> {
                textView.text = addToInputText(buttonText);
                backgroundColorTintLong = 0xFFE8E5E5
            }
            "." -> {
                printLength()
            }
            "=" -> {
                showResult()
            }
        }
        val  backgroundColorTint = backgroundColorTintLong.toInt()
        Log.d("bg", "color $backgroundColorTint")
        Timer().schedule(object : TimerTask() {
            override fun run() {
                (view as Button).backgroundTintList = ColorStateList.valueOf(backgroundColorTint)
            }
        }, 100)

    }
    private fun addToInputText(buttonValue: String): String {
        return textView.text.toString() + "" + buttonValue;
    }
    private fun clearCurrentOperand(): Unit {
        val lastOperatorIndex = textView.text.toString().lastIndexOfAny(charArrayOf('+', '-', 'x', '/'));
        if (lastOperatorIndex != -1) {
            // Có toán tử, loại bỏ toàn bộ phần sau toán tử
            val newText = textView.text.toString().substring(0, lastOperatorIndex + 1); // +1 để bao gồm toán tử
            textView.text = newText;
        } else {
            // Không có toán tử, loại bỏ toàn bộ chuỗi
            textView.text = "";
        }
    }
    private fun signReversal() {
        val lastBracketIndex = textView.text.toString().lastIndexOf(")");
        if(lastBracketIndex != -1 && lastBracketIndex + 1 == textView.text.toString().length) {

                val lastOpenBracketIndex = textView.text.toString().lastIndexOf("(");
                val newText = textView.text.toString().substring(0, lastOpenBracketIndex) + textView.text.toString().substring(lastOpenBracketIndex+2, lastBracketIndex)  ; // +1 để bao gồm toán tử
                textView.text = newText;

        }
        else {
            val lastOperatorIndex = textView.text.toString().lastIndexOfAny(charArrayOf('+', '-', 'x', '/'));
            if (lastOperatorIndex != -1) {
                if(textView.text.toString().length > lastOperatorIndex+1) {
                    val newText = textView.text.toString().substring(0, lastOperatorIndex + 1) + "(-" +textView.text.toString().substring(lastOperatorIndex + 1, textView.text.toString().length)+ ")" ;
                    textView.text = newText;
                }
            } else {
                if(textView.text.toString().length > 0) {
                    val newText = "(-" +textView.text.toString()+ ")" ;
                    textView.text = newText;
                }

            }
        }

    }
    private fun backSpace() {
        if(textView.text.toString().isNotEmpty()) {
            val newText = textView.text.toString().substring(0, textView.text.toString().length -1)
            textView.text = newText;
        }

    }
    private fun getInputExpression(): String {
        var expression = textView.text.replace(Regex("x"), "*")
        return expression
    }
    private fun showResult() {
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                // Show Error Message
                output.text = ""
            } else {
                // Show Result
                output.text = DecimalFormat("0.######").format(result).toString()
            }
        } catch (e: Exception) {
            // Show Error Message
            output.text = ""
//            output.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
    }
    private fun printLength() {
        val length = textView.text.toString().length
        Log.d("ButtonClicked", "$length")
    }
}