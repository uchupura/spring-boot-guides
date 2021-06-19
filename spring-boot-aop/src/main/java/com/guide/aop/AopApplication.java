package com.guide.aop;

import com.guide.aop.domain.Board;
import com.guide.aop.domain.User;
import com.guide.aop.repository.BoardRepository;
import com.guide.aop.repository.UserRepository;
import com.guide.aop.service.BoardService;
import com.guide.aop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class AopApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        for(int i=1;i<=100;i++){
            boardRepository.save(new Board(i+"번째 게시글의 제목", i+"번째 게시글의 내용"));
            userRepository.save(new User(i+"@email.com", i+"번째 사용자"));
        }
    }

    @GetMapping("/boards")
    public List<Board> getBoards() {
        //long start = System.currentTimeMillis();
        List<Board> boards = boardService.getBoards();
        //long end = System.currentTimeMillis();

        //System.out.println("수행 시간 : "+ (end - start));
        return boards;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        //long start = System.currentTimeMillis();
        List<User> users = userService.getUsers();
        //long end = System.currentTimeMillis();

        //System.out.println("수행 시간 : "+ (end - start));
        return users;
    }
}
