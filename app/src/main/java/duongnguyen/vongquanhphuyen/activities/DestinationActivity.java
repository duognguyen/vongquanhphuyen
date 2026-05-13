package duongnguyen.vongquanhphuyen.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.adapters.DestinationAdapter;
import duongnguyen.vongquanhphuyen.models.Destinations;

public class DestinationActivity extends AppCompatActivity {
    private DestinationAdapter adapter; // Tên Adapter bạn vừa tạo
    private ArrayList<Destinations> destinationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        destinationList =  new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.rcvListDestination);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DestinationAdapter(destinationList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbarDes);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button back = findViewById(R.id.btnBackDetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        db.collection("Destinations")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Destinations obj = d.toObject(Destinations.class);
                                destinationList.add(obj);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(DestinationActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DestinationActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}