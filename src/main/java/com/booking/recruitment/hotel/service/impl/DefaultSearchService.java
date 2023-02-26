package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.CityService;
import com.booking.recruitment.hotel.service.HotelService;
import com.booking.recruitment.hotel.service.SearchService;
import com.booking.recruitment.hotel.util.DistanceCalc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSearchService implements SearchService {

    private final HotelService hotelService;
    private final CityService cityService;

    @Autowired
    DefaultSearchService(HotelService hotelService, CityService cityService) {
        this.hotelService = hotelService;
        this.cityService = cityService;
    }

    @Override
    public List<Hotel> getClosestHotel(Long cityId) {
        List<Hotel> hotel = hotelService.findHotelByCityId(cityId);
        City city = cityService.getCityById(cityId);

        hotel.stream().sorted(Comparator.comparing((Hotel value) -> DistanceCalc.haversine(city.getCityCentreLatitude(), city.getCityCentreLongitude(), value.getLatitude(),
                value.getLongitude())).reversed()).collect(Collectors.toList());

        return hotel;
        //DistanceCalc.haversine(city.getCityCentreLatitude(),city.getCityCentreLongitude(),)
    }
}
