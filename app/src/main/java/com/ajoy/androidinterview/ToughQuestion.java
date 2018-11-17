package com.ajoy.androidinterview;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class ToughQuestion extends AppCompatActivity implements View.OnClickListener {

    String[] tough_questions,tough_answers;
    TextView tv_questions, tv_answers, tvtotallength_yy, tvcurrentindex_xx, questionstype;
    Button buttonLeft, buttonAns, buttonRight, bmute;
    LinearLayout layout;
    int index;


    // variables for text to speech conversion
    TextToSpeech tts;
    int result;
    String text;
    boolean isSpeakerOn, isAnswerShown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // inside your activity(if you did not enable transition in your theme)
        // for AppCompat getWindow() must be called before calling super.onCreate() & setContentView()
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        initAnimation(); // cause the animation

        index = 0;
        isSpeakerOn = false;
        isAnswerShown = false;
        text = "Hello";

        //importing the String array from values folder
        tough_questions = getResources().getStringArray(R.array.tough_ques);
        tough_answers = getResources().getStringArray(R.array.tough_ans);

        //Initialization of TextView
        tv_questions = findViewById(R.id.tvquestion);
        tv_answers = findViewById(R.id.tvanswer);
        tvcurrentindex_xx = findViewById(R.id.tvxx);
        tvtotallength_yy = findViewById(R.id.tvyy);
        questionstype = findViewById(R.id.question_type);

        //Initialization of Buttons
        buttonLeft = findViewById(R.id.bleft);
        buttonAns = findViewById(R.id.bshowans);
        buttonRight = findViewById(R.id.bright);
        bmute = findViewById(R.id.bstop);

        //Onclick for all buttons
        buttonLeft.setOnClickListener(this);
        buttonAns.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
        bmute.setOnClickListener(this);

        //setting values for 4 textViews
        tv_questions.setText(tough_questions[index]);
        tv_answers.setText("Press \"A\" Button for Answer");
        tvcurrentindex_xx.setText(String.valueOf(index+1));
        tvtotallength_yy.setText("/"+String.valueOf(tough_questions.length));
        questionstype.setText("Tough Questions");

        layout = findViewById(R.id.ques_titlebar);

        //Initalization of TextToSpeech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS)
                {
                    result = tts.setLanguage(Locale.US);
                    tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }
                else
                {
                    String alert = "Your device doesn't support this feature.";
                    Toast toast = Toast.makeText(getApplicationContext(),alert,Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    private void initAnimation()  {

        Transition enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setEnterTransition(enterTransition);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.bright:
                tv_answers.setText("Press \"A\" Button for Answer");
                bmute.setBackground(getDrawable(R.drawable.off_button));
                isAnswerShown = false;
                index++;

                if(tts!=null)
                {
                    tts.stop();
                    isSpeakerOn = false;
                }

                if(index == tough_questions.length) {
                    index = 0;
                    tv_questions.setText(tough_questions[index]);
                    tvcurrentindex_xx.setText(String.valueOf(index+1));
                } else {
                    tv_questions.setText(tough_questions[index]);
                    tvcurrentindex_xx.setText(String.valueOf(index + 1));
                }
                break;

            case R.id.bshowans:
                tv_answers.setText(tough_answers[index]);

                if(isSpeakerOn && !isAnswerShown) {
                    bmute.setBackground(getDrawable(R.drawable.off_button));
                    if (tts != null) {
                        tts.stop();
                        isSpeakerOn = false;
                    }
                }
                isAnswerShown = true;
                break;

            case R.id.bleft:
                tv_answers.setText("Press \"A\" Button for Answer");
                bmute.setBackground(getDrawable(R.drawable.off_button));
                isAnswerShown = false;
                index--;

                if(tts!=null)
                {
                    tts.stop();
                    isSpeakerOn = false;
                }

                if(index == -1) {
                    index = tough_questions.length - 1;
                    tv_questions.setText(tough_questions[index]);
                    tvcurrentindex_xx.setText(String.valueOf(index + 1));
                } else {
                    tv_questions.setText(tough_questions[index]);
                    tvcurrentindex_xx.setText(String.valueOf(index + 1));
                }
                break;

            case R.id.bstop:

                if(isSpeakerOn) {
                    bmute.setBackground(getDrawable(R.drawable.off_button));
                    if (tts != null) {
                        tts.stop();
                        isSpeakerOn = false;
                    }
                }
                else {
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(),"Your device doesn't support this feature.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        text = tv_answers.getText().toString();
                        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                        bmute.setBackground(getDrawable(R.drawable.on_button));
                        isSpeakerOn = true;
                    }
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts!=null)
        {
            tts.stop();
            tts.shutdown();
        }
    }
}
