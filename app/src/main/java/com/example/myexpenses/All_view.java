package com.example.myexpenses;


import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class All_view extends ListActivity {

	// declare class variables
	private Runnable viewParts;
	private ItemAdapter m_adapter;
	Dbhelper mydb =new Dbhelper(this);
	ArrayList<Item> abc = new ArrayList<Item>();
	Item it=new Item();
	String froma = "17/7/2014";
	String toa = "17/8/2014";
	String f,t;
	int flag;
	
	/*public All_view(String a,String b, int c){
		 f = a; 
		 t = b;
		 flag = c;
		
	}*/
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        if (null != getIntent().getExtras()) {
        	  Bundle b=getIntent().getExtras();
        	    f = b.getString("fromx");
        	    t = b.getString("toy");
        	    flag=b.getInt("f");
        	    
        	    Toast.makeText(getApplicationContext(), "from  :"+f+"to :"+t,Toast.LENGTH_LONG).show();
        	} else {
        	   // default values for from to and flag.
        		 f = "1/8/2014";
        		 t = "17/8/2014";
        	}

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
			if(flag==1)
			{
				abc = mydb.getsdata(f,t);
			}
			else 
				if(flag==2)
				{
					abc = mydb.i_getsdata(f,t);
				}
				else if(flag==3)
				{
					abc = mydb.all_getsdata(f,t);
				}

			addptr();
			
		}
	};
	   
	public void addptr()
	{
		
		m_adapter = new ItemAdapter(All_view.this, R.layout.list_item, abc);
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
        Intent intent = new Intent(getApplicationContext(),In_add.class);
        intent.putExtras(dataBundle);
        startActivity(intent);        
	}
}
