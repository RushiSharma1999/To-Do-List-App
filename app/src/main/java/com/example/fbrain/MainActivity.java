package com.example.fbrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> toDo;
    private ArrayAdapter<String> toDoAdapter;
    private ListView lView;
    private Button myButton;
    private Button myButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = findViewById(R.id.ListView);
        readItems();
        myButton = findViewById(R.id.button_add_new_todo);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addItem(v);
            }
        });

        toDo = new ArrayList<>();
        toDoAdapter = new ArrayAdapter<>(this, R.layout.layout, toDo);
        lView.setAdapter(toDoAdapter);
        setUpListViewListener();

        myButton1 = findViewById(R.id.button_reset);
        myButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                reset(v);
            }
        });

    }
    // class to remove an item by holding on top of it
    private void setUpListViewListener() {
        lView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();
                        toDo.remove(i);
                        toDoAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }

    //class to add an item to the list by clicking "add to list"
    private void addItem(View v) {
        EditText input = findViewById(R.id.editTextToDo);
        String iText = input.getText().toString();
        toDoAdapter.add(iText);
        input.setText("");

    }

    //class to reset the list by clicking "reset"
    private void reset(View v) {
        EditText input = findViewById(R.id.editTextToDo);
        toDoAdapter.clear();
    }

    // class to read the items on the list that was saved before
    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "FlashBrain.txt");
        try {
            toDo = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            toDo = new ArrayList<String>();
        }
    }
    // class that writes the items into a file so they get saved
    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "FlashBrain.txt");
        try {
            FileUtils.writeLines(todoFile, toDo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}