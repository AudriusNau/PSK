package dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoomAvailabilityDTO {

    private Integer officeId;
    private String startDate;
    private String endDate;

}
