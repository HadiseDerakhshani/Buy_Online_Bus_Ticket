package service;

import dao.DriverDao;
import model.Driver;

import java.util.List;

public class DriverService {
    DriverDao driverDao = new DriverDao();

    public void save(List<Driver> driverList) {
        driverDao.save(driverList);
    }
}
