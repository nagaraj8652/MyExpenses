package com.example.myexpenses;



import java.util.Calendar;


import android.os.Bundle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;


public class Selectoption extends Activity {


	 private Calendar cal;
	 private int day;
	 private int month;
	 private int year;
	 private EditText et,et1;
	 private ImageButton ib,ib1;
	 private Button b1;
	 public String category;
	 public int flag=0;
     Dbhelper mydb;

    //EditText t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.selectoption);
                            
        et = (EditText) findViewById(R.id.date1);
        et1 = (EditText) findViewById(R.id.date2);
        ib = (ImageButton) findViewById(R.id.imageButton1);
        b1 = (Button) findViewById(R.id.button1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        
        ib1 = (ImageButton) findViewById(R.id.imageButton2);
        
        ib.setOnClickListener(new View.OnClickListener() {
			
     			@SuppressWarnings("deprecation")
				@Override
     			public void onClick(View arg0) {
     				// TODO Auto-generated method stub
     				 showDialog(0);
     			}
     		});
        
        ib1.setOnClickListener(new View.OnClickListener() {
			
     			@SuppressWarnings("deprecation")
				@Override
     			public void onClick(View arg0) {
     				// TODO Auto-generated method stub
     				 showDialog(1);
     				
     			}
     		});
        
        //Enter button
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				validate();
			}
		});
 
    }

    public final Dialog onCreateDialog(int id) {
    	if(id==1)
    	{
    		return new DatePickerDialog(this, datePickerListener, year, month, day);
    	}
    	else
    		return new DatePickerDialog(this, datePickerListener1, year, month, day);
  	  
  	 }

  	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
  	  public void onDateSet(DatePicker view, int selectedYear,
  	    int selectedMonth, int selectedDay) {
  	   et1.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
  	     + selectedYear);
  	  }
  	 };
  	 
  	 private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
  	  	  public void onDateSet(DatePicker view, int selectedYear,
  	  	    int selectedMonth, int selectedDay) {
  	  	   et.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
  	  	     + selectedYear);
  	  	  }
  	  	 };
  	  	 
         public void onRadioButtonClicked(View view) {
             // Is the button now checked?
             boolean checked = ((RadioButton) view).isChecked();

             // Check which radio button was clicked
             switch(view.getId()) {
             	 case R.id.radioButton1:
                     if (checked)
                     	flag=1;
                     break;
                     
                 case R.id.radioButton2:
                     if (checked)
                     	flag=2;
                     break;
                     
                 case R.id.radioButton3:
                	 if(checked)
                	 	flag=3;
                	break;
             }
         }
         
         public void validate()
         {
        	 String from =  et.getText().toString();
    		 String to = et1.getText().toString();

        	 mydb=new Dbhelper(this);
        	 if(flag==1)
        	 {
        		 Intent i = new Intent(getApplicationContext(),All_view.class);
        	      i.putExtra("fromx", from);
        	      i.putExtra("toy", to);
        	      i.putExtra("f", flag);
        	      startActivity(i);
        	          	
        		 
        	 }
        	 else if(flag==2)
        	 {
        		 Intent i = new Intent(getApplicationContext(),All_view.class);
        		 i.putExtra("fromx", from);
        		 i.putExtra("toy", to);
        		 i.putExtra("f", flag);
        		 startActivity(i);
        	
        	 }
        	 else if(flag==3)
        	 {
        		 Intent i = new Intent(getApplicationContext(),All_view.class);
        		 i.putExtra("fromx", from);
        		 i.putExtra("toy", to);
        		 i.putExtra("f", flag);
        		 startActivity(i);
        	 }
         }
         

}
