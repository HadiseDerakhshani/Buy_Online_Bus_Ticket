package service;

import dao.TripDao;
import dto.TripDto;
import lombok.Data;
import model.Bus;
import model.Trip;
import model.enums.BusType;
import org.hibernate.Criteria;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

@Data
public class TripService {
    static final TripDao tripDao = new TripDao();
    Scanner scanner = new Scanner(System.in);
    int selectPage = 0;
    int numberTicket;
    int numPage;
    Criteria criteriaFirst;

    public List<TripDto> searchTrip(String info, int numResult) throws ParseException {//scanner & Exception & sout

        String[] input = info.split(",");
        List<TripDto> tripDtoList = tripDao.searchTrip(input[0], input[1]
                , new SimpleDateFormat("yyyy/mm/dd").parse(input[2]), numResult);
        numPage = tripDao.getNumPage();
        return tripDtoList;

    }

    public List<TripDto> pageResult(int firstResult, int numResult) {

        List<TripDto> list = tripDao.pageResult(firstResult, numResult);
        return list;
    }

    public Trip findById(int id) {
        return tripDao.findById(id);
    }

    public List<TripDto> filter(String company, BusType busType, int price1, int price2) {
        List<TripDto> filter = tripDao.filter(criteriaFirst, scanner.next(), null, 0, 0);
        return filter;
    }
    public void update(Trip trip) {
       tripDao.update(trip);
    }
}
