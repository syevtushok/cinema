package mate.academy.cinema.dto.request;

import javax.validation.constraints.NotEmpty;

public class ShoppingCartRequestDto {
    @NotEmpty
    private Long userId;
    private Long movieSessionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieSessionId() {
        return movieSessionId;
    }

    public void setMovieSessionId(Long movieSessionId) {
        this.movieSessionId = movieSessionId;
    }
}
