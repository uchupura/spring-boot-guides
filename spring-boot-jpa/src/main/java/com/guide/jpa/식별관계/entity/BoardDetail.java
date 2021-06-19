package com.guide.jpa.식별관계.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class BoardDetail {
    @Id
    private Long boardId;

    @Lob
    private String content;

    @MapsId("boardId")
    @OneToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private BoardDetail(String content, Board board) {
        this.content = content;
        this.board = board;
    }

    public static BoardDetail createBoardDetail(String content, Board board) {
        BoardDetail boardDetail = BoardDetail.builder()
                .content(content)
                .board(board)
                .build();
        return boardDetail;
    }

    /**
     * 연관관계 메소드
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}