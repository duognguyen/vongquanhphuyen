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
    TextView tvDetailName, tvDetailLocation, tvDescription;
    ImageView imgDetail, imgMap, btnBack;
    double latiude, longitude;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_destination);

        btnBack = findViewById(R.id.btnBackDesDetail);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvDetailName = findViewById(R.id.tvDetailDesName);
        imgDetail = findViewById(R.id.imgContentDesDetail);
        tvDetailLocation = findViewById(R.id.textDesLocation);
        tvDescription = findViewById(R.id.tvDescription);
        imgMap = findViewById(R.id.imgMap);

        name = getIntent().getStringExtra("name");
        String imageUrl = getIntent().getStringExtra("image");
        String location = getIntent().getStringExtra("location");
        String map = getIntent().getStringExtra("map");
        String descrip = getIntent().getStringExtra("description");
        latiude = getIntent().getDoubleExtra("latitude", 13.08);
        longitude = getIntent().getDoubleExtra("longitude", 109.30);

        tvDetailName.setText(name);
        tvDetailLocation.setText(location);
        tvDescription.setText(descrip);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgDetail);

        int resId = getResources().getIdentifier(map, "drawable", getPackageName());
        Glide.with(this)
                .load(resId)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgMap);
        googleMap();
    }
    public  void googleMap(){
        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "geo:" + latiude + "," + longitude + "?q=" + latiude + "," + longitude + "(" + name + ")";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    String webUri = "https://www.google.com/maps/search/?api=1&query=" + latiude + "," + longitude;
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUri));
                    startActivity(webIntent);
                }
            }
        });
    }
}