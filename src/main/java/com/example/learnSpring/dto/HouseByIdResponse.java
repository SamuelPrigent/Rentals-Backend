// dto: data transfer object
package com.example.learnSpring.dto;

import com.example.learnSpring.model.House;

public class HouseByIdResponse {
    private House house;

    public HouseByIdResponse(House house) {
        this.house = house;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
