package com.drac.text_to_speech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    EditText editText;
    Button button;
    TextToSpeech textToSpeech;
    int code = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == code) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                textToSpeech = new TextToSpeech(this, this);
            } else {
                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
