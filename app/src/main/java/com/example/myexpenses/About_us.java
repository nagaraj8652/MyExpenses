package com.example.myexpenses;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TextView;

/**
 * Created by Omkar on 10/9/2015.
 */
public class About_us extends Activity{
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        t1 =(TextView)findViewById(R.id.textView);
        t2 =(TextView)findViewById(R.id.textView1);



    }


}
