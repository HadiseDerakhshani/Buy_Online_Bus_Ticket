package service;

import dao.BusDao;
import model.Bus;

import java.util.List;

public class BusService {
    BusDao busDao = new BusDao();

    public void save(List<Bus> busList) {
        busDao.saveBus(busList);
    }
}
