package com.example.androidptit.view;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidptit.dao.RoomDAO;
import com.example.androidptit.databinding.ActivityEditRoomBinding;
import com.example.androidptit.model.Room;

import java.util.Locale;
import java.util.regex.Pattern;

public class EditRoomActivity extends AppCompatActivity {

    private ActivityEditRoomBinding b;
    private RoomDAO roomDAO;
    private String originalMaPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityEditRoomBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Sửa thông tin phòng");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        roomDAO = new RoomDAO();

        // Get room data from intent
        if (getIntent().hasExtra("ROOM_MA_PHONG")) {
            originalMaPhong = getIntent().getStringExtra("ROOM_MA_PHONG");
            Room room = roomDAO.getRoomByMaPhong(originalMaPhong);
            if (room != null) {
                displayRoomData(room);
            } else {
                Toast.makeText(this, "Không tìm thấy phòng", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Không có thông tin phòng", Toast.LENGTH_SHORT).show();
            finish();
        }

        b.swTinhTrang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Còn trống
                b.tilTenNguoiThue.setVisibility(View.GONE);
                b.etTenNguoiThue.setText(null);
                b.tilSoDienThoai.setVisibility(View.GONE);
                b.etSoDienThoai.setText(null);
            } else {
                // Đã thuê
                b.tilTenNguoiThue.setVisibility(View.VISIBLE);
                b.tilSoDienThoai.setVisibility(View.VISIBLE);
            }
        });

        b.btnUpdateRoom.setOnClickListener(v -> updateRoom());
    }

    private void displayRoomData(Room room) {
        b.etMaPhong.setText(room.getMaPhong());
        b.etTenPhong.setText(room.getTenPhong());
        b.etGiaThue.setText(String.format(Locale.getDefault(), "%.0f", room.getGiaThue()));
        b.swTinhTrang.setChecked(room.isTinhTrang());

        if (!room.isTinhTrang()) {
            b.tilTenNguoiThue.setVisibility(View.VISIBLE);
            b.etTenNguoiThue.setText(room.getTenNguoiThue());
            b.tilSoDienThoai.setVisibility(View.VISIBLE);
            b.etSoDienThoai.setText(room.getSoDienThoai());
        } else {
            b.tilTenNguoiThue.setVisibility(View.GONE);
            b.tilSoDienThoai.setVisibility(View.GONE);
        }
    }

    private void updateRoom() {
        String tenPhong = b.etTenPhong.getText().toString().trim();
        String giaThueStr = b.etGiaThue.getText().toString().trim();
        boolean tinhTrang = b.swTinhTrang.isChecked();
        String tenNguoiThue = b.etTenNguoiThue.getText().toString().trim();
        String soDienThoai = b.etSoDienThoai.getText().toString().trim();

        if (TextUtils.isEmpty(tenPhong) || TextUtils.isEmpty(giaThueStr)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
            return;
        }

        double giaThue;
        try {
            giaThue = Double.parseDouble(giaThueStr);
            if (giaThue <= 0) {
                Toast.makeText(this, "Giá thuê phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá thuê không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!tinhTrang) {
            // If rented, tenant info is required and validated
            if (TextUtils.isEmpty(tenNguoiThue) || TextUtils.isEmpty(soDienThoai)) {
                Toast.makeText(this, "Vui lòng nhập thông tin người thuê và số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate tenant name (no numbers)
            Pattern tenantNamePattern = Pattern.compile("[\\p{L}\\s]+"); // Unicode letters and spaces
            if (!tenantNamePattern.matcher(tenNguoiThue).matches()) {
                Toast.makeText(this, "Tên người thuê không được chứa số hoặc ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate phone number (only digits and optional leading +)
            Pattern phoneNumberPattern = Pattern.compile("^\\+?[0-9]{10,15}$"); // Basic phone number regex
            if (!phoneNumberPattern.matcher(soDienThoai).matches()) {
                Toast.makeText(this, "Số điện thoại không hợp lệ (chỉ chứa số và có thể có dấu '+' ở đầu)", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Room updatedRoom = new Room(originalMaPhong, tenPhong, giaThue, tinhTrang, tenNguoiThue, soDienThoai);
        roomDAO.updateRoom(updatedRoom);
        Toast.makeText(this, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show();
        finish(); // Go back to MainActivity
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
