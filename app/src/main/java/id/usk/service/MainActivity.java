package id.usk.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import id.usk.service.adapter.UserAdapter;
import id.usk.service.model.Pesan;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<Pesan> list = new ArrayList<>();
    private UserAdapter userAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton btnAdd = findViewById(R.id.btn_add);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        userAdapter = new UserAdapter(getApplicationContext(), list);
        userAdapter.setDialog(pos -> {
            final CharSequence[] dialogItem = {"Edit", "Hapus"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setItems(dialogItem, (dialogInterface, i) -> {
                switch (i){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                        intent.putExtra("id", list.get(pos).getId());
                        intent.putExtra("nama", list.get(pos).getName());
                        intent.putExtra("alamat", list.get(pos).getAlamat());
                        intent.putExtra("jasa", list.get(pos).getJasa());

                        startActivity(intent);
                        break;
                    case 1:
                        deleteData(list.get(pos).getId());
                        break;
                }
            });
            dialog.show();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapter(userAdapter);

        btnAdd.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData(){
        progressDialog.show();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Pesan user = new Pesan(document.getString("name"), document.getString("alamat"), document.getString("jasa"));
                            user.setId(document.getId());
                            list.add(user);
                        }
                        userAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
    }

    private void deleteData(String id){
        progressDialog.show();
        db.collection("users").document(id)
                .delete()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Data gagal di hapus!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    getData();
                });
    }
}