//package com.guide.openfeign.global.config;
//
//import feign.RequestTemplate;
//import feign.codec.EncodeException;
//import feign.codec.Encoder;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.cloud.openfeign.support.SpringEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//
//@Configuration
//public class FeignPageableConfiguration {
//    @Autowired
//    private ObjectFactory<HttpMessageConverters> messageConverters;
//
//    @Bean
//    public Encoder feignEncoder() {
//        return new PageableQueryEncoder(new SpringEncoder(messageConverters));
//    }
//
//    static class PageableQueryEncoder implements Encoder {
//
//        private final Encoder delegate;
//
//        PageableQueryEncoder(Encoder delegate){
//            this.delegate = delegate;
//        }
//
//        @Override
//        public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
//            if(object instanceof Pageable){
//                Pageable pageable = (Pageable)object;
//                template.query("page", pageable.getPageNumber() + "");
//                template.query("size", pageable.getPageSize() + "");
//
//                if(pageable.getSort() != null) {
//                    Collection<String> existingSorts = template.queries().get("sort");
//                    List<String> sortQueries  = existingSorts != null ? new ArrayList<>(existingSorts) : new ArrayList<>();
//                    for (Sort.Order order : pageable.getSort()) {
//                        sortQueries.add(order.getProperty() + "," + order.getDirection());
//                    }
//                    template.query("sort", sortQueries);
//                }
//
//            }else{
//                delegate.encode(object, bodyType, template);
//            }
//        }
//    }
//}
