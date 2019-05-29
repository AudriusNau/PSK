package dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TravelDTO {

    private String startDate;
    private String endDate;
    private Integer departureOfficeId;
    private Integer arrivalOfficeId;
    private Double price;
    private Integer organiserId;

}
