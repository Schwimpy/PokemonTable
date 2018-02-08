package simonwinther.pokemontable;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by simonwinther on 2018-02-06.
 */

public class ListDataActivity extends AppCompatActivity{

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        mListView = findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + name);

                Cursor data = mDatabaseHelper.getID(name);
                int ID = -1;
                while(data.moveToNext()){
                    ID = data.getInt(0);
                }
                if (ID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + ID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id", ID);
                    editScreenIntent.putExtra("name", name);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID associated with that Pokemon");
                }
            }
        });
    }

    private void toastMessage (String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
