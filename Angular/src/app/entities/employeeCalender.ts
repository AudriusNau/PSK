import { Employee } from './employee';

export interface EmployeeCalendar {
    calendar: Calendar
    employee: Employee;
    id: number;
}

interface Calendar {
    date: string;
}