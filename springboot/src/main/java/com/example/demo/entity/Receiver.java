package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("receiver_tab")
@Data
public class Receiver {
    @TableId(value="receiver_id",type= IdType.AUTO)
    private int receiverId;
    private String receiver;
    private String receiverTel;
    private String receiverAddress;
}
