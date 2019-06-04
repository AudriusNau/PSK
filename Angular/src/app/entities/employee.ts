export interface Employee {
  firstName: string;
  lastName: string;
  role: string;
  id?: number;
  username: string;

  password?: string;
  ticketsNeeded?: boolean;
  carNeeded?: boolean;
}
