package com.example.root.rekids.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.root.rekids.Logic.RegularExpressions;
import com.example.root.rekids.R;
import java.util.Random;
import java.util.regex.Pattern;

public class Game1Activity extends AppCompatActivity implements MyView.OnToggledListener {

    //COMPONENTES DE GUI
    MyView[] myViews;
    GridLayout myGridLayout;
    Button buttong, buttonc;
    ImageView a;
    int[] images = {R.drawable.i0,R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,
            R.drawable.i5,R.drawable.i6,R.drawable.i7};
    String[] Alphat={"a","b","c","d"};
    String[] expressions = {"a*c*b*", "ba*c", "(db)*", "(a|b|d)*", "adc*",
            "(ad*)|(c*a)", "a(bc)*", "(bd)*c", "dbc*"};
    int rand=generateRandom();
    String expression = expressions[rand];
    String[][] matrix;
    String[][] matrixShown;
    String matrixEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_activity);
        matrix = new String[5][5];
        matrixEntry = "";

        matrix=generateMatrix(Alphat,expression);
        matrixShown=generateFMatrix(matrix);

        myGridLayout = (GridLayout) findViewById(R.id.mygrid);

        buttong = (Button) findViewById(R.id.generar);
        buttonc = (Button) findViewById(R.id.compare);
        a=(ImageView) findViewById(R.id.expresion);
        buttong.setOnClickListener(generar);
        buttonc.setOnClickListener(compare);


        a.setImageResource(images[rand]);

        //GENERA CELDAS PARA EL JUEGO
        int numOfCol = myGridLayout.getColumnCount();
        int numOfRow = myGridLayout.getRowCount();
        myViews = new MyView[numOfCol * numOfRow];

        for (int yPos = 0; yPos < numOfRow; yPos++) {
            for (int xPos = 0; xPos < numOfCol; xPos++) {
                MyView tView = new MyView(this, xPos, yPos, matrixShown[yPos][xPos]);
                tView.setOnToggledListener(this);
                myViews[yPos * numOfCol + xPos] = tView;
                myGridLayout.addView(tView);
            }
        }
        myGridLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        final int MARGIN = 5;
                        int pWidth = myGridLayout.getWidth();
                        int pHeight = myGridLayout.getHeight();
                        int numOfCol = myGridLayout.getColumnCount();
                        int numOfRow = myGridLayout.getRowCount();
                        int w = pWidth / numOfCol;
                        int h = pHeight / numOfRow;

                        for (int yPos = 0; yPos < numOfRow; yPos++) {
                            for (int xPos = 0; xPos < numOfCol; xPos++) {
                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams) myViews[yPos * numOfCol + xPos].getLayoutParams();
                                params.width = w - 2 * MARGIN;
                                params.height = h - 2 * MARGIN;
                                params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                                myViews[yPos * numOfCol + xPos].setLayoutParams(params);
                            }
                        }

                    }
                });
    }


    /**Genera Matriz
     *
     * @param Alphabet Alfabeto que utilizara la expresion regular
     * @param exp Expresion regular
     * @return
     */
    public String[][] generateMatrix(String[] Alphabet, String exp) {
        for (int i = 0; i < 5; i++) {
            matrixEntry = generateLanguage(Alphabet, exp);

            for (int j = 0; j < 5; j++) {
                matrix[i][j] = String.valueOf(matrixEntry.charAt(j));


            }
        }

        return matrix;
    }

    /**Genera matriz con caracteres ocultos
     *
     * @param mat
     * @return
     */
    public String[][] generateFMatrix(String[][] mat) {
        matrixShown=matrix;
        Random random = new Random();
        int num;
        for(int i=0;i<5;i++) {
            num= 1 + random.nextInt(4);
            matrixShown[i][num]="F";
        }
        return matrixShown;
    }


    //REGEX LOGIC

    /**Compara lenguaje con expresion
     *
     * @param pLanguage lenguaje
     * @param pExpression expresion
     * @return
     */
    public boolean compareLanguage(String pLanguage, String pExpression){
        return Pattern.matches(pExpression,pLanguage);
    }



    /*
    Generate random integer
     */
    public int generateRandom() {
        Random random = new Random();
        int num = 1 + random.nextInt(5);
        return num;
    }

    /**Genera lenguajes
     *
     * @param Alphabet Alfabeto
     * @param exp expresion
     * @return
     */
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
            if (newLanguage.length() == 5) {
                return newLanguage;
            } else {
                return generateLanguage(alphabet, exp);
            }

        } else {
            return generateLanguage(alphabet, exp);
        }
    }

    /**Compara lenguaje
     *
     * @param language lenguaje
     * @param expression expresion
     * @return
     */
    public boolean isLanguage(String language, String expression) {
        return Pattern.matches(expression, language);
    }



    /*
    Checks if the answer given is correct
     */
    public boolean compare(){
        boolean cond=true;
        for(int i=0;i<5;i++){
            if(compareLanguage(langs(matrixShown[i]),expression)){
                cond=true;
            }else{
                cond=false;
                return cond;
            }
        }
        return cond;
    }

    /**
     * Retorna los lenguajes de la matriz
     * @param a Matriz lenguajes
     * @return
     */
    public String langs(String[] a){
        String res="";
        for(int i=0;i<a.length;i++){
            res+=a[i];
        }
        return res;
    }


    /*
    Listener
     */
    View.OnClickListener generar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
            overridePendingTransition( 0, 0);
            startActivity(getIntent());
            overridePendingTransition( 0, 0);
        }
    };

    /*
    Listener
     */
    View.OnClickListener compare = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(compare()){
                Toast.makeText(Game1Activity.this,
                        "RESPUESTA CORRECTA",
                        Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);

            }else{

                Toast.makeText(Game1Activity.this,
                        "RESPUESTA INCORRECTA",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    /*
    Game GUI Components listener
     */
    @Override
    public void OnToggled(MyView v, boolean touchOn) {

        //get the id string
        matrixShown[v.getIdY()][v.getIdX()]=v.getValor(v.shapes);
        //System.out.println(matrixShown[v.getIdY()][v.getIdX()]);


    }


}
