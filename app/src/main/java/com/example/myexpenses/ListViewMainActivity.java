package com.example.myexpenses;



import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class ListViewMainActivity extends ListActivity {

	// declare class variables
	private Runnable viewParts;
	private ItemAdapter m_adapter;
	Dbhelper mydb =new Dbhelper(this);
	ArrayList<Item> abc = new ArrayList<Item>();
	Item it=new Item();
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        // instantiate our ItemAdapter class
        m_adapter = new ItemAdapter(this, R.layout.list_item, abc);
        setListAdapter(m_adapter);

        // here we are defining our runnable thread.
        viewParts = new Runnable(){
        	public void run(){
        		handler.sendEmptyMessage(0);
        	}
        };

        // here we call the thread we just defined - it is sent to the handler below.
        Thread thread =  new Thread(null, viewParts, "MagentoBackground");
        thread.start();
    }

    private Handler handler = new Handler()
	 {
		public void handleMessage(Message msg)
		{
			// create some objects
			// here is where you could also request data from a server
			// and then create objects from that data.			
			abc = mydb.getAllexps();
			addptr();
			
		}
	};
	   
	public void addptr()
	{
		m_adapter = new ItemAdapter(ListViewMainActivity.this, R.layout.list_item, abc);

		// display the list.
        setListAdapter(m_adapter);
	}
	protected void onListItemClick (ListView arg0, View arg1, int arg2,
            long arg3)
	{
		Item i = abc.get(arg2);		
		Toast.makeText(getApplicationContext(), "Please Enter Expenses Type :" +i.getId(),Toast.LENGTH_LONG).show();
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", i.getId());
        Intent intent = new Intent(getApplicationContext(),Ex_add.class);
        intent.putExtras(dataBundle);
        startActivity(intent);        
	}

    public boolean onOptionsItemSelected(MenuItem item) 
    { 
       super.onOptionsItemSelected(item); 
       switch(item.getItemId()) 
    { 
       case R.id.Today: 
    	   
    	   abc = mydb.gettoday(); 
    	   addptr();
    	   
           return true; 
           
       case R.id.Current_Week:
    	   abc = mydb.getweek();
    	   addptr();
    	   
    	   return true;
    	   
       case R.id.Current_Month:
    	   abc = mydb.getmonth();
           addptr();

    	   return true;
    	   
       case R.id.Current_Year:
    	   
    	   abc = mydb.getcyears();
           addptr();

    	   return true;
       case R.id.all:
    	   
    	   abc = mydb.getAllexps();
           addptr();

    	   return true;
       default: 
       return super.onOptionsItemSelected(item); 

       } 
    }    
}
