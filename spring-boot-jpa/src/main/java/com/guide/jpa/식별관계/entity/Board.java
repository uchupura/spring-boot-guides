package com.guide.jpa.식별관계.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;

    @Builder
    private Board(String title) {
        this.title = title;
    }

    public static Board createBoard(String title) {
        Board board = Board.builder()
                .title(title)
                .build();
        return board;
    }

    public void setBoardDetail(BoardDetail boardDetail) {
        this.boardDetail = boardDetail;
        boardDetail.setBoard(this);
    }
}