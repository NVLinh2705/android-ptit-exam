package com.example.androidptit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidptit.dao.RoomDAO;
import com.example.androidptit.databinding.ActivityAddRoomBinding;
import com.example.androidptit.model.Room;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRoomActivity extends AppCompatActivity {

    private ActivityAddRoomBinding b;
    private RoomDAO roomDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAddRoomBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Thêm phòng mới");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        roomDAO = new RoomDAO();

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

        // Initial state
        b.swTinhTrang.setChecked(true);

        b.btnAddRoom.setOnClickListener(v -> addRoom());
    }

    private void addRoom() {
        String maPhong = roomDAO.generateNewMaPhong(); // Auto-generate maPhong
        String tenPhong = b.etTenPhong.getText().toString().trim();
        String giaThueStr = b.etGiaThue.getText().toString().trim();
        boolean tinhTrang = b.swTinhTrang.isChecked();
        String tenNguoiThue = b.etTenNguoiThue.getText().toString().trim();
        String soDienThoai = b.etSoDienThoai.getText().toString().trim();

        if (TextUtils.isEmpty(tenPhong) || TextUtils.isEmpty(giaThueStr)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (Tên phòng, Giá thuê)", Toast.LENGTH_SHORT).show();
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

        Room newRoom = new Room(maPhong, tenPhong, giaThue, tinhTrang, tenNguoiThue, soDienThoai);
        roomDAO.addRoom(newRoom);
        Toast.makeText(this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
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
