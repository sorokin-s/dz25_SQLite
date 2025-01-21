package com.amicus.dz25_sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

public class EditActivity extends AppCompatActivity {

    Button btnAdd,btnBack;
    EditText editTextName, editTextAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack= findViewById(R.id.btnBack);
        editTextName =findViewById(R.id.editName);
        editTextAuthor = findViewById(R.id.editAuthor);

        btnAdd.setOnClickListener(b->{
            if(!editTextName.getText().toString().isEmpty()) {
                createDialog();
            }
            else{
                Toast.makeText(EditActivity.this,"Заполните поле продукт",Toast.LENGTH_LONG).show();
            }
        });

        btnBack.setOnClickListener(b->{finish();});


    }
    public void createDialog(){// диалог добавления элемента в список
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Добавить:\n"+
                editTextName.getText().toString());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                intent.putExtra("name",editTextName.getText().toString());
                intent.putExtra("author",editTextAuthor.getText().toString());
                setResult(101,intent);

                finish();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}