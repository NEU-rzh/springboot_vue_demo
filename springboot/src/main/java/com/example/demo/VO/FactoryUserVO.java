package com.example.demo.VO;


import com.example.demo.entity.Factory;
import lombok.Data;

@Data
public class FactoryUserVO extends Factory {
    private int userId;
    private String usercode;
    private String uname;
    private String tel;
}
