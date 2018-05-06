package comviewbiksappshome.google.httpssites.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // reference to the EditText
    private EditText mEditText;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enterMessage is the id of the EditText in activity_main.xml
        mEditText = (EditText) findViewById(R.id.enterMessage);
        // reference to the database
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");
    }
    public void sendButtonClicked(View v) {
        // we are getting the text message contents, converting it to a string, and getting rid
        // of white space
        // getText() returns and editable, which is why we have to use toString()
        // textMessage is final because we do not want to change it
        final String textMessage = mEditText.getText().toString().trim();
        // checking if the textMessage string is empty or not
        if (!TextUtils.isEmpty(textMessage)) {
            // inserting the string textMessage into the database if it's not empty
           final DatabaseReference newText = mDatabaseReference.push();
            newText.child("content").setValue(textMessage);
        }
    }
}
