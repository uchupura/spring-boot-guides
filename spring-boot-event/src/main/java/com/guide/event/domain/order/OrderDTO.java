package com.guide.event.domain.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderDTO {;

    /*private interface id {
        @Null(groups = OnCreate.class)
        @NotNull(groups = OnUpdate.class)
        Long getId();
    }
    private interface code { String getCode(); }
    private interface count { Long getCount(); }
    private interface price { Long getPrice(); }
    private interface email { @Email String getEmail(); }
    private interface passwords { @Password String getPassword(); }
    private interface phone { @Phone String getPhone(); }

    interface OnCreate{}
    interface OnUpdate{}*/
    public static class Request {
        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            private Long memberId;
            private String itemName;
            private int price;
            private int count;

            @Builder
            public Create(Long memberId, String itemName, int price, int count) {
                this.memberId = memberId;
                this.itemName = itemName;
                this.price = price;
                this.count = count;
            }
        }
    }

    public static class Response {
        @Getter @Setter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            private Long id;
            private Long memberId;
            private String itemName;
            private int price;
            private int count;
            private int totalPrice;
        }
    }

}
