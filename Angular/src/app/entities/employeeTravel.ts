import {Employee} from "./employee";
import {Travel} from "./travel";
import {Room} from "./room";

export interface EmployeeTravel {
  id: number;
  employee: Employee;
  travel: Travel;
  room: Room;
  status: boolean;
}
