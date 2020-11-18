package com.guide.event.domain.order;

import com.guide.event.global.common.ApiResponseCode;
import com.guide.event.global.common.CommonApiResponse;
import com.guide.event.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.guide.event.global.common.CommonApiResponse.success;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(OrderAPI.URL)
public class OrderAPI {

    public static final String URL = "/order";

    private final OrderService orderService;

    @PostMapping
    public CommonApiResponse order(@RequestBody OrderDTO.Request.Create body) {
        return success(orderService.order(body));
    }

    @PostMapping("internal-server-error")
    public CommonApiResponse orderWithInternalServerException(@RequestBody OrderDTO.Request.Create body) {
        Order order = null;
        order.getId();
        return success(orderService.order(body));
    }

    @PostMapping("business-error")
    public CommonApiResponse orderWithBusinessException(@RequestBody OrderDTO.Request.Create body) {
        throw new BusinessException("Exception 테스트", ApiResponseCode.ORDER_QUANTITY_ERROR);
    }
}