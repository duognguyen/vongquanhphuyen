package duongnguyen.vongquanhphuyen.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.activities.DetailDestinationActivity;
import duongnguyen.vongquanhphuyen.models.Destinations;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {
    ArrayList<Destinations> listDes;

    public DestinationAdapter(ArrayList<Destinations> listDes) {
        this.listDes = listDes;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_des, parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        Destinations destinations = listDes.get(position);
        holder.tenDiaDiem.setText(destinations.getName());
        holder.viTri.setText(destinations.getLocation());
        Glide.with(holder.itemView.getContext())
                .load(destinations.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.anhHeader);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailDestinationActivity.class);
                intent.putExtra("name", destinations.getName());
                intent.putExtra("description", destinations.getDescription());
                intent.putExtra("image", destinations.getImageUrl());
                intent.putExtra("location", destinations.getLocation());
                intent.putExtra("latitude", destinations.getLatitude());
                intent.putExtra("longitude", destinations.getLongitude());
                intent.putExtra("map", destinations.getMap());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDes.size();
    }

    class DestinationViewHolder extends RecyclerView.ViewHolder{
        ImageView anhHeader;
        TextView tenDiaDiem;
        TextView viTri;
        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            anhHeader = itemView.findViewById(R.id.imgAnhMonAn);
            tenDiaDiem = itemView.findViewById(R.id.tvTenMonAn);
            viTri = itemView.findViewById(R.id.tvMoTa);
        }

    }
}