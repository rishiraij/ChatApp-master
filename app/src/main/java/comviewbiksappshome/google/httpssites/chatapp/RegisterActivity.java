package comviewbiksappshome.google.httpssites.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/*import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;*/

public class RegisterActivity extends AppCompatActivity {

    private Spinner etGrade;
    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private Button bRegister;
    private TextView signInLink;
    private EditText etPass2;
    private DatabaseReference mDatabaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etGrade = (Spinner) findViewById(R.id.etGrade);
        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPass2 = (EditText) findViewById(R.id.etReenterPass);
        bRegister = (Button) findViewById(R.id.bRegister);
        signInLink = (TextView) findViewById(R.id.signInLink);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    //Upload data to database
                    String user_email = etUsername.getText().toString().trim();
                    String user_pass = etPassword.getText().toString().trim();
                    String user_name = etName.getText().toString().trim();
                    String user_grade = etGrade.getSelectedItem().toString().trim();

                    final DatabaseReference newText = mDatabaseReference.push();
                    newText.child("email").setValue(user_email);
                    newText.child("grade").setValue(user_grade);
                    newText.child("password").setValue(user_pass);
                    newText.child("name").setValue(user_name).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                /*final String name = etName.getText().toString();
                final int grade = Integer.parseInt(etGrade.getSelectedItem().toString());
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new  AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username, grade, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);*/

                //Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                //RegisterActivity.this.startActivity(loginIntent);
            }
        });

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


    }
    private Boolean validate() {
        Boolean result = false;
        String name = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passCheck = etPass2.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        if(name.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
        } else if(!passCheck.trim().equals(password.trim())) {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }
}

