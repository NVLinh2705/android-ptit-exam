package com.example.androidptit.dao;


import com.example.androidptit.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomDAO{
    private static List<Room> roomList = new ArrayList<>();
    static{
        roomList.add(new Room("P001", "Phòng 102", 2000000, false, "Nguyễn Văn A", "0912345678"));
        roomList.add(new Room("P002", "Phòng 201", 2500000, false, "Trần Thị B", "0987654321"));
    }

    public List<Room> getAllRoom(){
        return new ArrayList<>(roomList);
    }

    public Room getRoomByMaPhong(String maPhong){
        for(Room room : roomList){
            if(room.getMaPhong().equals(maPhong)){
                return room;
            }
        }
        return null;
    }

    public void addRoom(Room room){
        roomList.add(room);
    }

    public void updateRoom(Room room){
        for(int i=0;i<roomList.size();i++){
            if(roomList.get(i).getMaPhong().equals(room.getMaPhong())){
                roomList.set(i,room);
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
                    e.printStackTrace();
                }
            }
        }
        return String.format(Locale.getDefault(), "P%03d", maxNumber + 1);

    }

}