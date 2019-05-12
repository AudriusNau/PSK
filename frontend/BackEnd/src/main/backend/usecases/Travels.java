package main.backend.usecases;

import main.backend.entities.Travel;
import main.backend.persistence.TravelsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Model
public class Travels implements Serializable {

    @Inject
    private TravelsDAO travelsDAO;
    private Travel travelToCreate = new Travel();

    private List<Travel> allTravels;
    @PostConstruct
    public void init(){
        loadTravels();
    }
    public void loadTravels() {
        this.allTravels = travelsDAO.loadAll();
    }
    public List<Travel> getAllTravels(){
        return allTravels;
    }
    @Transactional
    public String createTravel(){
        this.travelsDAO.persist(travelToCreate);
        return "success";
    }
    public Travel getTravelToCreate(){
        return travelToCreate;
    }
    public void setTravelToCreate(Travel travelToCreate){
        this.travelToCreate= travelToCreate;
    }
}
