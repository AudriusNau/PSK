import {Employee} from "./employee";
import {Travel} from "./travel";
import {Room} from "./room";
import {Flight} from "./flight";
import {CarRent} from "./carRent";
import { Accommodation } from './accommodation';

export interface EmployeeTravel {
  flight: Flight;
  carRent: CarRent;
  id: number;
  employee: Employee;
  travel: Travel;
  room: Room;
  accomodation?: Accommodation;
  status: number;
  departureOffice: String;
  arrivalOffice: String;
}
