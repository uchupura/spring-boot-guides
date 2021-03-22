package com.guide.jpa.domain.circle;

import com.guide.jpa.domain.user.entity.User;
import com.guide.jpa.domain.user.entity.UserId;
import com.guide.jpa.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CircleApiService {
    private final CircleRepository circleRepository;
    private final UserRepository userRepository;
    private final CircleQueryRepository circleQueryRepository;

    public List<CircleDTO.Response.Circle> getCircles() {
//        List<Circle> circles = circleRepository.findAll();
        List<Circle> circles = circleQueryRepository.findAllCircles();
        return circles.stream().map(c -> {
            return new CircleDTO.Response.Circle(c);
        }).collect(Collectors.toList());

    }

    @Transactional
    public CircleDTO.Response.Create create(CircleDTO.Request.Create body) {
        UserId ownerId = new UserId(body.getUserId());
        Optional<User> optOwner = userRepository.findById(ownerId);
        User owner = optOwner.orElseThrow(() -> new RuntimeException(ownerId + " 서클장이 될 사용자가 없음"));

        CircleName name = new CircleName(body.getName());
        Circle circle = Circle.create(name, owner);

        /*if (circleService.exists(circle)) {
            throw new RuntimeException("이미 등록된 서클임");
        }*/

        Circle savedCircle = circleRepository.save(circle);
        return new CircleDTO.Response.Create(savedCircle);
    }

    @Transactional
    public void join(CircleDTO.Request.Join body) {
        UserId memberId = new UserId(body.getUserId());
        Optional<User> optMember = userRepository.findById(memberId);
        User member = optMember.orElseThrow(() -> new RuntimeException(memberId + " 서클에 가입할 사용자를 찾지 못했음"));

        CircleId id = new CircleId(body.getCircleId());
        Optional<Circle> optCircle = circleRepository.findById(id);
        Circle circle = optCircle.orElseThrow(() -> new RuntimeException(id + " 가입할 서클을 찾지 못했음"));

        /*if (circle.getMembers().size() >= 29) {
            throw new RuntimeException();
        }*/

        member.join(circle);
    }

    @Transactional
    public void delete(String circleId) {
        CircleId id = new CircleId(circleId);
        circleRepository.deleteById(id);
    }
}
