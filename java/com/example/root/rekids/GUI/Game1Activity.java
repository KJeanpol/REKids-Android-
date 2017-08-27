package com.example.root.rekids.GUI;

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

public class Game1Activity extends AppCompatActivity implements MyView.OnToggledListener{

    MyView[] myViews;

    GridLayout myGridLayout;
    Button buttong;
    ImageView a;
    RegularExpressions regEx;
    int[] images={R.drawable.background};
    String[] expressions={"(a+b)*","(ab)c*","a*","a+b*","abc*","a*b*c*","a(b+c)*","a+bc*","abc"};
    String[] languages=new String[4];
    String[] alphabet={"a,b,c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_activity);
        regEx=new RegularExpressions();

        myGridLayout = (GridLayout)findViewById(R.id.mygrid);

        buttong= (Button) findViewById(R.id.generar);
        buttong.setOnClickListener(generar);

        a=(ImageView) findViewById(R.id.expresion);
        
        //Esta vara es lo de los cuadros
        int numOfCol = myGridLayout.getColumnCount();
        int numOfRow = myGridLayout.getRowCount();
        myViews = new MyView[numOfCol*numOfRow];
        for(int yPos=0; yPos<numOfRow; yPos++){
            for(int xPos=0; xPos<numOfCol; xPos++){
                MyView tView = new MyView(this, xPos, yPos);
                tView.setOnToggledListener(this);
                myViews[yPos*numOfCol + xPos] = tView;
                myGridLayout.addView(tView);
            }
        }

        myGridLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {

                        final int MARGIN = 5;

                        int pWidth = myGridLayout.getWidth();
                        int pHeight = myGridLayout.getHeight();
                        int numOfCol = myGridLayout.getColumnCount();
                        int numOfRow = myGridLayout.getRowCount();
                        int w = pWidth/numOfCol;
                        int h = pHeight/numOfRow;

                        for(int yPos=0; yPos<numOfRow; yPos++){
                            for(int xPos=0; xPos<numOfCol; xPos++){
                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams)myViews[yPos*numOfCol + xPos].getLayoutParams();
                                params.width = w - 2*MARGIN;
                                params.height = h - 2*MARGIN;
                                params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                                myViews[yPos*numOfCol + xPos].setLayoutParams(params);
                            }
                        }

                    }});
    }

    //Este método cierra la aplicacion
    public void rellenaMatriz(String expression){
        for(int i=0;i<languages.length;i++){
            languages[i]=regEx.generateLanguage(alphabet,expression);
        }
    }

    //Si uso el random de la lógica, se cae si se le da seguido al boton
    int random(){
        Random rand = new Random();
        int num=rand.nextInt((8 - 0) + 1) + 0;
        return num;
    }

    //Listener del boton
    View.OnClickListener generar= new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            String expression=expressions[random()];
            buttong.setText(expression);

         //a.setImageResource(R.drawable.background);
        }
    };

    @Override
    public void OnToggled(MyView v, boolean touchOn) {

        //get the id string
        String idString = v.getIdX() + ":" + v.getIdY();

        Toast.makeText(Game1Activity.this,
                "Toogled:\n" +
                        idString + "\n" +
                        touchOn,
                Toast.LENGTH_SHORT).show();
    }


}

