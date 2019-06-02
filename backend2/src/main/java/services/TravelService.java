package services;

import dto.MergeTravelsDTO;
import dto.TravelDTO;
import entities.Employee;
import entities.EmployeeTravel;
import entities.Office;
import entities.Travel;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.EmployeeTravelsDAO;
import persistence.EmployeesDAO;
import persistence.OfficesDAO;
import persistence.TravelsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@DevbridgeInterceptor
public class TravelService {

    @Inject
    @Setter
    @Getter
    private TravelsDAO travelsDAO;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Inject
    @Setter
    @Getter
    private EmployeeTravelsDAO employeeTravelsDAO;

    @Inject
    private CalendarService calendarService;

    public List<Travel> getAll(){
        return travelsDAO.loadAll();
    }

    public Travel getById(Integer id){
        return travelsDAO.findOne(id);
    }

    public List<Travel> getByDepartureOffice(Integer id){
        return travelsDAO.findByDepartureOfficeId(id);
    }

    public List<Travel> getByArrivalOffice(Integer id){
        return travelsDAO.findByArrivalOfficeId(id);
    }

    public List<Travel> getByOrganiserId(Integer id){
        return travelsDAO.findByOrganiserId(id);
    }

    public Office getDepartureOffice(Integer id){
        return (travelsDAO.findOne(id)).getDepartureOffice();
    }

    public Office getArrivalOffice(Integer id){
        return (travelsDAO.findOne(id)).getArrivalOffice();
    }

    public Employee getOrganiser(Integer id){
        return (travelsDAO.findOne(id)).getOrganiser();
    }

    public Travel create(TravelDTO travelDTO){
        Travel travel = travelsDAO.create();
        travel.setStartDate(travelDTO.getStartDate());
        travel.setEndDate(travelDTO.getEndDate());
        travel.setDepartureOffice(officesDAO.findOne(travelDTO.getDepartureOfficeId()));
        travel.setArrivalOffice(officesDAO.findOne(travelDTO.getArrivalOfficeId()));
        travel.setPrice(travelDTO.getPrice());
        travel.setOrganiser(employeesDAO.findOne(travelDTO.getOrganiserId()));
        travelsDAO.persist(travel);
        return travel;
    }

    public Travel update(Integer id, TravelDTO travelDTO){
        Travel travel = travelsDAO.findOne(id);
        if (travel == null){
            throw new IllegalArgumentException("travel id "
                    + id + " not found");
        }
        if (travelDTO.getStartDate() != null) travel.setStartDate(travelDTO.getStartDate());
        if (travelDTO.getEndDate() != null) travel.setEndDate(travelDTO.getEndDate());
        if (travelDTO.getDepartureOfficeId() != null) travel.setDepartureOffice(officesDAO.findOne(travelDTO.getDepartureOfficeId()));
        if (travelDTO.getArrivalOfficeId() != null) travel.setArrivalOffice(officesDAO.findOne(travelDTO.getArrivalOfficeId()));
        if (travelDTO.getOrganiserId() != null) travel.setOrganiser(employeesDAO.findOne(travelDTO.getOrganiserId()));
        if (travelDTO.getPrice() != null) travel.setPrice(travelDTO.getPrice());
        travelsDAO.update(travel);
        return travel;
    }

    public Travel mergeTravels(MergeTravelsDTO mergeTravelsDTO){
        if (!checkDates(mergeTravelsDTO) || !checkOffices(mergeTravelsDTO)) return null;
        else {
            Travel baseTravel = travelsDAO.findOne(mergeTravelsDTO.getBaseTravelId());
            List<EmployeeTravel> employeeTravels;
            for (Integer travelId : mergeTravelsDTO.getTravels()) {
                employeeTravels = employeeTravelsDAO.findByTravelId(travelId);
                for (EmployeeTravel employeeTravel : employeeTravels) {
                    employeeTravel.setTravel(baseTravel);
                    employeeTravel.setStatus(false);
                    employeeTravelsDAO.update(employeeTravel);
                }
                travelsDAO.delete(travelsDAO.findOne(travelId));
            }
            return baseTravel;
        }
    }

    private boolean checkDates(MergeTravelsDTO mergeTravelsDTO)
    {
        try {
            Date baseTravelStartDate = calendarService.parseDate((travelsDAO.findOne(mergeTravelsDTO.getBaseTravelId())).getStartDate());
            Date baseTravelEndDate = calendarService.parseDate((travelsDAO.findOne(mergeTravelsDTO.getBaseTravelId())).getEndDate());
            for (Integer travelId : mergeTravelsDTO.getTravels()){
                if(!checkDate(baseTravelStartDate, calendarService.parseDate(travelsDAO.findOne(travelId).getStartDate())))
                {
                    return false;
                }
                if(!checkDate(baseTravelEndDate, calendarService.parseDate(travelsDAO.findOne(travelId).getEndDate())))
                {
                    return false;
                }
            }
            return true;
        } catch (Exception e){
            System.out.println("wrong date format");
            return false;
        }
    }

    private boolean checkDate(Date baseTravelDate, Date travelDate){
        long difference = travelDate.getTime() - baseTravelDate.getTime();
        return (TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) <= 1) && (TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) >= -1);
    }

    private boolean checkOffices(MergeTravelsDTO mergeTravelsDTO)
    {
        Office baseDepartureOffice = travelsDAO.findOne(mergeTravelsDTO.getBaseTravelId()).getDepartureOffice();
        Office baseArrivalOffice = travelsDAO.findOne(mergeTravelsDTO.getBaseTravelId()).getArrivalOffice();
        for (Integer travelId : mergeTravelsDTO.getTravels()){
            if(!checkOffice(baseDepartureOffice,travelsDAO.findOne(travelId).getDepartureOffice()))
            {
                return false;
            }
            if(!checkOffice(baseArrivalOffice, travelsDAO.findOne(travelId).getArrivalOffice()))
            {
                return false;
            }
        }
        return true;

    }

    private boolean checkOffice(Office baseOffice, Office travelOffice)
    {
        return (baseOffice == travelOffice);
    }

    public void delete(Integer id){
        Travel travel = travelsDAO.findOne(id);
        travelsDAO.delete(travel);
    }
}
