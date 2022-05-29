package com.example.ontapdemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact_Minh> contacts = new ArrayList<>();
    ListView contactView;
    Contact_Minh tmpFood;
    MinhAdapter adapter;
    EditText searchBox;
    int viTri = -1;
    public static Minh_Sqlite myDB;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new Minh_Sqlite(this, "Minh_Contact", null, 1);

        contacts = new ArrayList<>();
        contacts = myDB.getAllContact();

        adapter = new MinhAdapter(MainActivity.this, contacts);
        contactView = findViewById(R.id.listContact);
        contactView.setAdapter(adapter);

        searchBox = findViewById(R.id.editSearch);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        contactView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                viTri = contacts.get(i).getId();
                tmpFood = contacts.get(i);
                Toast.makeText(MainActivity.this, ""+contacts.get(i).getId(), Toast.LENGTH_SHORT).show();
                registerForContextMenu(contactView);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delebtn:
                deleteOption();
                return true;
            case R.id.editbtn:
                editOption();
            default:
                return super.onContextItemSelected(item);

        }

    }

    private void editOption() {
        Intent intent = new Intent(MainActivity.this, addProduct.class);
        intent.putExtra("myContact", (Serializable) tmpFood);
        intent.putExtra("AddOrCreate", 1);
        startActivity(intent);

    }

    private void deleteOption() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Confirm");
        alert.setMessage("Lương Văn Minh wants to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    myDB.deleteContact(viTri);
                    contacts.removeIf(food -> food.getId() == viTri);
                    viTri = -1;
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void AddNewProduct(View view){
        Intent intent = new Intent(MainActivity.this, addProduct.class);
        intent.putExtra("AddOrCreate", 0);
        startActivity(intent);    }


}