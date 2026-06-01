package duongnguyen.vongquanhphuyen.fragments; // Hoặc .fragments tùy bạn đặt tệp

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private ArrayList<Destinations> originalList;
    private EditText edtSearch;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        destinationList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = view.findViewById(R.id.rcvListDes);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DestinationAdapter(destinationList);
        recyclerView.setAdapter(adapter);

        edtSearch = view.findViewById(R.id.edtSearch);
        originalList = new ArrayList<>();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSearch(s.toString());
            }
        });

        loadDataDes();
        return view;
    }

    public void loadDataDes() {
        db.collection("Destinations")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!isAdded()) return;

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            destinationList.clear();
                            originalList.clear();
                            for (DocumentSnapshot d : list) {
                                Destinations obj = d.toObject(Destinations.class);
                                destinationList.add(obj);
                                originalList.add(obj);
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
    }

    private void filterSearch(String text) {
        ArrayList<Destinations> filteredList = new ArrayList<>();

        for (Destinations item : originalList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())
                    || item.getLocation().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        destinationList.clear();
        destinationList.addAll(filteredList);
        adapter.notifyDataSetChanged();

    }
}