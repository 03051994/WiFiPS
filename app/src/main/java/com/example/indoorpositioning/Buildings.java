package com.example.indoorpositioning;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class Buildings extends Activity {

	ArrayList<String> buildings;
	DatabaseHelper db;
	ListView buildingsList ;
	Button add;
	EditText buildingName;
	ArrayAdapter<String> arrayAdapter;

	
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.buildings);
		db=new DatabaseHelper(this);
		add=(Button) findViewById(R.id.add);
		buildingName= (EditText) findViewById(R.id.name);

		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String input=buildingName.getText().toString();
				Intent intent=new Intent(getApplicationContext(),Positions.class);
				intent.putExtra("BUILDING_NAME",input);
				startActivityForResult(intent,0);

			}
		});
		buildings = db.getBuildings();


		// Get the reference of ListViewAnimals
		buildingsList = (ListView) findViewById(R.id.buildingslist);
		
	
		
		// Create The Adapter with passing ArrayList as 3rd parameter
		 arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,buildings);
		// Set The Adapter
		buildingsList.setAdapter(arrayAdapter);
		buildingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    public void onItemClick(AdapterView parent, View v, int position, long id){
		        // Start your Activity according to the item just clicked.
		    String selectedBuilding=(String)parent.getItemAtPosition(position);
		    Intent intent=new Intent(getApplicationContext(),Positions.class);
			intent.putExtra("BUILDING_NAME",selectedBuilding);
			startActivityForResult(intent,0);
		    
		    }

			
		});

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        buildingsList,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    db.deleteBuilding(buildings.get(position));
                                    buildings.remove(position);
                                    arrayAdapter.notifyDataSetChanged();

                                }

                            }
                        });
        buildingsList.setOnTouchListener(touchListener);
		

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		buildings=db.getBuildings();
		arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1,buildings);
	
		buildingsList.setAdapter(arrayAdapter);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}

