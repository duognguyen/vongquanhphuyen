package duongnguyen.vongquanhphuyen.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.adapters.DestinationAdapter;
import duongnguyen.vongquanhphuyen.models.Destinations;

public class MainActivity extends AppCompatActivity {
    private DestinationAdapter adapter; // Tên Adapter bạn vừa tạo
    private ArrayList<Destinations> destinationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}