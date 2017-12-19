package com.techdmz.lamooss.waterorder;

/**
 * Created by DBC on 2017-11-20.
 */

public class Water {
    int water_id;
    String water_name;
    int water_size;
    String water_type;
    int water_price;
    String water_useyn;

    public Water(int water_id, String water_name, int water_size, String water_type, int water_price, String water_useyn) {
        this.water_id = water_id;
        this.water_name = water_name;
        this.water_size = water_size;
        this.water_type = water_type;
        this.water_price = water_price;
        this.water_useyn = water_useyn;
    }

    public int getWater_id() {
        return water_id;
    }

    public void setWater_id(int water_id) {
        this.water_id = water_id;
    }

    public String getWater_name() {
        return water_name;
    }

    public void setWater_name(String water_name) {
        this.water_name = water_name;
    }

    public int getWater_size() {
        return water_size;
    }

    public void setWater_size(int water_size) {
        this.water_size = water_size;
    }

    public String getWater_type() {
        return water_type;
    }

    public void setWater_type(String water_type) {
        this.water_type = water_type;
    }

    public int getWater_price() {
        return water_price;
    }

    public void setWater_price(int water_price) {
        this.water_price = water_price;
    }

    public String getWater_useyn() {
        return water_useyn;
    }

    public void setWater_useyn(String water_useyn) {
        this.water_useyn = water_useyn;
    }
}
