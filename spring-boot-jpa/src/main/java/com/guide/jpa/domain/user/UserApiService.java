package com.guide.jpa.domain.user;

import com.guide.jpa.domain.user.dto.UserDTO;
import com.guide.jpa.domain.user.entity.User;
import com.guide.jpa.domain.user.entity.UserId;
import com.guide.jpa.domain.user.entity.UserName;
import com.guide.jpa.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserApiService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public UserDTO.Response.Create register(UserDTO.Request.Create body) {
        User user = new User(new UserName(body.getName()));

        /*if (userService.exist(user)) {
            throw new IllegalArgumentException("이미 등록된 사용자임");
        }*/

        userRepository.save(user);

        return new UserDTO.Response.Create(user.getId().getValue(), user.getName().getValue());
    }

    public UserDTO.Response.User get(String userId) {
        //UserDTO.Response.User result = new UserDTO.Response.User();

        UserId targetId = new UserId(userId);
        Optional<User> user = userRepository.findById(targetId);
        if (user.isPresent()) {
            return new UserDTO.Response.User(user.get());
        }
        /*user.ifPresent(u -> {
            result.setId(u.getId().getValue());
            result.setName(u.getName().getValue());
        });*/

        //return result;
        return null;
    }

    public UserDTO.Response.Update update(UserDTO.Request.Update body) {
        UserId userId = new UserId(body.getId());
        Optional<User> optUser = userRepository.findById(userId);
        User user = optUser.orElseThrow(() -> new RuntimeException("Not found"));

        UserName newUserName = new UserName(body.getName());
        user.changeName(newUserName);
        if (userService.exist(user)) {
            throw new RuntimeException("이미 존재하는 사용자명임");
        }
        return new UserDTO.Response.Update(user);
    }
}
