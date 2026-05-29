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
import duongnguyen.vongquanhphuyen.models.NoteItem;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    ArrayList<NoteItem> listNote;

    public NoteAdapter(ArrayList<NoteItem> listNote) {
        this.listNote = listNote;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteAdapter.NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        NoteItem noteItem = listNote.get(position);
        holder.tvTitle.setText(noteItem.getTitle());
        holder.tvContent.setText(noteItem.getContent());
        Glide.with(holder.itemView.getContext())
                .load(noteItem.getIcon())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgAnhIcon);
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnhIcon;
        TextView tvContent;
        TextView tvTitle;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhIcon = itemView.findViewById(R.id.imgNote);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
        }

    }
}
