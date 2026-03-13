package com.example.androidptit.dao;

import android.app.Application;

import com.example.androidptit.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomDAO extends Application {
    private static List<Room> roomList = new ArrayList<>();


    static {
        roomList.add(new Room("P001", "Phòng 101", 1500000, true, null, null));
        roomList.add(new Room("P002", "Phòng 102", 2000000, false, "Nguyễn Văn A", "0912345678"));
        roomList.add(new Room("P003", "Phòng 103", 1800000, true, null, null));
        roomList.add(new Room("P004", "Phòng 201", 2500000, false, "Trần Thị B", "0987654321"));
    }

    public List<Room> getAllRoom() {
        return new ArrayList<>(roomList);
    }

    public Room getRoomByMaPhong(String maPhong) {
        for (Room room : roomList) {
            if (room.getMaPhong().equals(maPhong)) {
                return room;
            }
        }
        return null;
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    public void updateRoom(Room updatedRoom) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getMaPhong().equals(updatedRoom.getMaPhong())) {
                roomList.set(i, updatedRoom);
                return;
            }
        }
    }

    public void deleteRoom(String maPhong) {
        roomList.removeIf(room -> room.getMaPhong().equals(maPhong));
    }

    public boolean isMaPhongExists(String maPhong) {
        for (Room room : roomList) {
            if (room.getMaPhong().equals(maPhong)) {
                return true;
            }
        }
        return false;
    }

    public String generateNewMaPhong() {
        int maxNumber = 0;
        for (Room room : roomList) {
            String maPhong = room.getMaPhong();
            if (maPhong != null && maPhong.startsWith("P")) {
                try {
                    int number = Integer.parseInt(maPhong.substring(1));
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    // Ignore malformed maPhong values
                }
            }
        }
        return String.format(Locale.getDefault(), "P%03d", maxNumber + 1);
    }
}
