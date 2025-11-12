package com.multi.backend5_1_multi_fc.security;

import com.multi.backend5_1_multi_fc.user.dao.UserDao;
import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao; // UserDao 생성

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDto user = userDao.findByUsername(username);
            return new CustomUserDetails(
                    user.getUserId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getNickname(),
                    user.getProfileImage(),
                    user.getLevel(),
                    user.getPosition(),
                    user.getGender(),
                    user.getLoginFailCount(),
                    user.getLockedUntil(),
                    user.getLastCheckedCommentId(),
                    user.getResetCode()
            );
        } catch (EmptyResultDataAccessException e){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + username, e);
        }
    }
}
