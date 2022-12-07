package id.usk.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import id.usk.service.R;
import id.usk.service.model.Pesan;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private final List<Pesan> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public UserAdapter(Context ignoredContext, List<Pesan> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pesan, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(list.get(position).getName());
        holder.alamat.setText(list.get(position).getAlamat());
        holder.jasa.setText(list.get(position).getJasa());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, alamat, jasa;
         public MyViewHolder(@NonNull View itemView){
             super(itemView);
             nama = itemView.findViewById(R.id.nama);
             alamat = itemView.findViewById(R.id.alamat);
             jasa = itemView.findViewById(R.id.jasa);
             itemView.setOnClickListener(view -> {
                 if(dialog!=null){
                     dialog.onClick(getLayoutPosition());
                 }
             });
         }
    }
}