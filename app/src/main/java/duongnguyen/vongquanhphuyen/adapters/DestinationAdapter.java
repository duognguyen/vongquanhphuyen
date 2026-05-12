package duongnguyen.vongquanhphuyen.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import duongnguyen.vongquanhphuyen.models.Destinations;
import duongnguyen.vongquanhphuyen.R;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {
    ArrayList<Destinations> listDes;

    public DestinationAdapter(ArrayList<Destinations> listDes) {
        this.listDes = listDes;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_destination, parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        Destinations destinations = listDes.get(position);
        holder.tenDiaDiem.setText(destinations.getName());
        Glide.with(holder.itemView.getContext())
                .load(destinations.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background) // Ảnh hiển thị khi đang load
                .error(R.drawable.ic_launcher_foreground)      // Ảnh hiển thị nếu link lỗi
                .into(holder.anhHeader);
    }

    @Override
    public int getItemCount() {
        return listDes.size();
    }

    class DestinationViewHolder extends RecyclerView.ViewHolder{
        ImageView anhHeader;
        TextView tenDiaDiem;
        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            anhHeader = itemView.findViewById(R.id.imgAnhHeader);
            tenDiaDiem = itemView.findViewById(R.id.tvTenDiaDiem);
        }
    }
}
