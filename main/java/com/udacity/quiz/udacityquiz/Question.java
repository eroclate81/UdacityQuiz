package com.udacity.quiz.udacityquiz;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by roberto.russo on 15/11/2017.
 */


public class Question {
    /**
     * in thi enum we define type of question based from this info will be different type View
     */
  public enum typeQuestionEnum {
        boleana,
        stringa,
        intero,
        multipla

    }

    /**
     * id must be a unique key to get the response from device
     */
    public int id;
    public String question;
    public typeQuestionEnum typeQuestion;
    public HashMap<String,Integer> response;


    String getResultfromMultipla(RadioGroup rg)
    {

        String val=null;
        for(int i=0;i<rg.getChildCount();i++)
        {
           RadioButton rb= (RadioButton)rg.getChildAt(i);
           if(rb.isChecked()){
              val= rb.getText().toString();

           }

        }
        return val;
    }

}
