package com.multi.backend5_1_multi_fc.community.mapper;

import com.multi.backend5_1_multi_fc.community.dto.CommunityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {
    // username → userId 조회
    Long findUserIdByUsername(@Param("username") String username);

    // 글 작성
    void insertPost(@Param("userId")Long userId,
                    @Param("req")CommunityDto.PostCreateRequest req,
                    @Param("imageUrl") String imageUrl);

    // 카테고리별 목록
    List<CommunityDto.PostListResponse> findPostByCategory(
            @Param("category") String category
    );

    // 게시글 상세
    CommunityDto.PostDetailResponse findPostDetail(
            @Param("postId") Long postId
    );

    // 게시글 수정
    int updatePostByWriter(@Param("postId")Long postId,
                           @Param("userId") Long userId,
                           @Param("req") CommunityDto.PostUpdateRequest req);

    // 게시글 삭제
    int deletePostByWriter(@Param("postId") Long postId,
                           @Param("userId") Long userId);

    // 조회수 증가
    void increaseViewCount(@Param("postId") Long postId);

    // 게시글 작성자 찾기
    Long findWriterIdByPostId(@Param("postId") Long postId);

    // current_comment_id 업데이트
    void updatePostCurrentCommentId(@Param("postId") Long postId,
                                    @Param("commentId") Long commentId);

    // last_checked_comment_id 조회
    Long findLastCheckedCommentId(@Param("postId") Long postId);

    // current_comment_id 조회
    Long findCurrentCommentId(@Param("postId") Long postId);

    // last_checked_comment_id 갱신
    void updateLastCheckedCommentId(@Param("postId") Long postId,
                                    @Param("commentId") Long commentId);

    // 페이징 + 검색
    List<CommunityDto.PostListResponse> findPostPageByCategory(
            @Param("category") String category,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword
    );

    // 전체 개수 (페이지 계산용)
    long countPostByCategory(
            @Param("category") String category,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword
    );
}
