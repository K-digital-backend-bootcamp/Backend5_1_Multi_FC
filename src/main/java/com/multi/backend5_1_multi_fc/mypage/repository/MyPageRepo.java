package com.multi.backend5_1_multi_fc.mypage.repository;

import com.multi.backend5_1_multi_fc.mypage.dao.MyPageDao;
import com.multi.backend5_1_multi_fc.mypage.dto.MyPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyPageRepo {

    private final MyPageDao myPageDao;

    public MyPageDto findByUsername(String username) {
        return myPageDao.findByUsername(username);
    }

    public MyPageDto findByUserId(Long userId) {
        return myPageDao.findByUserId(userId);
    }

    public String findPasswordByUserId(Long userId) {
        return myPageDao.findPasswordByUserId(userId);
    }

    public void updateProfileByUserId(Long userId, MyPageDto.UpdateProfileRequest request) {
        myPageDao.updateProfileByUserId(userId, request);
    }

    public void updatePasswordByUserId(Long userId, String encodedPassword) {
        myPageDao.updatePasswordByUserId(userId, encodedPassword);
    }
}
