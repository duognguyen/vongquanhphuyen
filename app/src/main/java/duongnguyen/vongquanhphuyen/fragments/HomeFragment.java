package duongnguyen.vongquanhphuyen.fragments;// Bạn kiểm tra xem dòng package này có giống MainActivity của bạn không nhé

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import duongnguyen.vongquanhphuyen.R;

public class HomeFragment extends Fragment {

    private LinearLayout lnDes, lnNote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Kết nối với file xml vừa tạo ở Bước 1
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Bê toàn bộ logic click từ MainActivity cũ sang đây, lưu ý phải có "view." ở trước findViewById
        lnDes = view.findViewById(R.id.lnLayoutDes);
        lnNote = view.findViewById(R.id.lnLayoutNote);

        lnDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fragment chuyển màn hình thì dùng getActivity() thay cho MainActivity.this
                Intent screeDes = new Intent(getActivity(), DestinationFragment.class);
                startActivity(screeDes);
            }
        });

        lnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screeNote = new Intent(getActivity(), NoteFragment.class);
                startActivity(screeNote);
            }
        });

        return view;
    }
}