package com.example.demo.entity;

import lombok.Data;

@Data
public class ListEntity {
    private int friendUid;
    private String username;
    private String remark;
    private String relationship;
    private int status;
    private int userUid;
    private int isFriend;
}
