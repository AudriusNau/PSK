package dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeTravelDTO {

    private Integer employeeId;
    private Integer travelId;
    private Integer flightId;
    private Integer carRentId;
    private Integer roomId;
    private Integer status;

}
