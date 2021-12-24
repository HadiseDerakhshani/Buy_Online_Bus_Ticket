package service;

import dao.AddressDao;
import model.Address;

import java.util.List;

public class AddressService {
    AddressDao addressDao = new AddressDao();

    public void save(List<Address> addressList) {
        addressDao.save(addressList);
    }
}
