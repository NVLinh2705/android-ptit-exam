package com.example.androidptit.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidptit.databinding.ItemRoomBinding;
import com.example.androidptit.interfaces.listener.OnItemListener;
import com.example.androidptit.model.Room;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private OnItemListener listener;

    public RoomAdapter(List<Room> roomList, OnItemListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRoomBinding b = ItemRoomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RoomViewHolder(b, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        private ItemRoomBinding b; // ViewBinding instance
        private OnItemListener listener;

        public RoomViewHolder(@NonNull ItemRoomBinding b, OnItemListener listener) {
            super(b.getRoot());
            this.b = b;
            this.listener = listener;

            b.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            b.getRoot().setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onItemLongClick(getAdapterPosition());
                }
                return true; // Consume the long click event
            });
        }

        public void bind(Room room) {
            b.tvRoomName.setText("Tên phòng: " + room.getTenPhong());

            // Format price
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            b.tvRoomPrice.setText("Giá thuê: " + currencyFormat.format(room.getGiaThue()));

            String statusText;
            int statusColor;
            if (room.isTinhTrang()) {
                statusText = "Còn trống";
                statusColor = Color.GREEN; // Xanh -> Còn trống
            } else {
                statusText = "Đã thuê";
                statusColor = Color.RED; // Đỏ -> Đã thuê
            }
            b.tvRoomStatus.setText("Tình trạng: " + statusText);
            b.tvRoomStatus.setTextColor(statusColor);

            b.tvTenantName.setText("Người thuê: " + (room.getTenNguoiThue() != null ? room.getTenNguoiThue() : "N/A"));
            b.tvPhoneNumber.setText("Số điện thoại: " + (room.getSoDienThoai() != null ? room.getSoDienThoai() : "N/A"));

            // Hide tenant info if room is available
            if (room.isTinhTrang()) {
                b.tvTenantName.setVisibility(View.GONE);
                b.tvPhoneNumber.setVisibility(View.GONE);
            } else {
                b.tvTenantName.setVisibility(View.VISIBLE);
                b.tvPhoneNumber.setVisibility(View.VISIBLE);
            }
        }
    }
}
