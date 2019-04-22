package com.andrey.wooppaymessenger.model;

public class Room {
    protected Long id;
    protected Long userId;

    protected String roomName;

    public Room(){

    }

    public Room(Long id, String roomName, Long userId) {
        this.roomName = roomName;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
