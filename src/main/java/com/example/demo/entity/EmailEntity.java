package com.example.demo.entity;

import lombok.Data;

@Data
public class EmailEntity {
    int id;
    int senderUid;
    int receiverUid;
    String title;
    String content;
    String sendTime;
    int isRead;
    int groupId;
    int canSee;
}