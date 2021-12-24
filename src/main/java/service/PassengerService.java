package service;

import dao.PassengerDao;
import model.Passenger;

public class PassengerService {
    PassengerDao passengerDao = new PassengerDao();

    public void save(Passenger passenger) {
        passengerDao.save(passenger);
    }
}
