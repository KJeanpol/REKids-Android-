package com.example.root.rekids.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.rekids.Logic.RegularExpressions;
import com.example.root.rekids.R;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by root on 25/08/17.
 */

public class Game3Activity extends AppCompatActivity {
    GridLayout myGridLayout;
    Button buttong, buttonc;
    EditText exp;
    TextView texto;
    RegularExpressions regEx;
    int[] images = {R.drawable.background};
    String[] Alphat={"a","b","c","d","e","f","g","h",};
    String[] expressions = {"a*c*b*", "ms*c", "(as)*", "(v+w+d)*", "adc*", "a(ad*+c*a)",
            "a(bc)*", "(bd)*c", "dbc*","h*","(t+z)*"};
    String expression = expressions[generateRandom()];
    String[] matrix;
    String matrixEntry;

    String[] ids = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game3_activity);
        matrix = new String[15];
        matrixEntry = "";


        myGridLayout = (GridLayout) findViewById(R.id.mygrid);

        buttong = (Button) findViewById(R.id.generar);
        texto = (TextView) findViewById(R.id.leng);
        buttonc = (Button) findViewById(R.id.or);
        exp =(EditText) findViewById(R.id.expression);
        buttong.setOnClickListener(generar);
        buttonc.setOnClickListener(or);


    }


    public String[] generateMatrix(String[] Alphabet, String exp) {
        for (int i = 0; i < 15; i++) {
            matrixEntry = generateLanguage(Alphabet, exp);
                matrix[i] = matrixEntry;

        }

        return matrix;
    }



    //REGEX LOGIC
    /*

     */



    public int generateRandom() {
        Random random = new Random();
        int num = (int) ((Math.random() * 15) + 1);
        System.out.println(num);
        return num;
    }

    public String generateLanguage(String[] Alphabet, String exp) {
        String[] alphabet = Alphabet;
        int num = generateRandom();
        String newLanguage = "";
        for (int i = 0; i < num;) {
            int pos = (int) (Math.random() * alphabet.length);
            newLanguage += alphabet[pos];
            i++;
        }

        if (isLanguage(newLanguage, exp)) {

                return newLanguage;
            } else {

            return generateLanguage(alphabet, exp);
        }
    }

    public boolean isLanguage(String language, String expression) {
        return Pattern.matches(expression, language);
    }

    View.OnClickListener or =new View.OnClickListener(){
        @Override
        public void onClick(View v){
            exp.append("|");
        }
    };



    View.OnClickListener generar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {


                matrix=generateMatrix(Alphat,exp.getText().toString());
                texto.setText("");
                for(int i=0;i<matrix.length;i++){
                    texto.append(matrix[i]+"\n");
                }


            }catch (Exception e){
                Toast.makeText(Game3Activity.this,
                        "EXPRESION NO VALIDA",
                        Toast.LENGTH_SHORT).show();

            }
        }
    };}


