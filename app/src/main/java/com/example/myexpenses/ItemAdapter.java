package com.example.myexpenses;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapter extends ArrayAdapter<Item> {

	// declaring our ArrayList of items
	private ArrayList<Item> objects;
	Context context;

	public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
	}

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		Item i = objects.get(position);

		if (i != null) {

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.

			TextView ttd = (TextView) v.findViewById(R.id.toptextdata);			
			TextView mtd = (TextView) v.findViewById(R.id.middletextdata);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);

			// check to see if each individual textview is null.
			// if not, assign some text!
			
			if (ttd != null){
				ttd.setText(i.getName());
			}
			
			if (mtd != null){
				mtd.setText("Rs." + i.getPrice());
			}
			
			if (bt != null){
				bt.setText(i.getDetails());
			}
		}

		// the view must be returned to our activity
		return v;

	}
	
	
}