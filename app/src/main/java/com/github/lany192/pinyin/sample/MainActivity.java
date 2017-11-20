package com.github.lany192.pinyin.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.lany192.pinyin.PingYinUtil;

public class MainActivity extends AppCompatActivity {
    EditText mEditText;
    TextView mShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.my_edit_text);
        mShowText = findViewById(R.id.my_text_view);
        findViewById(R.id.my_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditText.getText().toString();
                mShowText.setText(PingYinUtil.getPingYin(input));
            }
        });
    }
}
