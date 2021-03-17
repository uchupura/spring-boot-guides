package com.guide.oauth2.resource.controller;

import com.guide.oauth2.resource.config.AuthUser;
import com.guide.oauth2.resource.exception.BusinessException;
import com.guide.oauth2.resource.exception.ExceptionType;
import com.guide.oauth2.resource.model.Role;
import com.guide.oauth2.resource.model.User;
import com.guide.oauth2.resource.model.UserDTO;
import com.guide.oauth2.resource.repository.UserRepository;
import com.guide.oauth2.resource.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.URL)
public class UserController {
    public static final String URL = "/v1/users";

    private final TokenStore tokenStore;
    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping
    @Secured({"ROLE_MANAGER"})
    public List<User> findAllUsers(@AuthUser User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
        if (!isEmpty(additionalInfo)) {
            int userId = (int) additionalInfo.get("user_id");
        }


        if (Role.ROLE_SYSTEM.compareTo(Role.ROLE_SUPERVISOR) > 0) {
            System.out.println("system is greater than supervisor");
        }
        return userRepository.findAll();
    }

    @PostMapping
    public UserDTO.Response.User createUser(@RequestBody UserDTO.Request.User body, @AuthenticationPrincipal String username) {
        User user = new User(body.getUid(), body.getPassword(), body.getName(), body.getRole());
        User savedUser = userService.createUser(user);
        return new UserDTO.Response.User(savedUser.getId(), savedUser.getUid(), savedUser.getPassword(), savedUser.getName(), savedUser.getRole());
    }

    @PutMapping
    public UserDTO.Response.User updateUser(@RequestBody UserDTO.Request.User body, @AuthenticationPrincipal String username) {
        User user = new User(body.getUid(), body.getPassword(), body.getName(), body.getRole());
        User savedUser = userService.updateUser(user);
        return new UserDTO.Response.User(savedUser.getId(), savedUser.getUid(), savedUser.getPassword(), savedUser.getName(), savedUser.getRole());
    }

    private Map<String, Object> getAdditionalInfo(Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        if (!isEmpty(details) && !isEmpty(details.getTokenValue())) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
            return accessToken.getAdditionalInformation();
        }
        return null;
    }
}
