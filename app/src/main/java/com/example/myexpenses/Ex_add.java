package com.example.myexpenses;

import java.util.Calendar;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Ex_add extends Activity {
	
	 private Calendar cal;
	 private int day;
	 private int month;
	 private int year;
	 private EditText et;
	 private ImageButton ib;
	 private Button b1;
	 
	 private EditText e_type;
	 private EditText e_amt;
	 private EditText e_desc;
	 private Spinner e_method;
	 public  ArrayAdapter<CharSequence> adapter;
	 private Bundle extras;
	 
	 int id_To_Update = 0;
	 
	 Dbhelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_add);
        
        //content insert
        b1 =(Button)findViewById(R.id.button1);
        e_type=(EditText)findViewById(R.id.EditText01);
        e_amt=(EditText)findViewById(R.id.Amount);
        e_desc=(EditText)findViewById(R.id.editText2);
        
        //date picker 
        ib = (ImageButton) findViewById(R.id.imageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.editText);
        
        
        mydb = new Dbhelper(this);
        
        et.setFocusable(false); 
        et.setClickable(false);
        
        //spinner
        
        e_method = (Spinner) findViewById(R.id.spinner1);
     // Create an ArrayAdapter using the string array and a default spinner layout
       adapter = ArrayAdapter.createFromResource(this,
             R.array.p_array, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     e_method.setAdapter(adapter);
        
     	
     	//update
       	extras = getIntent().getExtras(); 
       	if(extras != null)
       	{
       		e_update();
       	}
       	else
        
        ib.setOnClickListener(new View.OnClickListener() {
			
     			@SuppressWarnings("deprecation")
				@Override
     			public void onClick(View arg0) {
     				// TODO Auto-generated method stub
     				 showDialog(0);
     			}
     		});
         
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String e_tp=e_type.getText().toString();
				String amt=e_amt.getText().toString();
				String dt = et.getText().toString();
				
				if(e_tp.equals("")){
					Toast.makeText(getApplicationContext(), "Please Enter Expenses Type",Toast.LENGTH_LONG).show();
				}
				else if(amt.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Amount",Toast.LENGTH_LONG).show();
				}
				else if(dt.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please Enter Date",Toast.LENGTH_LONG).show();
				}
				else{
					e_insert();
				}
			
			}
		});
    
    
    
    }
   
 
    
    //insert and update buttons
    public void e_insert()
    {
    	
    	
    	Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
           int Value = extras.getInt("id");
           if(Value>0){
              if(mydb.updateexpenses(id_To_Update,e_type.getText().toString(), et.getText().toString(), e_amt.getText().toString(), e_method.getSelectedItem().toString(), e_desc.getText().toString())){
                 Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
                 Intent intent = new Intent(getApplicationContext(),Ex_info.class);
                 startActivity(intent);
                 finish();
                 
               }		
              else{
                 Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();	
              }
  		 }
        }
         else{
        	   if(mydb.insertexps(e_type.getText().toString(), et.getText().toString(), e_amt.getText().toString(), e_method.getSelectedItem().toString(), e_desc.getText().toString())){
                   Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
           	}		
              else{
                 Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
              }
              Intent intent = new Intent(getApplicationContext(),MainActivity.class);
              startActivity(intent);
              finish();
             
              }
        
     }
     
    
    //update
    public void e_update()
    {
    	 int Value = extras.getInt("id");
         if(Value>0){
            //means this is the view part not the add contact part.
            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();
            String e_typ = rs.getString(rs.getColumnIndex(Dbhelper.EXPENSES_COLUMN_TYPE));
            String e_dat = rs.getString(rs.getColumnIndex(Dbhelper.EXPENSES_COLUMN_DATE));
            String[] e_d = e_dat.split("-");
            String date_e = e_d[2]+"/"+e_d[1]+"/"+e_d[0];
            String e_am = rs.getString(rs.getColumnIndex(Dbhelper.EXPENSES_COLUMN_AMOUNT));
            String e_met = rs.getString(rs.getColumnIndex(Dbhelper.EXPENSES_COLUMN_METHOD));
            String e_de = rs.getString(rs.getColumnIndex(Dbhelper.EXPENSES_COLUMN_DESC));
            if (!rs.isClosed()) 
            {
               rs.close();
            }
            
            b1.setVisibility(View.INVISIBLE);
            ib.setVisibility(View.INVISIBLE);
            
            e_type.setText((CharSequence)e_typ);
            e_type.setFocusable(false);
            e_type.setClickable(false);

            et.setText((CharSequence)date_e);
            et.setFocusable(false); 
            et.setClickable(false);

            e_amt.setText((CharSequence)e_am);
            e_amt.setFocusable(false);
            e_amt.setClickable(false);
           
            e_method.setSelection(adapter.getPosition(e_met));
            e_method.setFocusable(false); 
            e_method.setClickable(false);

            e_desc.setText((CharSequence)e_de);
            e_desc.setFocusable(false);
            e_desc.setClickable(false);
            
           
           }
    }

    public final Dialog onCreateDialog(int id) {
    	  return new DatePickerDialog(this, datePickerListener, year, month, day);
    	 }

    	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
    	  public void onDateSet(DatePicker view, int selectedYear,
    	    int selectedMonth, int selectedDay) {
    	   et.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear );
    	  }
    	 };
    	 
    public boolean onCreateOptionsMenu(Menu menu) {
    	        // Inflate the menu; this adds items to the action bar if it is present.
    	        Bundle extras = getIntent().getExtras(); 
    	        if(extras !=null)
    	        {
    	           int Value = extras.getInt("id");
    	           if(Value>0){
    	              getMenuInflater().inflate(R.menu.sub_menu, menu);
    	           }
    	           else{
    	              getMenuInflater().inflate(R.menu.main, menu);
    	           }
    	        }
    	        return true;
    	     }
    
    public boolean onOptionsItemSelected(MenuItem item) 
    { 
       super.onOptionsItemSelected(item); 
       switch(item.getItemId()) 
    { 
       case R.id.Edit_Expenses: 
     
       b1.setVisibility(View.VISIBLE);
       ib.setVisibility(View.VISIBLE);
       
       e_type.setEnabled(true);
       e_type.setFocusableInTouchMode(true);
       e_type.setClickable(true);

       et.setEnabled(true);
       et.setFocusableInTouchMode(false);
       et.setClickable(false);

       e_amt.setEnabled(true);
       e_amt.setFocusableInTouchMode(true);
       e_amt.setClickable(true);

       e_method.setEnabled(true);
       e_method.setFocusableInTouchMode(true);
       e_method.setClickable(true);

       e_desc.setEnabled(true);
       e_desc.setFocusableInTouchMode(true);
       e_desc.setClickable(true);
       
       ib.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 showDialog(0);
			}
		});

       return true; 
       case R.id.Delete_Expenses:
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setMessage(R.string.deleteconf)
      
       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
             mydb.deleteexpenses(id_To_Update);
             Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();  
             Intent intent = new Intent(getApplicationContext(),Ex_view.class);
             startActivity(intent);
             finish();
          }
       })
      
       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
             // User cancelled the dialog
          }
       });
       
       AlertDialog d = builder.create();
       d.setTitle("Are you sure");
       d.show();

       return true;
       default: 
       return super.onOptionsItemSelected(item); 

       } 
    } 


    
   
}
