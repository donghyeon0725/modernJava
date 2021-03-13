package app.reflection;

import app.annotation.NotNull;
import app.annotation.Repeat;
import app.annotation.Validation;

// 유효성 검사 대상 클래스
@Validation
public class UserVO {
    // 이 값은 빈 값일 수 없습니다.
    @NotNull
    private String id;
    @NotNull
    private String name;

    private int book = 0;

    public UserVO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserVO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    // 이 메소드는 실행시 2번 실행됩니다.
    @Repeat(count = 2)
    public void purchaseBook() {
        this.book += 1;
    }
}
