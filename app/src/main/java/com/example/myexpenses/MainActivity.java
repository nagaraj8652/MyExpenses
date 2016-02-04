package com.example.myexpenses;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    Button b1,b2,b5,in_a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        
        b5=(Button)findViewById(R.id.aboutus);
        
        in_a=(Button)findViewById(R.id.add_income);
        
     
        
        
        // call the e_view method which shows the details of expenses
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				e_view();
			}
		});
        //call e_add method which helps to add the details of the expenses
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				e_add();
			}
		});
        b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				aboutus();
			}
		});
        
        in_a.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				income_add();
			}
		});
        
    }
    //view the details of expenses
      public void e_view()
      {
    	  Intent i=new Intent();
    	  i.setClass(this,Ex_info.class);
    	  startActivity(i);
      }
    //add Expenses
      public void e_add()
      {
    	  Intent i=new Intent();
    	  i.setClass(this,Ex_add.class);
    	  startActivity(i);
      }
      //add income
      public void income_add()
      {
    	  Intent i=new Intent();
    	  i.setClass(this,In_add.class);
    	  startActivity(i);
      }
      //about us
      public void aboutus()
      {
    	  Intent i=new Intent();
    	  i.setClass(this,About_us.class);
    	  startActivity(i);
      }
      

}
