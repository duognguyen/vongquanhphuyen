package duongnguyen.vongquanhphuyen.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.adapters.CommentAdapter;
import duongnguyen.vongquanhphuyen.models.Comment;

public class DetailDestinationActivity extends AppCompatActivity {
    TextView tvDetailName, tvDetailLocation, tvDescription;
    ImageView imgDetail, imgMap, btnBack;
    double latiude, longitude;
    String name;
    private RecyclerView rcvComments;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList;

    private LinearLayout layoutInputComment;
    private EditText edtInputComment;
    private ImageView btnSendComment;
    private Button btnLoginToComment;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_destination);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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

        rcvComments = findViewById(R.id.rcvComments);
        layoutInputComment = findViewById(R.id.layoutInputComment);
        edtInputComment = findViewById(R.id.edtInputComment);
        btnSendComment = findViewById(R.id.btnSendComment);
        btnLoginToComment = findViewById(R.id.btnLoginToComment);

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

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        rcvComments.setLayoutManager(new LinearLayoutManager(this));
        rcvComments.setAdapter(commentAdapter);

        loadCommentsFromFirebase();

        btnLoginToComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailDestinationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtInputComment.getText().toString().trim();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (content.isEmpty()) {
                    Toast.makeText(DetailDestinationActivity.this, "Nội dung bình luận không được trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentUser != null) {
                    sendCommentToFirebase(content, currentUser);
                }
            }
        });
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
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            layoutInputComment.setVisibility(View.VISIBLE);
            btnLoginToComment.setVisibility(View.GONE);
        } else {
            layoutInputComment.setVisibility(View.GONE);
            btnLoginToComment.setVisibility(View.VISIBLE);
        }
    }

    private void loadCommentsFromFirebase() {
        if (name == null) return;

        db.collection("Comments")
                .whereEqualTo("destinationId", name)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("FirestoreError", "Lỗi tải bình luận: " + error.getMessage());
                        return;
                    }
                    if (value != null) {
                        commentList.clear();
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            Comment obj = doc.toObject(Comment.class);
                            if (obj != null) {
                                commentList.add(obj);
                            }
                        }
                        commentAdapter.notifyDataSetChanged();

                        if (commentList.size() > 0) {
                            rcvComments.scrollToPosition(commentList.size() - 1);
                        }
                    }
                });
    }

    private void sendCommentToFirebase(String text, FirebaseUser currentUser) {
        String currentTimestamp = String.valueOf(System.currentTimeMillis());

        String userId = currentUser.getUid();
        String userName = currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()
                ? currentUser.getDisplayName() : "Thành viên";
        String userAvatar = currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : "";

        Comment newComment = new Comment(
                text,
                name,
                currentTimestamp,
                userAvatar,
                userId,
                userName
        );

        db.collection("Comments")
                .add(newComment)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(DetailDestinationActivity.this, "Đăng bình luận thành công!", Toast.LENGTH_SHORT).show();
                    edtInputComment.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DetailDestinationActivity.this, "Lỗi gửi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}