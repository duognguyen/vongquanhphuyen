package duongnguyen.vongquanhphuyen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import duongnguyen.vongquanhphuyen.R;

public class MainActivity extends AppCompatActivity {
    private LinearLayout lnDes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lnDes = findViewById(R.id.lnLayoutDes);
        lnDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screeDes = new Intent(MainActivity.this, DestinationActivity.class);
                startActivity(screeDes);
            }
        });
        LinearLayout lnNote = findViewById(R.id.lnLayoutNote);
        lnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screeNote = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(screeNote);
            }
        });

    }
}