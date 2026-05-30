package duongnguyen.vongquanhphuyen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class NoteFragment extends Fragment {
    private TextView tvTemperature, tvDescription, tvWindSpeed;
    private RecyclerView rvKhanCap, rvPhuongTien, rvVanHoa;
    private ArrayList<NoteItem> khanCapList, phuongTienList, vanHoaList;
    private NoteAdapter khanCapAdapter, phuongTienAdapter, vanHoaAdapter;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_note, container, false);

        db = FirebaseFirestore.getInstance();

        // 1. Ánh xạ các TextView thời tiết
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvWindSpeed = view.findViewById(R.id.tvWindSpeed);

        // 2. Ánh xạ các RecyclerView từ file layout XML
        rvKhanCap = view.findViewById(R.id.rcvUrgent);
        rvPhuongTien = view.findViewById(R.id.rcvVehicle);
        rvVanHoa = view.findViewById(R.id.rcvNote);

        // 3. Khởi tạo các danh sách List
        khanCapList = new ArrayList<>();
        phuongTienList = new ArrayList<>();
        vanHoaList = new ArrayList<>();

        // 4. Thiết lập LayoutManager định dạng danh sách dọc cho 3 RecyclerView
        rvKhanCap.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPhuongTien.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvVanHoa.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 5. Khởi tạo Adapter và gắn vào các RecyclerView tương ứng
        khanCapAdapter = new NoteAdapter(khanCapList);
        phuongTienAdapter = new NoteAdapter(phuongTienList);
        vanHoaAdapter = new NoteAdapter(vanHoaList);

        rvKhanCap.setAdapter(khanCapAdapter);
        rvPhuongTien.setAdapter(phuongTienAdapter);
        rvVanHoa.setAdapter(vanHoaAdapter);

        // 6. Kích hoạt lấy dữ liệu Thời tiết và Firebase
        getWeatherData();
        loadFirebaseData();

        return view;
    }

    private void getWeatherData() {
        String latitude = "13.08";
        String longitude = "109.302";
        String apiKey = "914a3293293e01cd95d0fb1e2cb8890c";

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=metric&lang=vi";

        // Đã sửa đổi thành requireContext() thay vì "this" để tránh lỗi ngữ cảnh trên Fragment
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Kiểm tra an toàn: Nếu Fragment đã bị đóng trước khi API trả về thì dừng lại
                if (!isAdded()) return;

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
                    double windSpeedKmh = windSpeed * 3.6;
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
                if (!isAdded()) return;
                tvDescription.setText("Lỗi tải thời tiết");
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void loadFirebaseData() {
        // 1. Dữ liệu khẩn cấp
        db.collection("Notes")
                .whereEqualTo("category", "urgent")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!isAdded() || getActivity() == null) return;

                    khanCapList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        NoteItem item = doc.toObject(NoteItem.class);
                        if (item != null) khanCapList.add(item);
                    }
                    khanCapAdapter.notifyDataSetChanged(); // Đã kích hoạt làm mới danh sách
                });

        // 2. Dữ liệu vùng Phương Tiện
        db.collection("Notes")
                .whereEqualTo("category", "vehicle")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!isAdded() || getActivity() == null) return;

                    phuongTienList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        NoteItem item = doc.toObject(NoteItem.class);
                        if (item != null) phuongTienList.add(item);
                    }
                    phuongTienAdapter.notifyDataSetChanged(); // Đã kích hoạt làm mới danh sách
                });

        // 3. Dữ liệu vùng Văn Hóa
        db.collection("Notes")
                .whereEqualTo("category", "note")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!isAdded() || getActivity() == null) return;

                    vanHoaList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        NoteItem item = doc.toObject(NoteItem.class);
                        if (item != null) vanHoaList.add(item);
                    }
                    vanHoaAdapter.notifyDataSetChanged(); // Đã kích hoạt làm mới danh sách
                });
    }
}