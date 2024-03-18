package com.example.demo.entity;

import lombok.Data;

@Data
public class GroupEntity {
    private int userUid;
    private int friendUid;
    private String groupName;
    private int status;
}
