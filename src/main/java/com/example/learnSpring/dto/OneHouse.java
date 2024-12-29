// dto: data transfer object
package com.example.learnSpring.dto;

import com.example.learnSpring.model.House;

public class OneHouse {
    private House house;

    public OneHouse(House house) {
        this.house = house;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
