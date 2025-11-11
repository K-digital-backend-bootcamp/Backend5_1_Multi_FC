package com.multi.backend5_1_multi_fc.user.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class UserDto {
    private Long user_id;          // PK
    private String username;       // 아이디 (Unique)
    private String password;
    private String nickname;       // 닉네임
    private String profile_image;
    private String address;
    private String email;


    private String level;
    private String position;
    private String gender;

    private Integer login_fail_count;
    private Timestamp locked_until;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String reset_code;
    private Timestamp reset_code_expires;
}