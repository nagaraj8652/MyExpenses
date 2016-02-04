package com.example.myexpenses;




import android.os.Bundle;

import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;





@SuppressWarnings("deprecation")
public class Ex_info extends TabActivity  {
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.ex_info);
                
     
        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabSpec tab1 = tabHost.newTabSpec("View Expenses");
        TabSpec tab2 = tabHost.newTabSpec("View Income");
        TabSpec tab3 = tabHost.newTabSpec("View All");
      
        TabSpec tab4 = tabHost.newTabSpec("Select");
       // Set the Tab name and Activity
       // that will be opened when particular Tab will be selected
        tab1.setIndicator("View Expenses");
        tab1.setContent(new Intent(this,Ex_view.class));
        
        tab2.setIndicator("View Incomes");
        tab2.setContent(new Intent(this,In_view.class));
        
        tab3.setIndicator("View All");
        tab3.setContent(new Intent(this,Expand.class));

        tab4.setIndicator("Select");
        tab4.setContent(new Intent(this,Selectoption.class));
        
        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
                    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
