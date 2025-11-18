package com.multi.backend5_1_multi_fc.mypage.mapper;

import com.multi.backend5_1_multi_fc.mypage.dto.MyPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyPageMapper {

    // username -> 전체 정보 + userId (초기 1회 조회용)
    MyPageDto findByUsername(@Param("username") String username);

    // userId 기준 현재 비밀번호 조회
    String findPasswordByUserId(@Param("userId") Long userId);

    // userId 기준 개인정보 조회 (원하면 사용)
    MyPageDto findByUserId(@Param("userId") Long userId);

    // userId 기준 개인정보 수정
    int updateProfileByUserId(@Param("userId") Long userId,
                              @Param("request") MyPageDto.UpdateProfileRequest request);

    // userId 기준 비밀번호 변경
    int updatePasswordByUserId(@Param("userId") Long userId,
                               @Param("encodedPassword") String encodedPassword);
}
