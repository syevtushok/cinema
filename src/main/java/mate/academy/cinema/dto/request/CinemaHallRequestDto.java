package mate.academy.cinema.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CinemaHallRequestDto {
    @Min(5)
    private int capacity;
    @NotEmpty
    private String description;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
