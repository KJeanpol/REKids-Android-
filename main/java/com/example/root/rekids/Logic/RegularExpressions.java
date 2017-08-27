package com.example.root.rekids.Logic;


import java.util.Random;
import java.util.regex.Pattern;

public class RegularExpressions {

    public boolean compareLanguage(String pLanguage, String pExpression){
        return Pattern.matches(pExpression,pLanguage);
    }

    public int generateRandom() {
        Random random = new Random();
        //genera un numero entre 1 y 5 y lo guarda en la variable numeroAleatorio
        int num = 1+ random.nextInt(10);
        return num;
    }
    public String generateLanguage(String[] Alphabet, String exp) {
        String[] alphabet = Alphabet;
        boolean[] printed = new boolean[alphabet.length];
        int num=generateRandom();
        //System.out.println("Mi numero random es: "+num);
        String newLanguage="";
        for(int i = 0; i < num;){
            int pos =(int)(Math.random() * alphabet.length);
            newLanguage+=alphabet[pos];
            i++;

        }
        if (compareLanguage(newLanguage,exp)) {
            return newLanguage;
        }
        else {
            //System.out.println("Estos fueron posibles lenguajes: "+newLanguage);
            return generateLanguage(alphabet, exp);
        }

    }



}
