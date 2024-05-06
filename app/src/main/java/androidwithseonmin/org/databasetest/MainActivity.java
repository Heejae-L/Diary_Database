package androidwithseonmin.org.databasetest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText nameInput, numberInput;
    ListView userList;
    Button addUserButton;
    SQLiteHelper dbHelper;
    ArrayList<String> userData;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLiteHelper(this);
        nameInput = findViewById(R.id.nameInput);
        numberInput = findViewById(R.id.numberInput);
        userList = findViewById(R.id.userList);
        addUserButton = findViewById(R.id.addUserButton);
        userData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userData);
        userList.setAdapter(adapter);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String number = numberInput.getText().toString();
                addUser(name, number);
                updateUserList();
            }
        });

        updateUserList();
    }

    private void addUser(String name, String number) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO USER (name, number) VALUES (?, ?)", new String[] {name, number});
    }

    private void updateUserList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_ID, name, number FROM USER", null);
        userData.clear();
        while (cursor.moveToNext()) {
            int userIdIndex = cursor.getColumnIndex("user_ID");
            int nameIndex = cursor.getColumnIndex("name");
            int numberIndex = cursor.getColumnIndex("number");

            if (userIdIndex != -1 && nameIndex != -1 && numberIndex != -1) {
                int id = cursor.getInt(userIdIndex);
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);
                userData.add(id + " - " + name + " - " + number);
            } else {
                // Log an error or handle the case where column indices are invalid
                Log.e("MainActivity", "One or more column names are invalid.");
            }
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }

}
