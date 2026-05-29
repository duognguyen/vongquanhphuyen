package duongnguyen.vongquanhphuyen.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.adapters.NoteAdapter;
import duongnguyen.vongquanhphuyen.models.NoteItem;

public class NoteActivity extends AppCompatActivity {
    private TextView tvTemperature, tvDescription, tvWindSpeed;
    private RecyclerView rvKhanCap, rvPhuongTien, rvVanHoa;
    private ArrayList<NoteItem> khanCapList;
    private ArrayList<NoteItem> phuongTienList;
    private ArrayList<NoteItem> vanHoaList;

    // Khai báo 2 Adapter (Nếu giao diện 2 vùng giống nhau, bạn dùng chung 1 lớp Adapter luôn cũng được!)
    private NoteAdapter khanCapAdapter;
    private NoteAdapter phuongTienAdapter;
    private NoteAdapter vanHoaAdapter;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        db = FirebaseFirestore.getInstance();
        // Thời tiết
        tvTemperature = findViewById(R.id.tvTemperature);
        tvDescription = findViewById(R.id.tvDescription);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);

        // Phương tiện, văn hóa, khẩn cấp
        rvKhanCap = findViewById(R.id.rcvUrgent);
        rvKhanCap.setLayoutManager(new LinearLayoutManager(this));
        khanCapList = new ArrayList<>();
        khanCapAdapter = new NoteAdapter(khanCapList);
        rvKhanCap.setAdapter(khanCapAdapter);

        rvPhuongTien = findViewById(R.id.rcvVehicle);
        rvPhuongTien.setLayoutManager(new LinearLayoutManager(this));
        phuongTienList = new ArrayList<>();
        phuongTienAdapter = new NoteAdapter(phuongTienList);
        rvPhuongTien.setAdapter(phuongTienAdapter);

        rvVanHoa = findViewById(R.id.rcvNote);
        rvVanHoa.setLayoutManager(new LinearLayoutManager(this));
        vanHoaList = new ArrayList<>();
        vanHoaAdapter = new NoteAdapter(vanHoaList);
        rvVanHoa.setAdapter(vanHoaAdapter);

        rvKhanCap.setNestedScrollingEnabled(false);
        rvPhuongTien.setNestedScrollingEnabled(false);
        rvVanHoa.setNestedScrollingEnabled(false);
        getWeatherData();
        getNoteDataFromFirebase();
    }

    private void getWeatherData() {
        String latitude = "13.08";
        String longitude = "109.302";
        String apiKey = "914a3293293e01cd95d0fb1e2cb8890c";

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=metric&lang=vi";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainObject = response.getJSONObject("main");
                    double temp = mainObject.getDouble("temp");
                    int roundedTemp = (int) Math.round(temp);
                    tvTemperature.setText(roundedTemp + "°C");

                    JSONObject weatherObject = response.getJSONArray("weather").getJSONObject(0);
                    String description = weatherObject.getString("description");
                    description = description.substring(0, 1).toUpperCase() + description.substring(1);
                    tvDescription.setText(description);

                    JSONObject windObject = response.getJSONObject("wind");
                    double windSpeed = windObject.getDouble("speed");
                    double windSpeedKmh = windSpeed*3.6;
                    double roundedWindSpeed = Math.round(windSpeedKmh * 10.0) / 10.0;
                    tvWindSpeed.setText("Gió: " + roundedWindSpeed + " km/h");
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvDescription.setText("Lỗi xử lý dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvDescription.setText("Lỗi tải thời tiết");
            }
        });
        queue.add(jsonObjectRequest);
    }
    private void getNoteDataFromFirebase() {
        // 1. Lấy dữ liệu cho vùng Khẩn Cấp
        db.collection("Notes")
                .whereEqualTo("category", "urgent")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (isFinishing() || isDestroyed()) return;
                    khanCapList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        NoteItem item = doc.toObject(NoteItem.class);
                        if (item != null) {
                            khanCapList.add(item);
                        }
                    }
                    khanCapAdapter.notifyDataSetChanged();
                });

        // 2. Dữ liệu vùng Phương Tiện (Đã thêm kiểm tra Null chống sập app)
        db.collection("Notes")
                .whereEqualTo("category", "vehicle")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (isFinishing() || isDestroyed()) return;
                    phuongTienList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        NoteItem item = doc.toObject(NoteItem.class);
                        if (item != null) {
                            phuongTienList.add(item);
                        }
                    }
                    phuongTienAdapter.notifyDataSetChanged();
                });

        // 3. Dữ liệu vùng Văn Hóa
        db.collection("Notes")
                .whereEqualTo("category", "note")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (isFinishing() || isDestroyed()) return;
                    vanHoaList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        NoteItem item = doc.toObject(NoteItem.class);
                        if (item != null) {
                            vanHoaList.add(item);
                        }
                    }
                    vanHoaAdapter.notifyDataSetChanged();
                });
    }

}
