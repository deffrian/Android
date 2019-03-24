package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final int MAX_NUMBER_LEN = 10;

    TextView numberBottom;
    TextView numberTop;

    String valueBottom = "0";
    String valueTop = "";

    boolean isFloat = false;
    boolean locked = false;
    int numOfDigits = 0;

    char operation = ' ';

    void calculate(String str) {
        if (str.equals("+/-")) {
            valueBottom = Double.toString(-Double.parseDouble(valueBottom));
            numberBottom.setText(valueBottom);
            return;
        }

        char character = str.charAt(0);

        if (character == 'C') {
            isFloat = false;
            valueBottom = "0";
            valueTop = "";
            locked = false;
            operation = ' ';
            numOfDigits = 0;
        } else if (Character.isDigit(character) && numOfDigits < MAX_NUMBER_LEN && !valueBottom.equals("Infinity") && !valueBottom.equals("NaN") && !valueBottom.equals("-Infinity") && !locked) {
            if (valueBottom.equals("0")) {
                valueBottom = "" + character;
            } else {
                valueBottom += character;
            }
            numOfDigits++;
        } else if (character == '.' && !isFloat && !valueBottom.equals("Infinity") && !valueBottom.equals("NaN") && !locked) {
            valueBottom += '.';
            isFloat = true;
        } else if (character == '+' || character == '-' || character == '*' || character == '/') {
            locked = false;
            valueTop = valueBottom;
            valueBottom = "0";
            numOfDigits = 0;
            isFloat = false;
            operation = character;
        } else if (character == '=') {
            if (valueTop.equals("")) {
                return;
            }
            Double first = Double.parseDouble(valueTop);
            Double second = Double.parseDouble(valueBottom);
            Double res;
            switch (operation) {
                case '+':
                    res = first + second;
                    break;
                case '-':
                    res = first - second;
                    break;
                case '*':
                    res = first * second;
                    break;
                case '/':
                    res = first / second;
                    break;
                default:
                    return;
            }
            locked = true;
            operation = ' ';
            valueTop = "";
            valueBottom = res.toString();
        } else if (character == '%') {
            valueBottom = Double.toString(Double.parseDouble(valueBottom) / 100);
        }
        numberTop.setText(valueTop);
        numberBottom.setText(valueBottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberBottom = findViewById(R.id.numberBottom);
        numberTop = findViewById(R.id.numberTop);
        if (savedInstanceState != null) {
            valueBottom = savedInstanceState.getString("valueBottom");
            valueTop = savedInstanceState.getString("valueTop");
            isFloat = savedInstanceState.getBoolean("isFloat");
            locked = savedInstanceState.getBoolean("locked");
            numOfDigits = savedInstanceState.getInt("numOfDigits");
            operation = savedInstanceState.getChar("operation");
            numberBottom.setText(valueBottom);
            numberTop.setText(valueTop);
        }

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMult = findViewById(R.id.buttonMult);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonPersent = findViewById(R.id.buttonPersent);
        Button buttonC = findViewById(R.id.buttonC);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        Button buttonSign = findViewById(R.id.buttonSign);
        Button buttonPoint = findViewById(R.id.buttonPoint);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate((String) ((Button) v).getText());
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonPlus.setOnClickListener(listener);
        buttonMinus.setOnClickListener(listener);
        buttonMult.setOnClickListener(listener);
        buttonDivide.setOnClickListener(listener);
        buttonPersent.setOnClickListener(listener);
        buttonC.setOnClickListener(listener);
        buttonCalculate.setOnClickListener(listener);
        buttonSign.setOnClickListener(listener);
        buttonPoint.setOnClickListener(listener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("valueBottom", valueBottom);
        outState.putString("valueTop", valueTop);
        outState.putBoolean("isFloat", isFloat);
        outState.putBoolean("locked", locked);
        outState.putInt("numOfDigits", numOfDigits);
        outState.putChar("operation", operation);
        super.onSaveInstanceState(outState);
    }
}
