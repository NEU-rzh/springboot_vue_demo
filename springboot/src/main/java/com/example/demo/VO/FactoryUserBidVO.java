package com.example.demo.VO;

import com.example.demo.entity.Bid;
import lombok.Data;

@Data
public class FactoryUserBidVO extends Bid {
    String factoryName;
    String uname;
    String tel;
}
