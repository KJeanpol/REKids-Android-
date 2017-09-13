package com.example.root.rekids.GUI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.root.rekids.R;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by jose on 26/08/2017.
 */

/**
 * Metodo Move: Clase principal
 *
 */
public class Move extends Activity implements SensorEventListener2,OnClickListener {
    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float xMax, yMax;
    Bitmap airplane, mars,lune,heart;
    SensorManager sensorManager;
    Draw ourView;
    TextView texto, texto2, texto3,texto1,textoG;
    Button endGameButton, round;
    String[] Alphat={"a","b","c","d"};
    String[] expressions = {"a*c*b*",  "(ac)*", "adc*", "a(ad*|c*a)", "a(bc)*", "(bd)*c", "dbc*","a*|bdc*","b(c*a)|d","c|a|b*|d","d(ac)*","c*|b(ad*)","b*(adb)"};
    Random rand = new Random();
    int indice, indice2, opcion, ran;
    String[] matrix;
    String matrixEntry;

    LinearLayout gameWidgets;
    RelativeLayout textview;

    Rect rect1, rect2, rect3;
    int vidas = 2;
    int Puntos = 0;



    //---------------CONSTRUCTOR------------------
    public Move (){

    }


    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccel = sensorEvent.values[0];
            yAccel = -sensorEvent.values[1]; //LLLEVA -
            updateBit();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}


    /**
     *
     * @param Alphabet Alfabeto que utilizara la expresion regular
     * @param exp Expresion regular
     * @return
     */
    public String[] generateMatrix(String[] Alphabet, String exp) {
        for (int i = 0; i < 11; i++) {
            matrixEntry = generateLanguage(Alphabet, exp);
            matrix[i] = matrixEntry;

        }

        return matrix;
    }

    /**
     *
     * @return retorna un numero random
     */
    public int generateRandom() {
        Random random = new Random();
        int num = random.nextInt(5);
        return num;
    }

    /**
     *
     * @param Alphabet Alfabeto
     * @param exp Expresion
     * @return lenguage generado
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
            if (newLanguage.length() <= 11) {
                return newLanguage;
            } else {
                return generateLanguage(alphabet, exp);
            }

        } else {
            return generateLanguage(alphabet, exp);
        }
    }

    /**
     *
     * @param language Lenguaje
     * @param expression Expresion regular
     * @return retorna un boolean true o false dependiendo si el lenguaje le corresponda a la expresion
     */
    public boolean isLanguage(String language, String expression) {
        return Pattern.matches(expression, language);
    }

    /**
     * Provee los fps =fp, y la velocdad del movimiento de la nave, ademas define los limites en x y en y de la nave en el canvas
     */
    private void updateBit() {
        float frameTime = 10f;
        xVel += (xAccel * frameTime);
        yVel += (yAccel * frameTime);

        float xS = (xVel / 20) * frameTime; //PROVIDE THE ANGULE OF THE POSITION
        float yS = (yVel / 20) * frameTime;  // WITH LESS DENOMINADOR THE BALL MOVE IS MORE DIFFICULT

        xPos -= xS;
        yPos -= yS;

        if (xPos > xMax) {
            xPos = xMax;
        } else if (xPos < 0) {
            xPos = 0;
        }

        if (yPos > yMax) {
            yPos = yMax;
        } else if (yPos < 0) {
            yPos = 0;
        }


    }
    /**
    * Click de eventos
    */
    @Override
    public void onClick(View view) {
        if(view.getTag()== "Round"){
            texto2.setText("");
            texto2.append("POINTS:"+Puntos);
            texto3.setText("");
            texto3.append("LIFES:"+vidas);
            opcion = rand.nextInt(2);

            if( opcion ==1){

                Onwin();
            }
            else{

                Onwin2();
            }
            if(vidas==0){
                vidas+=2;
                Puntos=0;
            }

        }
        else if(view.getTag()== "Language"){
            xPos=0;
            yPos=0;
            texto3.setText("");
            texto3.append("LIFES:"+vidas);
            matrix=generateMatrix(Alphat,expressions[indice]);
            textoG.setText("");
            for(int i=0;i<matrix.length;i++){
                textoG.append(matrix[i]+"\n");
            }


        }

    }

    /**
     * Metodo donde se dibuja todo el juego
     */
    public class Draw extends SurfaceView implements Runnable {
        SurfaceHolder ourHolder;
        Thread ourThread = null;
        boolean isRunning = true;


        public Draw(Context context) {
            super(context);
            ourHolder = getHolder();
            Bitmap ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.ovni);
            Bitmap mars1 = BitmapFactory.decodeResource(getResources(), R.drawable.marte);
            Bitmap lune1 = BitmapFactory.decodeResource(getResources(), R.drawable.luna);
            Bitmap heart1 = BitmapFactory.decodeResource(getResources(), R.drawable.corazon);
            final int dstWidth = 100;
            final int dstHeight = 100;
            final int dstWidth1= 225;
            final int dstHeight2 = 310;
            final int dstWidth2= 355;
            final int dstHeight3 = 220;
            final int dstWidth3= 80;
            final int dstHeight4 = 80;
            airplane = Bitmap.createScaledBitmap(ballSrc, dstWidth, dstHeight, true);
            mars = Bitmap.createScaledBitmap(mars1, dstWidth1, dstHeight2, true);
            lune = Bitmap.createScaledBitmap(lune1, dstWidth2, dstHeight3, true);
            heart = Bitmap.createScaledBitmap(heart1, dstWidth3, dstHeight4, true);

        }

        /**
         * Pone en pausa el hilo del juego
         */
        public void pause(){
            isRunning = false;
            while (true){
                try{
                    ourThread.join();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }

            ourThread = null;

        }

        /**
         * Tras la pausa, resumen el hilo del juego
         */
        public void resume(){

            isRunning = true;
            ourThread = new Thread(this);
            ourThread.start();
        }

        /**
         * Dibuja en el canvas
         */
        public void draw() {
            try{
                while(vidas!=0) {
                    Canvas canvas = ourHolder.lockCanvas();
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.space), 0, 0, null);
                    canvas.drawBitmap(airplane, xPos, yPos, null);
                    canvas.drawBitmap(mars, 490, 0, null);
                    canvas.drawBitmap(lune, 430, 380, null);

                    for (int i = 1; i < vidas; i++) {
                        int x = 475;
                        int y = 285;
                        canvas.drawBitmap(heart, x, y, null);

                    }

                    //RECTANGULOS PARA COLISION//

                    RectF drawRect = new RectF();
                    RectF drawRect2 = new RectF();
                    RectF drawRect3 = new RectF();


                    drawRect.set(425, 50, 585, 215);
                    drawRect3.set(425, 400, 585, 575);
                    drawRect2.set(xPos, yPos, xPos + 100, yPos + 100);
                    ourHolder.unlockCanvasAndPost(canvas); //BLOQUEA EL CANVAS  NO SE PUEDE DIBUJAR NADA MAS DEBEMOS UTILIZARLO AL FINAL


                    rect1 = new Rect(450, 50, 575, 200);
                    rect2 = new Rect(Math.round(xPos), Math.round(yPos), Math.round(xPos + 100), Math.round(yPos + 100));
                    rect3 = new Rect(450, 400, 585,560);

                    //COLISION

                    if(opcion==1){
                        ourView.Colision(rect1, rect2, rect3);
                    }
                    else{
                        ourView.Colision2(rect1,rect2,rect3);
                    }


                }}
            catch (Exception e) {

            }
        }

        /**
         * Metodo de colision de la nave y las opciones
         * @param rect1 Rectangulo 1
         * @param rect2 Rectangulo 2
         * @param rect3 Rectangulo 3
         */
        public void Colision(Rect rect1, Rect rect2, Rect rect3){

            if (Rect.intersects(rect1, rect2)) {
                xPos =0;
                yPos = 0;
                vidas--;
                Puntos = Puntos-50;
            }
            else if(Rect.intersects(rect2,rect3)){
                yPos = 0;
                xPos = 0;
                Puntos += 100;
            }
        }

        /**
         * Metodo de colision de la nave y las opciones
         * @param rect1 Rectangulo 1
         * @param rect2 Rectangulo 2
         * @param rect3 Rectangulo 3
         */
        public void Colision2(Rect rect1, Rect rect2, Rect rect3){

            if (Rect.intersects(rect1, rect2)) {
                xPos =0;
                yPos = 0;
                Puntos += 100;
            }
            else if(Rect.intersects(rect2,rect3)){
                yPos = 0;
                xPos = 0;
                vidas--;
                Puntos = Puntos-50;

            }
        }

        /**
         * Metodo donde se inicializa el juego
         */
        @Override
        public void run(){
            while (isRunning){
                if(!ourHolder.getSurface().isValid())
                    continue;
                draw();
            }
        }

    }

    /**
     * Metodo que pinta las expresiones, y lleva el manejo de puntos y vidas
     */
    public void Onwin(){
        xPos=0;
        yPos=0;
        indice = rand.nextInt(11);
        if(indice==12){indice2= indice-1;}
        else{indice2= indice+1;};


        texto.setText("");
        texto.append(expressions[indice]);
        texto1.setText("");
        texto1.append(expressions[indice2]);

        matrix=generateMatrix(Alphat,expressions[indice]);
        textoG.setText("");
        for(int i=0;i<matrix.length;i++){
            textoG.append(matrix[i]+"\n");
        }

    }

    /**
     *     Metodo que pinta las expresiones, y lleva el manejo de puntos y vidas

     */
    public void Onwin2(){
        xPos=0;
        yPos=0;
        indice = rand.nextInt(11);
        if(indice==12){indice2= indice-1;}
        else{indice2= indice+1;};


        texto.setText("");
        texto.append(expressions[indice2]);
        texto1.setText("");
        texto1.append(expressions[indice]);

        matrix=generateMatrix(Alphat,expressions[indice]);
        textoG.setText("");
        for(int i=0;i<matrix.length;i++){
            textoG.append(matrix[i]+"\n");
        }

    }

    /**
     * Se inicializa la posocion de los widgets y los layouts
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matrix=new String[7];
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){

            Sensor s = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 150;
        yMax = (float) size.y -250;
        matrix = new String[11];
        matrixEntry = "";

        airplane = BitmapFactory.decodeResource(getResources(), R.drawable.ovni);
        mars = BitmapFactory.decodeResource(getResources(), R.drawable.marte);
        lune = BitmapFactory.decodeResource(getResources(), R.drawable.luna);
        heart = BitmapFactory.decodeResource(getResources(), R.drawable.corazon);

        ourView = new Draw(this);
        ourView.resume();
        texto = new TextView(this);
        texto1 = new TextView(this);
        textoG = new TextView(this);
        texto2 = new TextView(this);
        texto3 = new TextView(this);

        texto.setWidth(110);
        texto.setX(335);
        texto.setY(460);
        int RED = Color.RED;
        texto.setTextColor(RED);
        texto.setTextSize(14);

        texto1.setWidth(110);
        texto1.setX(230);
        texto1.setY(125);
        int WHITE = Color.WHITE;
        texto1.setTextColor(WHITE);
        texto1.setTextSize(14);

        texto2.setWidth(180);
        texto2.setX(60);
        texto2.setY(1000);
        texto2.setTextColor(WHITE);
        texto2.setTextSize(18);

        texto3.setWidth(180);
        texto3.setX(230);
        texto3.setY(1000);
        texto3.setTextColor(WHITE);
        texto3.setTextSize(18);

        textoG.setHeight(300);
        textoG.setWidth(100);
        textoG.setX(0);
        textoG.setY(580);
        int GREEN = Color.GREEN;
        textoG.setTextColor(GREEN);
        textoG.setTextSize(26);

        FrameLayout game = new FrameLayout(this);
        gameWidgets = new LinearLayout(this);
        textview = new RelativeLayout(this);

        endGameButton = new Button(this);
        endGameButton.setWidth(200);
        endGameButton.setX(500);
        endGameButton.setY(1000);
        endGameButton.setText("NEXT ROUND");
        endGameButton.setOnClickListener(this);
        endGameButton.setTag("Round");

        round = new Button(this);
        round.setWidth(200);
        round.setX(500);
        round.setY(900);
        round.setText("NEXT LANGUAGE");
        round.setOnClickListener(this);
        round.setTag("Language");


        gameWidgets.addView(endGameButton);
        gameWidgets.addView(texto);
        gameWidgets.addView(texto1);

        textview.addView(round);
        textview.addView(texto2);
        textview.addView(texto3);
        textview.addView(textoG);

        game.addView(ourView);
        game.addView(textview);
        game.addView(gameWidgets);

        setContentView(game);

    }

}
