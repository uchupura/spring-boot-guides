### 식별 관계와 비식별 관계
#### 식별관계
부모 테이블의 기본키를 내려받아서 자식 테이블의 기본키 + 외래키로 사용하는 관계
```sql
CREATE TABLE parent(
    parent_id integer,
    PRIMARY KEY(parent_id)
)

CREATE TABLE child(
    parent_id integer,
    child_id integer,
    PRIMARY KEY(parent_id, child_id),
    FOREIGN KEY(parent_id) REFERENCES parent(parent_id)
)
```
```java
@Getter
@Entity
public class Parent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;
}

@Getter
@Entity
@NoArgsConstructor
public class Child {
    @EmbeddedId
    private ChildId childId = new ChildId();

    @MapsId("test")
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Builder
    private Child(Parent parent) {
        this.parent = parent;
    }
    /**
     * 생성 메서드
     */
    public static Child createChild(Parent parent) {
        Child child = Child.builder()
                .parent(parent)
                .build();
        child.getChildId().setChildId(2l);
        return child;
    }
}

@Getter @Setter
@EqualsAndHashCode
@Embeddable
public class ChildId implements Serializable {
    private Long test;
    private Long childId;
}
```
* @MapsId에는 복합키 클래스의 멤버 변수명이 들어간다.
* **복합키의 경우 @GeneratedValue (자동생성) 기능을 사용할 수 없다.**

#### 비식별 관계
* 부모 테이블의 기본키를 내려받아서 자식 테이블의 외래키로만 사용하는 관계
    * 필수적 비식별 관계 : FK NOT NULL(INNER JOIN 사용됨)
    * 선택적 비식별 관계 : FK NULLALBE(OUTER JOIN 사용됨)
```sql
CREATE TABLE parent(
    parent_id integer,
    PRIMARY KEY(parent_id)
)

CREATE TABLE child(
    parent_id integer,
    child_id integer,
    PRIMARY KEY(child_id),
    FOREIGN KEY(parent_id) REFERENCES parent(parent_id)
)
```