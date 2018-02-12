package simonwinther.pokemontable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabasehelper;
    private Button btnAddPokemon, btnViewPokemons, btnLogOut;
    private EditText editTextPokemonName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPokemonName = findViewById(R.id.editTextPokemonName);

        btnAddPokemon = findViewById(R.id.btnAddPokemon);
        btnViewPokemons = findViewById(R.id.btnViewPokemons);
        btnLogOut = findViewById(R.id.logOutButton);

        mDatabasehelper = new DatabaseHelper(this);

        btnAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = editTextPokemonName.getText().toString();
                if (editTextPokemonName.length() != 0) {
                    AddData(pokemonName);
                    editTextPokemonName.setText("");
                } else {
                    toastMessage("You must write a name for the Pokemon!");
                }
            }
        });

        btnViewPokemons.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void AddData(String pokemonName) {
        boolean insertData = mDatabasehelper.addData(pokemonName);

        if (insertData) {
            toastMessage("Pokemon added");
        } else {
            toastMessage("Pokemon not added");
        }
    }

    private void toastMessage (String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
