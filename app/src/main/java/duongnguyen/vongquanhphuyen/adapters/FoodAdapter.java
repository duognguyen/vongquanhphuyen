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

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.models.Foods;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    ArrayList<Foods> listFood;

    public FoodAdapter(ArrayList<Foods> listFood) {
        this.listFood = listFood;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Foods foods = listFood.get(position);
        holder.tenMonAn.setText(foods.getNameFood());
        Glide.with(holder.itemView.getContext())
                .load(foods.getImageFood())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.anhHeader);
        holder.moTaMonAn.setText(foods.getDescriptionFood());
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView anhHeader;
        TextView tenMonAn;
        TextView moTaMonAn;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            anhHeader = itemView.findViewById(R.id.imgAnhMonAn);
            tenMonAn = itemView.findViewById(R.id.tvTenMonAn);
            moTaMonAn = itemView.findViewById(R.id.tvMoTa);
        }

    }
}

