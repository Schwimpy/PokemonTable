package simonwinther.pokemontable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText loginName, password;
    Button registerButton, loginButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         loginName = findViewById(R.id.nameEditText);
         password = findViewById(R.id.passwordEditText);

         registerButton = findViewById(R.id.registerButton);
         loginButton = findViewById(R.id.loginButton);

         db = new DatabaseHelper(this);

         registerButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name = loginName.getText().toString();
                 String pass = password.getText().toString();

                 if (name.equals("")||pass.equals("")) {
                     toastMessage("You must fill all fields");
                 } else {
                     Boolean checkName = db.checkName(name);
                        if (checkName) {
                            Boolean Register = db.insert(name,pass);
                            if (Register) {
                                toastMessage("You have now registered");
                            }
                        }
                        else {
                            toastMessage("That name already exists, \n please chose another one");
                        }

                 }
             }
         });

         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name = loginName.getText().toString();
                 String pass = password.getText().toString();
                 Boolean checknamepass = db.namePlusPassword(name,pass);
                 if (checknamepass) {
                     toastMessage("succesfully logged in");
                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     startActivity(intent);
                 } else {
                     toastMessage("Name and password did not match, or is not registered");
                 }
             }
         });
    }

    private void toastMessage (String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
