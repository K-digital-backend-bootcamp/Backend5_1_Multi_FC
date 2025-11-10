package com.multi.backend5_1_multi_fc.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private String lastCheckedCommentId;
    private String address;
    private String level;
}
