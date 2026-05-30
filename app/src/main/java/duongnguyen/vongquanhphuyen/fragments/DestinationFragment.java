package duongnguyen.vongquanhphuyen.fragments; // Hoặc .fragments tùy bạn đặt tệp

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class DestinationFragment extends Fragment {

    private DestinationAdapter adapter;
    private ArrayList<Destinations> destinationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_destination, container, false);

        destinationList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = view.findViewById(R.id.rcvListDes);

        // 3. Thay chữ "this" bằng "requireContext()"
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DestinationAdapter(destinationList);
        recyclerView.setAdapter(adapter);


        // Tải dữ liệu từ Firebase gán vào Fragment
        db.collection("Destinations")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Kiểm tra xem Fragment còn hiển thị không trước khi cập nhật UI
                        if (!isAdded() || getActivity() == null) return;

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            destinationList.clear();
                            for (DocumentSnapshot d : list) {
                                Destinations obj = d.toObject(Destinations.class);
                                destinationList.add(obj);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (!isAdded() || getActivity() == null) return;
                        Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}