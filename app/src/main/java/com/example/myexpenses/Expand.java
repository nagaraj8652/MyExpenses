package com.example.myexpenses;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

public class Expand extends ExpandableListActivity{

	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();
	Dbhelper mydb;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// this is not really  necessary as ExpandableListActivity contains an ExpandableList
		//setContentView(R.layout.main);

		ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		setGroupParents();
		setChildData();

		MyExpandableListAdapter adapter = new MyExpandableListAdapter(parentItems, childItems);

		adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
		expandableList.setAdapter(adapter);
		expandableList.setOnChildClickListener(this);
	}

	public void setGroupParents() {
		parentItems.add("Today");
		parentItems.add("This Week");
		parentItems.add("This Month");
		parentItems.add("This Year");

	}

	public void setChildData() {
		
		mydb = new Dbhelper(this);
       float ex = Float.parseFloat(mydb.e_total());
       float in = Float.parseFloat(mydb.ic_total());
       float t_in = Float.parseFloat(mydb.in_today());
       float t_ex = Float.parseFloat(mydb.ex_today());
       float w_in = Float.parseFloat(mydb.in_week());
       float w_ex = Float.parseFloat(mydb.ex_week());
       float m_in = Float.parseFloat(mydb.in_monthly());
       float m_ex = Float.parseFloat(mydb.ex_monthly());
       
		// Today
		ArrayList<String> child = new ArrayList<String>();
		child.add("Expenses "+"\t\t\t\t\t\t"+"Rs."+t_ex);
		child.add("Income "+"\t\t\t\t\t\t\t"+"Rs."+t_in);
		validate(t_in,t_ex,child);
		childItems.add(child);
	 
		 
		// This Week
		child = new ArrayList<String>();
		child.add("Expenses "+"\t\t\t\t\t\t"+"Rs."+w_ex);
		child.add("Income "+"\t\t\t\t\t\t\t"+"Rs."+w_in);
		validate(w_in,w_ex,child);
		childItems.add(child);

		// This Month
		child = new ArrayList<String>();
		child.add("Expenses "+"\t\t\t\t\t\t"+"Rs."+m_ex);
		child.add("Income "+"\t\t\t\t\t\t\t"+"Rs."+m_in);
		validate(m_in,m_ex,child);
		childItems.add(child);

		// This Year
		child = new ArrayList<String>();
		child.add("Expenses "+"\t\t\t\t\t\t"+"Rs."+ex);
		child.add("Income "+"\t\t\t\t\t\t\t"+"Rs."+in);
		validate(in,ex,child);
		childItems.add(child);

	}
	public void validate(float income,float expenses,ArrayList<String> child)
	{
		float in=income,ex=expenses;
		float amount = in - ex;
		String i_income = String.format("%.2f",amount);
		
		if(in-ex<0)
		{
			child.add("Total Loss"+"\t\t\t\t\t\t"+"Rs."+(i_income));
		}
		else if(in-ex>0)
		{
			child.add("Total Profit"+"\t\t\t\t\t"+"Rs."+(i_income));
		}
		else if(in-ex==0)
		{
			child.add("No Profit No Loss"+"\t\t"+"Rs."+(i_income));
		}
	}

}