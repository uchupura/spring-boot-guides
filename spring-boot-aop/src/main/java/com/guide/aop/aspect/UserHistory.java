package com.guide.aop.aspect;

import com.guide.aop.domain.History;
import com.guide.aop.domain.User;
import com.guide.aop.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class UserHistory {
    private final HistoryRepository historyRepository;

    /*@Pointcut("execution(* com.guide.aop.service.UserService.update(*)) && args(user)")
    public void updateUser(User user){}

    @AfterReturning("updateUser(user)")
    public void saveHistory(User user){
        historyRepository.save(new History(user.getIdx(), LocalDateTime.now()));
    }*/

    @Pointcut("execution(* com.guide.aop.service.UserService.update(*)) && args(user)")
    public void updateUser(User user){}

    @AfterReturning(value = "updateUser(user)", returning = "returnValue")
    public void saveHistory(User user, Object returnValue){
        System.out.println(((User)returnValue).getName());
        historyRepository.save(new History(user.getIdx(), LocalDateTime.now()));
    }

    @Pointcut("execution(* com.guide.aop.service.UserService.delete(*)) && args(id)")
    public void deleteUser(Long id){}

    @AfterThrowing(value = "deleteUser(id)", throwing = "e")
    public void deleteUsers(Long id, Exception e){
        System.out.println(e.getMessage());
    }
}
