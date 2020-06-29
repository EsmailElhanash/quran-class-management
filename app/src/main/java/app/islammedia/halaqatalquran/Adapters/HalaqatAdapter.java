package app.islammedia.halaqatalquran.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.database_room.Halaqa;

public class HalaqatAdapter extends RecyclerView.Adapter<HalaqatAdapter.HViewHolder> {

    private List<Halaqa> halaqat;

    public HalaqatAdapter(List<Halaqa> halaqat){
        this.halaqat = halaqat;
    }

    @NonNull
    @Override
    public HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.halaqat_adapter,parent,false);
        return new HViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return halaqat.size();
    }

    static class HViewHolder extends RecyclerView.ViewHolder{
        TextView hText;
        public HViewHolder(@NonNull View v) {
            super(v);
            hText = v.findViewById(R.id.hName);
        }
    }
}
