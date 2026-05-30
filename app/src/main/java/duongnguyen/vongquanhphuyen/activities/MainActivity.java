package duongnguyen.vongquanhphuyen.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.fragments.DestinationFragment;
import duongnguyen.vongquanhphuyen.fragments.FoodFragment;
import duongnguyen.vongquanhphuyen.fragments.HomeFragment;
import duongnguyen.vongquanhphuyen.fragments.NoteFragment;

public class MainActivity extends AppCompatActivity {
    private LinearLayout lnDes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_destination) {
                selectedFragment = new DestinationFragment();
            } else if (itemId == R.id.nav_food) {
                selectedFragment = new FoodFragment();
            } else if (itemId == R.id.nav_note) {
                selectedFragment = new NoteFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}