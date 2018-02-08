package simonwinther.pokemontable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by simonwinther on 2018-02-06.
 */

public class EditDataActivity extends AppCompatActivity{

    private static final String Tag = "EditDataActivity";

    private Button btnSave, btnDelete, btnViewData;
    private EditText editable_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewData = findViewById(R.id.btnViewTable);
        editable_item = findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent recievedIntent = getIntent();

        selectedID = recievedIntent.getIntExtra("id", -1);

        selectedName = recievedIntent.getStringExtra("name");

        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if (!item.equals("")){
                    mDatabaseHelper.updatedName(item, selectedID, selectedName);
                    toastMessage("Name Changed");
                } else {
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("removed from database");
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void toastMessage (String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
