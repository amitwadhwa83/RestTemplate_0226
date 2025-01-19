package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.CityService;
import com.booking.recruitment.hotel.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultSearchServiceTest {

    @Mock
    private HotelService hotelService;

    @Mock
    private CityService cityService;

    @InjectMocks
    private DefaultSearchService searchService;

    private City city;
    private Hotel hotel1;
    private Hotel hotel2;
    private Hotel hotel3;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(1L);
        city.setCityCentreLatitude(10.0);
        city.setCityCentreLongitude(10.0);

        hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setLatitude(10.1);
        hotel1.setLongitude(10.1);

        hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setLatitude(10.2);
        hotel2.setLongitude(10.2);

        hotel3 = new Hotel();
        hotel3.setId(3L);
        hotel3.setLatitude(10.3);
        hotel3.setLongitude(10.3);
    }

    @Test
    void testGetClosestHotel() {
        when(cityService.getCityById(1L)).thenReturn(city);
        when(hotelService.findHotelByCityId(1L)).thenReturn(Arrays.asList(hotel1, hotel2, hotel3));

        List<Hotel> closestHotels = searchService.getClosestHotel(1L);

        assertEquals(3, closestHotels.size());
        assertEquals(hotel1, closestHotels.get(0));
        assertEquals(hotel2, closestHotels.get(1));
        assertEquals(hotel3, closestHotels.get(2));
    }

    @Test
    void testGetClosestHotel_LimitToThree() {
        Hotel hotel4 = new Hotel();
        hotel4.setId(4L);
        hotel4.setLatitude(10.4);
        hotel4.setLongitude(10.4);

        when(cityService.getCityById(1L)).thenReturn(city);
        when(hotelService.findHotelByCityId(1L)).thenReturn(Arrays.asList(hotel1, hotel2, hotel3, hotel4));

        List<Hotel> closestHotels = searchService.getClosestHotel(1L);

        assertEquals(3, closestHotels.size());
    }
}
