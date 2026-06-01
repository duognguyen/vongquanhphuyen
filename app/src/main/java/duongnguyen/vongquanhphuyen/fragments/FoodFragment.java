package duongnguyen.vongquanhphuyen.fragments;

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
import duongnguyen.vongquanhphuyen.adapters.FoodAdapter;
import duongnguyen.vongquanhphuyen.models.Foods;

public class FoodFragment extends Fragment {
    private FoodAdapter adapter;
    private ArrayList<Foods> foodList;

    private ArrayList<Foods> originalList;
    private EditText edtSearch;
    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        db = FirebaseFirestore.getInstance();

        foodList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.rcvListFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new FoodAdapter(foodList);
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
        loadDataFood();


        return view;
    }
    public void loadDataFood(){
        db.collection("Foods")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!isAdded()) return;

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            foodList.clear();
                            originalList.clear();
                            for (DocumentSnapshot d : list) {
                                Foods obj = d.toObject(Foods.class);
                                foodList.add(obj);
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
        ArrayList<Foods> filteredList = new ArrayList<>();

        for (Foods item : originalList) {
            if (item.getNameFood().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        foodList.clear();
        foodList.addAll(filteredList);
        adapter.notifyDataSetChanged();

    }
}