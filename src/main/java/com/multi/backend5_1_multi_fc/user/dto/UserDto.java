package com.multi.backend5_1_multi_fc.user.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class UserDto {
    private Long userId;          // PK
    private Long LastCheckedCommentId;
    private String username;       // 아이디 (Unique)
    private String password;
    private String nickname;       // 닉네임
    private String profileImage;
    private String address;
    private String email;


    private String level;
    private String position;
    private String gender;

    private Integer loginFailCount;
    private Timestamp lockedUntil;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String resetCode;
    private Timestamp resetCodeExpires;
}