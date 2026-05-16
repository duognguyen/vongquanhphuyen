package duongnguyen.vongquanhphuyen.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import duongnguyen.vongquanhphuyen.R;

public class DetailDestinationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_destination);
        ImageView btnBack = findViewById(R.id.btnBackDesDetail);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvDetailNmae = findViewById(R.id.tvDetailDesName);
        ImageView imgDetail = findViewById(R.id.imgContentDesDetail);
        TextView tvDetailLocation = findViewById(R.id.textDesLocation);
        ImageView imgMap = findViewById(R.id.imgMap);
        String name = getIntent().getStringExtra("name");
        String imageUrl = getIntent().getStringExtra("image");
        String location = getIntent().getStringExtra("location");
        String map = getIntent().getStringExtra("map");

        tvDetailNmae.setText(name);
        tvDetailLocation.setText(location);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgDetail);
        Glide.with(this)
                .load(map)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgMap);

        double latiude = getIntent().getDoubleExtra("latitude", 13.08);
        double longitude = getIntent().getDoubleExtra("longitude", 109.30);

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "geo:" + latiude + "," + longitude + "?q=" + latiude + "," + longitude + "(" + name + ")";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // Nếu điện thoại không có sẵn app Google Maps, mở bằng trình duyệt Web
                    String webUri = "https://www.google.com/maps/search/?api=1&query=" + latiude + "," + longitude;
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUri));
                    startActivity(webIntent);
                }
            }
        });
    }
}