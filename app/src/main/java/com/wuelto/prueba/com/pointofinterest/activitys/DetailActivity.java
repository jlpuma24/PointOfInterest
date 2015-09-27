package com.wuelto.prueba.com.pointofinterest.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.wuelto.prueba.com.pointofinterest.R;

public class DetailActivity extends Activity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView = (TextView) findViewById(R.id.textViewDescription);
        intent = getIntent();
        textView.setText(intent.getStringExtra("Description"));
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
