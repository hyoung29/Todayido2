package com.metamong.todayido.dao;

import com.metamong.todayido.dto.BoardDto;
import com.metamong.todayido.dto.BoardFileDto;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    //전체 게시글 개수 구하는 메소드
    List<BoardDto> selectBoardList(SearchDto sdto);

    //게시글 저장 메소드
    int selectBoardCnt(SearchDto sdto);

    //파일 정보 저장 메소드
    void insertBoard(BoardDto board);

    //게시글 하나만 가져오는 메소드
    void insertFile(BoardFileDto bFile);

    //게시글 번호에 해당하는 파일목록을 가져오는 메소드
    BoardDto selectBoard(int qna_num);

    //게시글 번호에 해당하는 댓글목록을 가져오는 메소드
    List<BoardFileDto> selectFileList(int qna_num);

    //게시글 번호에 해당하는 게시글, 파일목록, 댓글목록 삭제 메소드
    List<ReviewDto> selectReplyList(int qna_num);

    //    게시판 삭제
    void deleteBoard(int qna_num);

    //    파일삭제
    void deleteFiles(int qna_num);

    //    리뷰삭제
    void deleteReplies(int qna_num);

    //파일의 저장 이름 목록 구하는 메소드
    List<String> selectFnameList(int qna_num);

    //수정 시 단독 파일 삭제
    void deleteFile(String sysname);

    //게시글 수정 메소드
    void updateBoard(BoardDto board);

    //댓글 저장 메소드
    ReviewDto selectLastReply(int r_num);
}
