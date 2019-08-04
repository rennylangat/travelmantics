package com.renny.user.travelmantics;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle;
    EditText txtDescription;
    EditText txtPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        FirebaseUtil.openFbReference("travelmantics",this);
        mFirebaseDatabase=FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference=FirebaseUtil.mDatabaseReference;
        txtTitle=(EditText)findViewById(R.id.txtTitle);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        txtPrice=(EditText)findViewById(R.id.txtPrice);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_menu:
            saveDeals();
                Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                clean();
                return  true;

            default:
                return  super.onOptionsItemSelected(item);
        }
        
    }

    private void clean() {
        txtTitle.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
        txtTitle.requestFocus();
    }


    private void saveDeals() {
        String title=txtTitle.getText().toString();
        String price=txtPrice.getText().toString();
        String description=txtDescription.getText().toString();
        TravelDeals deals=new TravelDeals(title,description,price,"");
        mDatabaseReference.push().setValue(deals);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
}

