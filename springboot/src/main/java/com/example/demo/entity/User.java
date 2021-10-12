package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;


@TableName("user_tab")
@Data
public class User {
    @TableId(value="user_id",type= IdType.AUTO)
    private int userId;
    private String usercode;
    private String pword;
    private String uname;
    private String tel;
    private String urole;
    private String createTime;
    private String updateTime;
}
