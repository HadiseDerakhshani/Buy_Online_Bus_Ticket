package view;

import model.Address;
import model.Bus;
import model.Driver;
import model.Trip;
import model.enums.BusType;
import model.enums.Gender;
import service.AddressService;
import service.BusService;
import service.DriverService;
import service.TripService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InitializationEntity {
    AddressService addressService = new AddressService();
    DriverService driverService = new DriverService();
    BusService busService = new BusService();
    TripService tripService = new TripService();

    public InitializationEntity() throws ParseException {
        initializationAddress();
    }

    public void initializationAddress() throws ParseException {
        List<Address> addressList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            Address address = Address.builder()
                    .city("city" + i)
                    .street("street" + i)
                    .plaque(12345 + 1)
                    .build();
            addressList.add(address);
        }
        addressService.save(addressList);
        initializationDriver(addressList);
    }

    public void initializationDriver(List<Address> addressList) throws ParseException {
        List<Driver> driverList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            String dateContract = ((int) (Math.random() * (1400 - 1350 + 1)) + 1350) + "/" + ((int) (Math.random() * (12 - 1 + 1)) + 1) + "/1" + i;
            String dateBirth = ((int) (Math.random() * (1370 - 1330 + 1)) + 1330) + "/" + ((int) (Math.random() * (12 - 1 + 1)) + 1) + "/1" + i;
            Driver driver = new Driver();
            driver.setBirthDate(new SimpleDateFormat("yyyy/mm/dd").parse(dateContract));
            driver.setBeginContract(new SimpleDateFormat("yyyy/mm/dd").parse(dateContract));
            driver.setAge((int) (Math.random() * (60 - 30 + 1) + 30));
            driver.setGender(Gender.MALE);
            driver.setFamily("family" + i);
            driver.setName("driver" + i);
            driver.setNationalCode(String.valueOf(Math.random() * (1000000000 - 1111111111) + 1111111111));
            driver.setPhoneNumber("0912345678" + i);
            driver.setAddress(addressList.get(i - 1));
            driverList.add(driver);
        }
        driverService.save(driverList);
        initializationBus(driverList);
    }

    public void initializationBus(List<Driver> driverList) {
        List<Bus> busList = new ArrayList<>();
        BusType type;
        String name;
        int seat;
        for (int i = 1; i < 5; i++) {
            if (i % 2 == 0) {
                type = BusType.VIP;
                name = "safir";
                seat = 30;
            } else {
                type = BusType.NORMAL;
                name = "hasafar";
                seat = 45;
            }
            Bus bus = Bus.builder()
                    .busType(type)
                    .plaque(((int) (Math.random() * (987689 - 123456 + 1)) + 123456))
                    .company(name)
                    .build();
            for (int j = 0; j < i; j++)
                bus.getDriverList().add(driverList.get(j));
            busList.add(bus);
        }
        busService.save(busList);
    }

    public void initializationTrip(List<Bus> busList) throws ParseException {
        List<Trip> tripList = new ArrayList<>();
        int price, seat, reserve;
        for (int i = 1; i < 5; i++) {
            if (busList.get(i - 1).getBusType().equals(BusType.VIP.toString())) {
                price = 50000;
            } else {
                price = 35000;
            }
            seat = busList.get(i).getNumOfSeat();
            reserve = seat - (i * 2);
            String date = ((int) (Math.random() * (1401 - 1400 + 1)) + 1400) + "/" + ((int) (Math.random() * (12 - 1 + 1)) + 1) + "/" + ((int) (Math.random() * (29 - 1 + 1)) + 1);
            String time = ((int) (Math.random() * (24 - 1 + 1)) + 1) + ":" + ((int) (Math.random() * (60 - 1 + 1)) + 1) + ":" + ((int) (Math.random() * (60 - 1 + 1)) + 1);
            Trip trip = Trip.builder()
                    .origin("origin" + i)
                    .destination("dest" + i)
                    .buses(busList.get(i - 1))
                    .moveDate(new SimpleDateFormat("yyyy/mm/dd").parse(date))
                    .moveDate(new SimpleDateFormat("HH:mm:ss").parse(time))
                    .price(price + (i * 3000))
                    .numOfEmpty(seat - (i * 2))
                    .numOfReserve(reserve)
                    .build();
            tripList.add(trip);
        }
        tripService.save(tripList);
    }
}
