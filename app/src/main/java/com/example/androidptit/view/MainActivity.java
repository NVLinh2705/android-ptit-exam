package com.example.androidptit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidptit.dao.RoomDAO;
import com.example.androidptit.databinding.ActivityMainBinding;
import com.example.androidptit.interfaces.listener.OnItemListener;
import com.example.androidptit.model.Room;
import com.example.androidptit.view.adapter.RoomAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemListener {

    private ActivityMainBinding b;
    private RoomDAO roomDAO;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Quản lý phòng trọ");
        }

        roomDAO = new RoomDAO();

        setupRecyclerView();

        b.fabAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        roomList = roomDAO.getAllRoom();
        roomAdapter = new RoomAdapter(roomList, this);
        b.recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        b.recyclerViewRooms.setAdapter(roomAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        roomList.clear();
        roomList.addAll(roomDAO.getAllRoom());
        roomAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}