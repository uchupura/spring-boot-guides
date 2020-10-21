package com.guide.oauth2.config.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
public final class PostPageableArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String JSON_BODY = "JSON_REQUEST_BODY";
    private final String PAGEABLE = "pageable";
    private final String SIZE = "size";
    private final String PAGE = "page";
    private final String SORT = "sort";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(PostPageable.class);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter,
                                    ModelAndViewContainer modelAndViewContainer,
                                    NativeWebRequest nativeWebRequest,
                                    WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String) servletRequest.getAttribute(JSON_BODY);
        if (jsonBody == null) {
            jsonBody = CharStreams.toString(servletRequest.getReader());
            servletRequest.setAttribute(JSON_BODY, jsonBody);
        }

        JsonNode jsonNode = objectMapper.readTree(jsonBody);
        JsonNode pageableJsonNode = jsonNode.get(PAGEABLE);
        Integer size = null;
        Integer page = null;
        String[] sortArray = null;
        if (pageableJsonNode != null) {
            JsonNode sizeJsonNode = pageableJsonNode.get(SIZE);
            JsonNode pageJsonNode = pageableJsonNode.get(PAGE);
            JsonNode sortJsonNode = pageableJsonNode.get(SORT);
            if (sizeJsonNode != null) {
                size = sizeJsonNode.intValue();
            }
            if (pageJsonNode != null) {
                page = pageJsonNode.intValue();
            }
            if (sortJsonNode != null) {
                sortArray = objectMapper.convertValue(sortJsonNode, String[].class);
            }
        }

        PostPageable defaults = methodParameter.getParameterAnnotation(PostPageable.class);
        size = (size == null ? defaults.size() : size);
        page = (page == null ? defaults.page() : page - 1);
        page = page < 0 ? 0 : page;
        Sort sort = null;
        if (sortArray != null) {
            sort = parseParameterIntoSort(sortArray, ",");
            return PageRequest.of(page, size, sort);
        } else {
            return PageRequest.of(page, size);
        }
    }

    Sort parseParameterIntoSort(String[] source, String delimiter) {

        List<Sort.Order> allOrders = new ArrayList<Sort.Order>();

        for (String part : source) {
            if (part == null) continue;

            String[] elements = part.split(delimiter);
            Sort.Direction direction = elements.length == 0 ? null : Sort.Direction.fromOptionalString(elements[elements.length - 1]).orElse(null);

            for (int i = 0; i < elements.length; i++) {
                if (i == elements.length - 1 && direction != null) continue;

                String property = elements[i];
                if (!hasText(property)) {
                    continue;
                }

                allOrders.add(new Sort.Order(direction, property));
            }
        }

        return allOrders.isEmpty() ? null : Sort.by(allOrders);
    }
}