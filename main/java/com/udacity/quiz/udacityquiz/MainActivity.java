package com.udacity.quiz.udacityquiz;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    ArrayList<Question> questions=new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        /**
         * we can generate ArrayList of Question and create a runtime Customized View to show Question in display
         * we can make get data from some database to make a different Interview with the same code (in this sample i don'tuse database)
         */
        generateViewQuestions(generateQuestions());

    }

    /**
     *
      * @return in this method define an ArrayList of Question , for each Question you can define Question description,type,and suggest in some case some response
     */
    ArrayList<Question> generateQuestions()
    {

        questions=new ArrayList<Question>();

        Question q1=new Question();
        q1.question=getString(R.string.question1);

        /**
         * implement possible response for multiple question
          */
        HashMap<String,Integer> resp1 = new HashMap<String, Integer>();
        resp1.put(getString(R.string.responseQuestion1_1),10);
        resp1.put(getString(R.string.responseQuestion1_2),5);
        resp1.put(getString(R.string.responseQuestion1_3),2);

        q1.response=resp1;
        q1.typeQuestion= Question.typeQuestionEnum.multipla;


        Question q2=new Question();
        q2.question=getString(R.string.question2);
        q2.typeQuestion= Question.typeQuestionEnum.intero;


        Question q3=new Question();
        q3.question=getString(R.string.question3);
        q3.typeQuestion= Question.typeQuestionEnum.multipla;
        resp1 = new HashMap<String, Integer>();
        resp1.put(getString(R.string.responseQuestion3),10);
        q3.response=resp1;


        Question q4=new Question();
        q4.question=getString(R.string.question4);
        q4.typeQuestion= Question.typeQuestionEnum.boleana;


        Question q5=new Question();
        q5.question=getString(R.string.question5);
        q5.typeQuestion= Question.typeQuestionEnum.boleana;


        /**
        * add all question in ArrayList
         */
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);

        return questions;
}

    /**
     * in this method we generate a complete list of Views customized with a parameter witch is defined in Questions ArrayList<Question>
     * a
     *
     * @param questions this is a ArrayList<Question> contain each question parameter to show in display
     */
    public void generateViewQuestions(ArrayList<Question> questions)
    {
        LinearLayout rootLayout=(LinearLayout)findViewById(R.id.contentRoot);

         for(Question q :questions)
         {
             /**
              * create and define TextView will show the question
              */
             TextView questionView =new TextView(this);
             questionView.setText(q.question);
             LinearLayout.LayoutParams lpt =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
             lpt.topMargin=15;
             questionView.setLayoutParams(lpt);
             /**
              * create and define LinearLayout will contain Views of question
              */
             LinearLayout linearQuestion=new LinearLayout(this);
             linearQuestion.setOrientation(linearQuestion.VERTICAL);


             /**
              * in this code i will create a View general witch we redifined in base of information of
              * question View is base of other and i can use this object to define EditText,ButtonGroup,Switch etc..
              */
             View respObject=new View(this);
             switch( q.typeQuestion)
             {
                 case multipla:
                                respObject=generateMulitpleAsk(q.response);
                                break;
                 case intero:
                                respObject=generateIntegerViewAsk();
                                break;
                 case stringa:
                                respObject=generateTextViewAsk();
                                break;
                 case boleana:
                                respObject =generateBooleanaAsk(q.question);
                                break;
             }

             /**
              * now we define layout Question and View
              */
             LinearLayout.LayoutParams LP=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
             LP.setMargins(8,8,8,8);
             respObject.setLayoutParams(LP);
             respObject.setId(View.generateViewId());
             q.id=respObject.getId();
             linearQuestion.setLayoutParams(LP);
             linearQuestion.addView(questionView);
             linearQuestion.addView((View)respObject);
             rootLayout.addView(linearQuestion);
         }



    };

    /**
     *
     * @param asks this contain HashMap with possible response of a question and will generate a LinearLayout with contain a RadioGroup with possible options (RadioButton)
     *             we must add RadioGroup in LinearLayout without Layout is some bug and RadioGroup don't work fine , you can try to remove LinearLayout and return RadioGroup to simulate bug
     * @return
     */

    View generateMulitpleAsk(HashMap<String,Integer> asks)
    {
        LinearLayout LinearGroup=new LinearLayout(this);
        ViewGroup.LayoutParams LP=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        RadioGroup rg=new RadioGroup(this);
        rg.setLayoutParams(LP);
        for(Map.Entry<String, Integer> entry : asks.entrySet())
        {
            RadioButton rb=new RadioButton(this);
            String key = entry.getKey();
            rb.setText(key);
            rg.addView(rb);
        }
        LinearGroup.addView(rg);
        return LinearGroup;
    }

    /**
     *
     * @return generate and return a TextView
     */
    EditText generateTextViewAsk()
    {
        EditText result=new EditText(this);
        return result;
    }

    /**
     *
     * @return generate and return  customized TextView for numbers
     */
    EditText generateIntegerViewAsk()
    {
        EditText result=new EditText(this);
        result.setInputType(InputType.TYPE_CLASS_NUMBER);
        return result;
    }
    /**
     *
     * @return generate and return CheckBox for boolean response
     */
    CheckBox generateBooleanaAsk(String question)
    {
        CheckBox result=new CheckBox(this);
        result.setText(question);
        return result;
    }

    void submitResult(View v)
    {
        MediaPlayer mp=MediaPlayer.create(this,R.raw.beep);
        mp.start();
      int result=  getResult();
        Toast.makeText(this,getString(R.string.points) + String.valueOf(result) ,Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @return we get response of Survey
     */
    int getResult(){
        int points=0;

        LinearLayout rootLayout=(LinearLayout)findViewById(R.id.contentRoot);

        for(int i=0;i<questions.size();i++)
        {
         Question q=questions.get(i);

        switch (q.typeQuestion)
        {
            case intero:
                 TextView resulti=rootLayout.findViewById(q.id);
                 if(!resulti.getText().toString().equals(""))
                 {
                     points=points+Integer.valueOf(resulti.getText().toString());
                 }
                break;
            case stringa:
                 TextView results=rootLayout.findViewById(q.id);
                 if(!results.getText().toString().equals(""))
                 {
                    points = points + Integer.valueOf(results.getText().toString());
                 }
                break;
            case boleana:
                 CheckBox resultb=(CheckBox)rootLayout.findViewById(q.id);
                 if(resultb.isChecked()){points=points+1;};
                 break;
            case multipla:
                 int value=0;
                 LinearLayout ll=(LinearLayout)findViewById(q.id);
                 String key=q.getResultfromMultipla((RadioGroup)ll.getChildAt(0));
                 if(key!=null)
                 {
                    value = q.response.get(key).byteValue();
                    points = points + value;
                 }
                 break;
        }

        }
        return points;
    }
}
