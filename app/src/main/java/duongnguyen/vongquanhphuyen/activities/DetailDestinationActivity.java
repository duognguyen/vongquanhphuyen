package duongnguyen.vongquanhphuyen.activities;

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
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        ImageView btnBack = findViewById(R.id.btnBackFoodDetail);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvDetailNmae = findViewById(R.id.tvDetailFoodName);
        ImageView imgDetail = findViewById(R.id.imgContentFoodDetail);

        String name = getIntent().getStringExtra("name");
        String imageUrl = getIntent().getStringExtra("image");

        tvDetailNmae.setText(name);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgDetail);
    }
}