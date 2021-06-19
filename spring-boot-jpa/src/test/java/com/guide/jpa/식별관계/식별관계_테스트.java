package com.guide.jpa.식별관계;

import com.guide.jpa.식별관계.entity.*;
import com.guide.jpa.식별관계.repository.BoardDetailRepository;
import com.guide.jpa.식별관계.repository.BoardRepository;
import com.guide.jpa.식별관계.repository.ChildRepository;
import com.guide.jpa.식별관계.repository.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static com.guide.jpa.식별관계.entity.Board.createBoard;
import static com.guide.jpa.식별관계.entity.BoardDetail.createBoardDetail;
import static com.guide.jpa.식별관계.entity.Child.createChild;

//@Rollback(value = false)
@EnableJpaRepositories(basePackages = "com.guide.jpa.식별관계")
@Transactional
@SpringBootTest
class 식별관계_테스트 {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardDetailRepository boardDetailRepository;

    @BeforeEach
    void init(TestInfo testInfo) {
        if (testInfo.getDisplayName().equals("식별관계테스트()")) {
            Parent parent = new Parent();
            parentRepository.save(parent);

            Child child = createChild(parent);
            childRepository.save(child);
        }

    }

    @Test
    void 식별관계테스트() {
        Child child = childRepository.findById(new ChildId(1l, 2l)).orElse(null);
        System.out.println(child.getChildId().getTest() + " / " + child.getChildId().getChildId());
    }

    @Test
    void 동등비교테스트() {
        ChildId id1 = new ChildId(1l, 2l);
        ChildId id2 = new ChildId(1l, 2l);
        System.out.println(id1.equals(id2));
    }

    @Test
    void 보드생성_테스트() {
        Board board = createBoard("보드");
        boardRepository.save(board);

        BoardDetail boardDetail = createBoardDetail("컨텐츠", board);
        boardDetailRepository.save(boardDetail);
    }
}
