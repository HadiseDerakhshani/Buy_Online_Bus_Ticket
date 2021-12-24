package service;

import dao.TripDao;
import dto.TripDto;
import lombok.Data;
import model.Trip;
import model.enums.BusType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Data
public class TripService {
    static final TripDao tripDao = new TripDao();
    Scanner scanner = new Scanner(System.in);

    public void save(List<Trip> tripList) {
        tripDao.save(tripList);
    }

    public List<TripDto> searchTrip(String info, int firstResult, int numResult) throws ParseException {//scanner & Exception & sout

        String[] input = info.split(",");

        return tripDao.searchTrip(input[0], input[1]
                , new SimpleDateFormat("yyyy/mm/dd").parse(input[2]), firstResult, numResult);

    }


    public Trip findById(int id) {
        return tripDao.findById(id);
    }

    public List<TripDto> filter(List<TripDto> list, String company, String busType, int price1, int price2) {

        if (company != null) {
            list = list.stream().filter(i -> i.getCompany().equals(company)).collect(Collectors.toList());
        } else if (busType != null) {
            list = list.stream().filter(i -> i.getBusType().equals(BusType.valueOf(busType.toUpperCase()))).collect(Collectors.toList());
        } else if (price1 != 0 && price2 != 0) {
            list = list.stream().filter(i -> (i.getPrice() >= price1 && i.getPrice() < price2)).collect(Collectors.toList());
        }

        return null;

    }

    public void update(Trip trip) {
        tripDao.update(trip);
    }
}
