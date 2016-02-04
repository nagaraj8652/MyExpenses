package com.example.myexpenses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;


public class Dbhelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "Myexpenses";
	public static final String EXPENSES_TABLE_NAME = "expenses";
	public static final String EXPENSES_COLUMN_ID = "id";
	public static final String EXPENSES_COLUMN_TYPE= "e_type";
	public static final String EXPENSES_COLUMN_DATE = "e_date";
	public static final String EXPENSES_COLUMN_AMOUNT = "e_amt";
	public static final String EXPENSES_COLUMN_METHOD = "e_method";
	public static final String EXPENSES_COLUMN_DESC = "desc";
	//income
	public static final String INCOME_TABLE_NAME = "income";
	public static final String INCOME_COLUMN_ID = "id";
	public static final String INCOME_COLUMN_TYPE= "i_type";
	public static final String INCOME_COLUMN_DATE = "i_date";
	public static final String INCOME_COLUMN_AMOUNT = "i_amt";
	public static final String INCOME_COLUMN_METHOD = "i_method";
	public static final String INCOME_COLUMN_DESC = "desc";

	public Dbhelper(Context context)
	   {
	      super(context, DATABASE_NAME , null, 1);
	      
	   }

	   @Override
	   public void onCreate(SQLiteDatabase db) {
	      // TODO Auto-generated method stub
	      db.execSQL(
	      "create table expenses " +
	      "(id integer primary key autoincrement,e_type text not null,e_date date not null,e_amt text not null, e_method text not null,desc text)"
	      );
	      db.execSQL(
	    	      "create table income " +
	    	      "(id integer primary key autoincrement,i_type text not null,i_date date not null,i_amt text not null, i_method text not null,desc text)"
	    	      );
	   }

	   @Override
	   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	      // TODO Auto-generated method stub
	      db.execSQL("DROP TABLE IF EXISTS expenses");
	      db.execSQL("DROP TABLE IF EXISTS income");
	      onCreate(db);
	   }
	
	   public boolean insertexps  (String e_type, String e_date, String e_amt, String e_method,String desc)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      
	      String[] date=e_date.split("/");
	      String p1,p2,p3;
	      p1=date[0];
	      p2=date[1];
	      p3=date[2];
	      
	      contentValues.put("e_type", e_type);
	      contentValues.put("e_date", p3+"-"+p2+"-"+p1);
	      contentValues.put("e_amt", e_amt);	
	      contentValues.put("e_method", e_method);
	      contentValues.put("desc", desc);

	      db.insert("expenses", null, contentValues);
	      return true;
	   }
	   
	   public Cursor getData(int id){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from expenses where id="+id+"", null );
	      return res;
   
	   }
	   
	   public Cursor getall()
	   {
		   	  SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from expenses", null );
		      return res;
	   }
	 
	   public ArrayList<Item> getsdata(String fromj ,String toj)
	   {
		   SQLiteDatabase db =this.getReadableDatabase();
		   ArrayList<Item> list =new ArrayList<Item>();
		   
		   String[] fromb = fromj.split("/");
		   String[] tob = toj.split("/");
		   String start = fromb[2]+"-"+fromb[1]+"-"+fromb[0];
		   String end = tob[2]+"-"+tob[1]+"-"+tob[0];
		   
		   Cursor res = db.rawQuery( "select * from expenses where e_date between '"+start+"'and '"+end+"'", null );
		   float amount;
		   String e_date;
		   String[] dt;
		   res.moveToFirst();
		   while(res.isAfterLast()==false)
		   {
			   e_date=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
			   dt=e_date.split("-");
			   amount = Float.parseFloat(res.getString(res.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
			   String f_amount = String.format("%.2f",amount );
			   
			   list.add(new Item(res.getString(res.getColumnIndex(EXPENSES_COLUMN_TYPE)),
	   					dt[2]+"/"+dt[1]+"/"+dt[0],f_amount,
	   					res.getInt(res.getColumnIndex(EXPENSES_COLUMN_ID))));
			   res.moveToNext();
		   }
		   
		   
		   return list;
	   
	   }
	   public String e_total()
	   {
		  	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select e_amt from expenses ",null);
		   	int total=0,t=0;
		   	
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(EXPENSES_COLUMN_AMOUNT));		   		   		
		   		total+=t;		   		
		   		c.moveToNext();
		   	}
		   
		   	String amount=Integer.toString(total);
		    return amount;
	  
	   }
	   
	   public String C_total()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from expenses ",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int yea = calendar.get(Calendar.YEAR);
		   	String year=Integer.toString(yea);
		   	int total=0,t=0;
		   	String dt="";
		   	
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(EXPENSES_COLUMN_AMOUNT));
		   		dt=c.getString(c.getColumnIndex(EXPENSES_COLUMN_DATE));
		   		if(dt.startsWith(year))
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   public int numberOfRows(){
		      SQLiteDatabase db = this.getReadableDatabase();
		      int numRows = (int) DatabaseUtils.queryNumEntries(db, EXPENSES_COLUMN_TYPE);
		      return numRows;
		   }
	   
	   public ArrayList<Item> getAllexps()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from expenses", null );
		      res.moveToFirst();
		      String[] dt;
		      String e_date;
		      float amount;
		      
		      while(res.isAfterLast() == false){

		    	  e_date=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
				   dt=e_date.split("-");
				   amount = Float.parseFloat(res.getString(res.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
				   String f_amount = String.format("%.2f",amount );
				   
				   list.add(new Item(res.getString(res.getColumnIndex(EXPENSES_COLUMN_TYPE)),
		   					dt[2]+"/"+dt[1]+"/"+dt[0],f_amount,
		   					res.getInt(res.getColumnIndex(EXPENSES_COLUMN_ID))));
				   
		      	res.moveToNext();
		      }
		    
		    return list;
		  }
	   
	   //view today
	   public ArrayList<Item> gettoday()
	   {
		   
		   ArrayList<Item> mp = new ArrayList<Item>();
		   SQLiteDatabase db= this.getReadableDatabase();
		   Cursor res =db.rawQuery("select * from expenses", null);
		   Calendar calendar = Calendar.getInstance();
		   	
		   	int dat = calendar.get(Calendar.DATE);
		   	int mnt = calendar.get(Calendar.MONTH);
		   	int yea = calendar.get(Calendar.YEAR);
		   int month,year,day;
		    String dt,p2,p1,p3;
		      String[] nyear;
		  	String[] dta;
	  		String e_date;
	  		float amount;
		   res.moveToFirst();
		   while(res.isAfterLast()== false)
		   {
			   dt=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
			   nyear = dt.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			 
		  		day=Integer.parseInt(p1);
		  		month=Integer.parseInt(p2)-1;
		  		year=Integer.parseInt(p3);		  				  	
		  				
		  		if(day==dat&&month==mnt&&year==yea)
		  		{
			   	
		  			e_date=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
					   dta=e_date.split("-");
					   amount = Float.parseFloat(res.getString(res.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
					   String f_amount = String.format("%.2f",amount );
					   
					   mp.add(new Item(res.getString(res.getColumnIndex(EXPENSES_COLUMN_TYPE)),
			   					dta[2]+"/"+dta[1]+"/"+dta[0],f_amount,
			   					res.getInt(res.getColumnIndex(EXPENSES_COLUMN_ID))));
			   	res.moveToNext();
		  		}
		  		res.moveToNext();
		   }
		   
		 return mp;
		}
	   
	   //view week
	   public ArrayList<Item> getweek()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from expenses", null );
		      Calendar calendar = Calendar.getInstance();
			   	int wk = calendar.get(Calendar.WEEK_OF_YEAR);
			   	int yr = calendar.get(Calendar.YEAR);
			   	
			   	int week,yea;
			   	
		      res.moveToFirst();
		      String amt,typ,rs,dt,tp,p2,p1,p3;
		      String[] nyear,dta;
		      String e_date;
		      float amount;
		      
		   while(res.isAfterLast() == false){
		    	  
		      rs=res.getString(res.getColumnIndex(EXPENSES_COLUMN_AMOUNT));
		      tp=res.getString(res.getColumnIndex(EXPENSES_COLUMN_TYPE));
		      dt=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
		     
		      nyear = dt.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			 
			 
		   		yea =Integer.parseInt(p3);
		   		week= chk(p1,p2,p3);
		    	
		   		if(week==wk && yr==yea)
		   		{
		   			e_date=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
					   dta=e_date.split("-");
					   amount = Float.parseFloat(res.getString(res.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
					   String f_amount = String.format("%.2f",amount );
					   
					   list.add(new Item(res.getString(res.getColumnIndex(EXPENSES_COLUMN_TYPE)),
			   					dta[2]+"/"+dta[1]+"/"+dta[0],f_amount,
			   					res.getInt(res.getColumnIndex(EXPENSES_COLUMN_ID))));
		  			res.moveToNext();
		   		}
		   		
		      res.moveToNext();
		      }
		    
		    return list;
		  }
	   
	   //view month
	   public ArrayList<Item> getmonth()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from expenses", null );
		  	Calendar calendar = Calendar.getInstance();
		   	int mt = calendar.get(Calendar.MONTH);
		   	int yr = calendar.get(Calendar.YEAR);
		 
		   	int month,yea;
			   	
		      res.moveToFirst();
		      String dt,p2,p3,e_date;
		      String[] nyear,dta;
		      float amount;
		      
		      while(res.isAfterLast() == false){

		      dt=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
		     
		  	nyear = dt.split("-");
		    p2=nyear[1];
		    p3=nyear[0];
		    
	   		yea =Integer.parseInt(p3);
	    	month = Integer.parseInt(p2)-1;
	   			if(month==mt && yr==yea)
	   			{
	   				  e_date=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
					   dta=e_date.split("-");
					   amount = Float.parseFloat(res.getString(res.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
					   String f_amount = String.format("%.2f",amount );
					   
					   list.add(new Item(res.getString(res.getColumnIndex(EXPENSES_COLUMN_TYPE)),
			   					dta[2]+"/"+dta[1]+"/"+dta[0],f_amount,
			   					res.getInt(res.getColumnIndex(EXPENSES_COLUMN_ID))));
		   		}
		   		
		      res.moveToNext();
		      }
		    
		    return list;
		}
	  
	   //view year
	   public ArrayList<Item> getcyears()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from expenses", null );
		      
		      Calendar calendar = Calendar.getInstance();
			   	int year = calendar.get(Calendar.YEAR);
			   	
		      res.moveToFirst();
		      String amt,typ,rs,dt,tp,p3,e_date;
		      int yea;
		      float amount;
		      String[] nyear,dta;
		      while(res.isAfterLast() == false){

		      dt=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
		      	
		      
		      	nyear = dt.split("-");
			    
			    p3=nyear[0];
			    
		   		yea =Integer.parseInt(p3);
		   		
		    	if(yea==year)
		   		{
		    		e_date=res.getString(res.getColumnIndex(EXPENSES_COLUMN_DATE));
					   dta=e_date.split("-");
					   amount = Float.parseFloat(res.getString(res.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
					   String f_amount = String.format("%.2f",amount );
					   
					   list.add(new Item(res.getString(res.getColumnIndex(EXPENSES_COLUMN_TYPE)),
			   					dta[2]+"/"+dta[1]+"/"+dta[0],f_amount,
			   					res.getInt(res.getColumnIndex(EXPENSES_COLUMN_ID))));
		   			res.moveToNext();
		   		}
		    	else
		    		res.moveToNext();
		      }
		    
		    return list;
		  }
	   
	   
	   public boolean updateexpenses (Integer id, String e_type, String e_date, String e_amt, String e_method,String desc)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	     
	      String[] date=e_date.split("/");
	      String p1 = date[2]+"-"+date[1]+"-"+date[0];
	      
	      
	      contentValues.put("e_type", e_type);
	      contentValues.put("e_date", p1);
	      contentValues.put("e_amt", e_amt);	
	      contentValues.put("e_method", e_method);
	      contentValues.put("desc", desc);
	      db.update("expenses", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
	      return true;
	   }

	   public Integer deleteexpenses (Integer id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      return db.delete("expenses","id = ? ", 
	      new String[] { Integer.toString(id) });
	   }
	   public String ex_today()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from expenses ",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int dat = calendar.get(Calendar.DATE);
		   	int mnt = calendar.get(Calendar.MONTH);
		   	int yea = calendar.get(Calendar.YEAR);
		   
		   	
		   	//String year=dat+"/"+y;
		   	int total=0,t=0,day,month,year;;
		   	String dt="" , p1,p2,p3;
		   	String[] nyear;
		   	
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(EXPENSES_COLUMN_AMOUNT));
		   		dt=c.getString(c.getColumnIndex(EXPENSES_COLUMN_DATE));
		   		nyear = dt.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			 
			    day=Integer.parseInt(p1);
			   	month=Integer.parseInt(p2)-1;
			   	year=Integer.parseInt(p3);
		   		if(day==dat&&month==mnt&&year==yea)
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   
	   //income

	   public boolean insertincome  (String i_type, String i_date, String i_amt, String i_method,String desc)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      String[] date=i_date.split("/");
	      String p1,p2,p3;
	      p1=date[0];
	      p2=date[1];
	      p3=date[2];
	      
	      contentValues.put("i_type", i_type);
	      contentValues.put("i_date", p3+"-"+p2+"-"+p1);
	      contentValues.put("i_amt", i_amt);	
	      contentValues.put("i_method", i_method);
	      contentValues.put("desc", desc);

	      db.insert("income", null, contentValues);
	      return true;
	   }
	   
	   public Cursor i_getData(int id){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from income where id="+id+"", null );
	      return res;
	   }
	   public String i_total()
	   {
		  	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select e_amt from income ",null);
		   	int total=0,t=0;
		   	
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(INCOME_COLUMN_AMOUNT));		   		   		
		   		total+=t;		   		
		   		c.moveToNext();
		   	}
		   
		   	String amount=Integer.toString(total);
		    return amount;
	  
	   }
	   
	   public String ic_total()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from income ",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int yea = calendar.get(Calendar.YEAR);
		   	String year=Integer.toString(yea);
		   	int total=0,t=0;
		   	String dt="";
		   	
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(INCOME_COLUMN_AMOUNT));
		   		dt=c.getString(c.getColumnIndex(INCOME_COLUMN_DATE));
		   		if(dt.startsWith(year))
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   //today income
	   public String in_today()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from income ",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int dat = calendar.get(Calendar.DATE);
		   	int mnt = calendar.get(Calendar.MONTH);
		   	int yea = calendar.get(Calendar.YEAR);
		
		   	//String year=dat+"/"+y;
		   	int total=0,t=0,mn,day,month,year;
		   	String dt="",p1,p2,p3;
			String[] nyear;
		   	
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(INCOME_COLUMN_AMOUNT));
		   		dt=c.getString(c.getColumnIndex(INCOME_COLUMN_DATE));
		   		nyear = dt.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			 
			    day=Integer.parseInt(p1);
			   	month=Integer.parseInt(p2)-1;
			   	year=Integer.parseInt(p3);
		   		if(day==dat&&month==mnt&&year==yea)
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   //view today
	   public ArrayList<Item> gettodayincome()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from income", null );
		      Calendar calendar = Calendar.getInstance();
			   	int dat = calendar.get(Calendar.DATE);
			   	int mnt = calendar.get(Calendar.MONTH);
			   	int yea = calendar.get(Calendar.YEAR);
			   int month,year,day;
			   	
		      res.moveToFirst();
		      String dt,p2,p1,p3;
		      String[] nyear;
		      while(res.isAfterLast() == false){
		     
		      dt=res.getString(res.getColumnIndex(INCOME_COLUMN_DATE));
		     
		      nyear = dt.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			 
		  		day=Integer.parseInt(p1);
		  		month=Integer.parseInt(p2)-1;
		  		year=Integer.parseInt(p3);
		  		if(day==dat&&month==mnt&&year==yea)
		  		{
		  			list.add(new Item(res.getString(res.getColumnIndex(INCOME_COLUMN_TYPE)),
		   					res.getString(res.getColumnIndex(INCOME_COLUMN_DATE)),
		   					res.getString(res.getColumnIndex(INCOME_COLUMN_AMOUNT)),
		   					res.getInt(res.getColumnIndex(INCOME_COLUMN_ID))));
		  			res.moveToNext();
		   		}
		   		
		      res.moveToNext();
		      }
		    
		    return list;
		  }
	   
	   //view week
	   public ArrayList<Item> getweekincome()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from income", null );
		      Calendar calendar = Calendar.getInstance();
			   	int wk = calendar.get(Calendar.WEEK_OF_YEAR);
			   	int yr = calendar.get(Calendar.YEAR);
			   	
			   	int week,yea;
			   	
		      res.moveToFirst();
		      String dt,p2,p1,p3;
		      String[] nyear;
		      while(res.isAfterLast() == false){
		 
		      dt=res.getString(res.getColumnIndex(INCOME_COLUMN_DATE));
		     
		      	nyear = dt.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			 
		   		yea =Integer.parseInt(p3);
		   		week= chk(p1,p2,p3);
		    	
		   		if(week==wk && yr==yea)
		   		{
		   			list.add(new Item(res.getString(res.getColumnIndex(INCOME_COLUMN_TYPE)),
		   					res.getString(res.getColumnIndex(INCOME_COLUMN_DATE)),
		   					res.getString(res.getColumnIndex(INCOME_COLUMN_AMOUNT)),
		   					res.getInt(res.getColumnIndex(INCOME_COLUMN_ID))));
		  			res.moveToNext();
		   		}
		   		
		      res.moveToNext();
		      }
		    
		    return list;
		  }
	   
	   //view month
	   public ArrayList<Item> getmonthincome()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from income", null );
		  	Calendar calendar = Calendar.getInstance();
		   	int mt = calendar.get(Calendar.MONTH);
		   	int yr = calendar.get(Calendar.YEAR);
		 
		   	int month,yea;
			   	
		      res.moveToFirst();
		      String dt,p2,p3;
		      String[] nyear;
		      while(res.isAfterLast() == false){

		      dt=res.getString(res.getColumnIndex(INCOME_COLUMN_DATE));
		     
		  	nyear = dt.split("-");
		    p2=nyear[1];
		    p3=nyear[0];
		    
	   		yea =Integer.parseInt(p3);
	    	month = Integer.parseInt(p2)-1;
	   		if(month==mt && yr==yea)
	   		{
	   			list.add(new Item(res.getString(res.getColumnIndex(INCOME_COLUMN_TYPE)),
	   					res.getString(res.getColumnIndex(INCOME_COLUMN_DATE)),
	   					res.getString(res.getColumnIndex(INCOME_COLUMN_AMOUNT)),
	   					res.getInt(res.getColumnIndex(INCOME_COLUMN_ID))));
		  			res.moveToNext();
		   		}
		   		
		      res.moveToNext();
		      }
		    
		    return list;
		  }
	  
	   //view year
	   public ArrayList<Item> getcyearsincome()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from income", null );
		      
		      Calendar calendar = Calendar.getInstance();
			   	int year = calendar.get(Calendar.YEAR);
			   	
		      res.moveToFirst();
		      String amt,typ,rs,dt,tp,p3;
		      int yea;
		      String[] nyear;
		      while(res.isAfterLast() == false){

		      dt=res.getString(res.getColumnIndex(INCOME_COLUMN_DATE));
		      	
		      
		      	nyear = dt.split("-");
			    
			    p3=nyear[0];
			    
		   		yea =Integer.parseInt(p3);
		   		
		    	if(yea==year)
		   		{
		    		list.add(new Item(res.getString(res.getColumnIndex(INCOME_COLUMN_TYPE)),
		   					res.getString(res.getColumnIndex(INCOME_COLUMN_DATE)),
		   					res.getString(res.getColumnIndex(INCOME_COLUMN_AMOUNT)),
		   					res.getInt(res.getColumnIndex(INCOME_COLUMN_ID))));
		   			res.moveToNext();
		   		}
		    	else
		    		res.moveToNext();
		      }
		    
		    return list;
		  }
	   
	   
	   
	   
	   //weekly Expenses
	   public String ex_week()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from expenses ",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int wk = calendar.get(Calendar.WEEK_OF_YEAR);
		   	int yr = calendar.get(Calendar.YEAR);
		 
		   	int total=0,t=0,week,yea;

		    String year = null,p1,p2,p3;
		    String[] nyear;
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(EXPENSES_COLUMN_AMOUNT));
		   		year=c.getString(c.getColumnIndex(EXPENSES_COLUMN_DATE));
		   	
		   		nyear = year.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			 
		   		yea =Integer.parseInt(p3);
		   		week= chk(p1,p2,p3);
		    	
		   		if(week==wk && yr==yea)
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   //weekly income
	   public String in_week()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from income ",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int wk = calendar.get(Calendar.WEEK_OF_YEAR);
		   	int yr = calendar.get(Calendar.YEAR);
		 
		   	int total=0,t=0,week,yea;

		    String year = null,p1,p2,p3;
		    String[] nyear;
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(INCOME_COLUMN_AMOUNT));
		   		year=c.getString(c.getColumnIndex(INCOME_COLUMN_DATE));
		   	
		   		nyear = year.split("-");
			    p1=nyear[2];
			    p2=nyear[1];
			    p3=nyear[0];
			    
			
		   		yea =Integer.parseInt(p3);
		   		week= chk(p1,p2,p3);
		    	
		   		if(week==wk && yr==yea)
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   public int chk(String a,String b,String c)
	   {
		   	
		  		int x=Integer.parseInt(a);
			   	int y=Integer.parseInt(b);
			   	int z=Integer.parseInt(c);
			  
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");	
			Calendar calendar = new GregorianCalendar(z,y-1,x);
			int week = calendar.get(Calendar.WEEK_OF_YEAR);
			
			return week;
		  		   	
	   	/* String dateFormat="7/28/2014";
	   	 * 	String datef=new String(dat);
	   		String dt=new SimpleDateFormat("w").format(new java.util.Date(datef));
	   		int week=Integer.parseInt(dt);
	   		return week;*/			
		
	   }
	   //income monthly
	   public String in_monthly()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from income",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int mt = calendar.get(Calendar.MONTH);
		   	int yr = calendar.get(Calendar.YEAR);
		 
		   	int total=0,t=0,month,yea;
		    String year = null,p2,p3;
		    String[] nyear;
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(INCOME_COLUMN_AMOUNT));
		   		year=c.getString(c.getColumnIndex(INCOME_COLUMN_DATE));
		   	
		   		nyear = year.split("-");
			    p2=nyear[1];
			    p3=nyear[0];
			    
		   		yea =Integer.parseInt(p3);
		    	month = Integer.parseInt(p2)-1;
		   		if(month==mt && yr==yea)
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   //expenses monthly
	   public String ex_monthly()
	   {
		  
		   	SQLiteDatabase db = this.getReadableDatabase();
		   	Cursor c= db.rawQuery("select * from expenses ",null);
		   	Calendar calendar = Calendar.getInstance();
		   	int mt = calendar.get(Calendar.MONTH);
		   	int yr = calendar.get(Calendar.YEAR);
		 
		   	int total=0,t=0,month,yea;
		    String year = null,p2,p3;
		    String[] nyear;
		   	c.moveToFirst();
		   	while(c.isAfterLast()== false)
		   	{
		   		
		   		t=c.getInt(c.getColumnIndex(EXPENSES_COLUMN_AMOUNT));
		   		year=c.getString(c.getColumnIndex(EXPENSES_COLUMN_DATE));
		   	
		   		nyear = year.split("-");
			    p2=nyear[1];
			    p3=nyear[0];
			    
		   		yea =Integer.parseInt(p3);
		    	month = Integer.parseInt(p2)-1;
		   		if(month==mt && yr==yea)
		   		{
		   			total+=t;
		   		}
		   		c.moveToNext();
		   	}	 
		   
		   	String amount=Integer.toString(total);		 
		    return amount;
	  
	   }
	   //get the year
	   
	   public ArrayList getyear()
	   {
		   ArrayList list = new ArrayList();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select i_date from income", null );
		      res.moveToFirst();
		      String year,p1,p2;
		      String[] nyear;
		      while(res.isAfterLast() == false)
		      {
		    	  year=res.getString(res.getColumnIndex(INCOME_COLUMN_DATE));
		    	  nyear = year.split("-");
		    	  p1=nyear[1];
		    	  p2=nyear[0];
		    	  if(!list.contains(p1))
		    	  {
		    		  list.add(p1);  
		    	  }
		          res.moveToNext();
		      }
		    
		    return list;
	   }
	   
	   
	   public int i_numberOfRows(){
		      SQLiteDatabase db = this.getReadableDatabase();
		      int numRows = (int) DatabaseUtils.queryNumEntries(db, INCOME_COLUMN_TYPE);
		      return numRows;
		   }
	   
	   public ArrayList<Item> getAllincomes()
	   {
		   ArrayList<Item> list = new ArrayList<Item>();
		   
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from income", null );
		      res.moveToFirst();
		      String e_date;
			float amount;
		      String[] dt;
		      
		      while(res.isAfterLast() == false){

		    	  e_date=res.getString(res.getColumnIndex(INCOME_COLUMN_DATE));
				   dt=e_date.split("-");
				   amount = Float.parseFloat(res.getString(res.getColumnIndex(INCOME_COLUMN_AMOUNT)));
				   String f_amount = String.format("%.2f",amount );
				   
				   list.add(new Item(res.getString(res.getColumnIndex(INCOME_COLUMN_TYPE)),
		   					dt[2]+"/"+dt[1]+"/"+dt[0],f_amount,
		   					res.getInt(res.getColumnIndex(INCOME_COLUMN_ID))));
						      	
		      	res.moveToNext();
		      }
		    
		    return list;
		 }
	   

	   
	   public boolean updateincome (Integer id, String i_type, String i_date, String i_amt, String i_method,String desc)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      String[] date=i_date.split("/");
	      String p1,p2,p3;
	      p1=date[0];
	      p2=date[1];
	      p3=date[2];
	      
	      contentValues.put("i_type", i_type);
	      contentValues.put("i_date", p3+"-"+p2+"-"+p1);
	      contentValues.put("i_amt", i_amt);	
	      contentValues.put("i_method", i_method);
	      contentValues.put("desc", desc);
	      db.update("income", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
	      return true;
	   }
	   public ArrayList<Item> i_getsdata(String fromj ,String toj)
	   {
		   SQLiteDatabase db =this.getReadableDatabase();
		   ArrayList<Item> list =new ArrayList<Item>();
		   
		   String[] fromb = fromj.split("/");
		   String[] tob = toj.split("/");
		   String start = fromb[2]+"-"+fromb[1]+"-"+fromb[0];
		   String end = tob[2]+"-"+tob[1]+"-"+tob[0];
		   
		   Cursor res = db.rawQuery( "select * from income where i_date between '"+start+"'and '"+end+"'", null );
		   String[] dt;
		   String i_date;
		   float amount;
		   res.moveToFirst();
		   while(res.isAfterLast()==false)
		   {
			   i_date= res.getString(res.getColumnIndex(INCOME_COLUMN_DATE));
			   dt=i_date.split("-");
			   
			   amount = Float.parseFloat(res.getString(res.getColumnIndex(INCOME_COLUMN_AMOUNT)));
			   String f_amount = String.format("%.2f",amount );
			   
			   list.add(new Item(res.getString(res.getColumnIndex(INCOME_COLUMN_TYPE)),
					   dt[2]+"/"+dt[1]+"/"+dt[0],f_amount,
	   				res.getInt(res.getColumnIndex(INCOME_COLUMN_ID))));
			   res.moveToNext();
		   }
		   		   
		   return list;  
	   }

	   public ArrayList<Item> all_getsdata(String fromj ,String toj)
	   {
		   SQLiteDatabase db =this.getReadableDatabase();
		   ArrayList<Item> list =new ArrayList<Item>();
		   
		   String[] fromb = fromj.split("/");
		   String[] tob = toj.split("/");
		   String start = fromb[2]+"-"+fromb[1]+"-"+fromb[0];
		   String end = tob[2]+"-"+tob[1]+"-"+tob[0];
		   float income=0,expens=0,total=0;
		   Cursor res = db.rawQuery( "select sum(i_amt) from income where i_date between '"+start+"'and '"+end+"'", null );
		   Cursor exp = db.rawQuery( "select sum(e_amt) from expenses where e_date between '"+start+"'and '"+end+"'", null );
		   
		   if (res != null && exp != null) {
			    try {
			        if (res.moveToFirst() && exp.moveToFirst()) {
			            income = res.getFloat(0);
			            expens = exp.getFloat(0);
			             total = income - expens;
			             
			        }
			    } finally {
			        res.close();
			    }		   	  
		   }
		   		String in = String.format("%.2f", income);
		   		String ex = String.format("%.2f", expens);
		   		String tot = String.format("%.2f", total);
		   		
		   		String abc = null;
			   list.add(new Item("Income :",abc,in,1));
			   list.add(new Item("Expenses :",abc,ex,1));
			   list.add(new Item("Total :",abc,tot,1));
		   return list;  
	   }
	   public Integer deleteincome (Integer id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      return db.delete("income","id = ? ", 
	      new String[] { Integer.toString(id) });
	   }
	   
	
	   
}