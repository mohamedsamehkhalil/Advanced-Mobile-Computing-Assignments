package com.example.ttsexample;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    @Override
//    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    tts.setLanguage(Locale.ENGLISH);
                    tts.setPitch(70f);
                    tts.setSpeechRate(1.1f);
                    tts.speak("Hello World",TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });
    }
}
