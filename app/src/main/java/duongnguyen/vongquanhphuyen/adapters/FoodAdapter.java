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
import java.util.Arrays;

import duongnguyen.vongquanhphuyen.R;
import duongnguyen.vongquanhphuyen.fragments.DestinationFragment;
import duongnguyen.vongquanhphuyen.models.Foods;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    ArrayList<Foods> listFood;

    public FoodAdapter(ArrayList<Foods> listFood) {
        this.listFood = listFood;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DestinationFragment.class);
                intent.putExtra("name", foods.getNameFood());
                intent.putExtra("description", foods.getDescriptionFood());
                intent.putExtra("image", foods.getImageFood());
                ArrayList<String> resIds = new ArrayList<>(Arrays.asList(foods.getRestaurantIds()));
                intent.putStringArrayListExtra("restaurantIds", resIds);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView anhHeader;
        TextView tenMonAn;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            anhHeader = itemView.findViewById(R.id.imgAnhHeader);
            tenMonAn = itemView.findViewById(R.id.tvTenDiaDiem);
        }

    }
}

