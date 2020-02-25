package mate.academy.cinema.dto.request;

import javax.validation.constraints.NotEmpty;

public class OrderRequestDto {
    @NotEmpty
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
