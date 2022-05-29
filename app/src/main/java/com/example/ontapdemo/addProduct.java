package com.example.ontapdemo;

import static com.example.ontapdemo.MainActivity.myDB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addProduct extends AppCompatActivity {

    Button addbtn, canbtn;
    EditText id, name, phone;
    int checkAdd;
    Contact_Minh currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        anhxa();
        myDB = new Minh_Sqlite(this, "Minh_Contact", null, 1);
        Intent intent = getIntent();
        checkAdd = intent.getIntExtra("AddOrCreate",0);
        if(checkAdd > 0)
        {
            addbtn.setText("Update");
            currentContact = (Contact_Minh) intent.getSerializableExtra("myContact");
            id.setText(""+currentContact.getId());
            name.setText(currentContact.getFullName());
            phone.setText(currentContact.getPhoneNumber());
        }
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAdd > 0){
                    myDB.addContact(new Contact_Minh(Integer.valueOf(id.getText().toString()), name.getText().toString(), phone.getText().toString(), R.drawable.toucan));
                    Toast.makeText(addProduct.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Contact_Minh temp = new Contact_Minh(name.getText().toString(), phone.getText().toString(), R.drawable.toucan);
                    if(myDB.updateFood(currentContact.getId(), temp))
                    {
                        Toast.makeText(addProduct.this, "Edit Successfully^^", Toast.LENGTH_SHORT).show();
                        Intent veMain = new Intent(addProduct.this, MainActivity.class);
                        startActivity(veMain);
                    }
                }
            }
        });

        canbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhxa() {
        addbtn = findViewById(R.id.addBTN);
        canbtn = findViewById(R.id.CancelBTN);
        id = findViewById(R.id.editID);
        name = findViewById(R.id.editName);
        phone = findViewById(R.id.editPhone);
    }
}