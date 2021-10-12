package com.example.demo.VO;

import com.example.demo.entity.Order;
import lombok.Data;

@Data
public class OrderReceiverVO extends Order {
    int receiverID;
    String receiver;
    String receiverTel;
    String receiverAddress;

    int orderId;
    int factoryId;
}
