package duongnguyen.vongquanhphuyen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import duongnguyen.vongquanhphuyen.R;

public class FoodFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_food, container, false);

        // Sau này bạn viết code hiển thị món ăn, RecyclerView món ăn thì viết ở dưới này giống như DestinationFragment nhé.

        return view;
    }
}