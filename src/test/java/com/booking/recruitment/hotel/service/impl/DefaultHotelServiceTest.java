package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultHotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private DefaultHotelService hotelService;

    private Hotel hotel1;
    private Hotel hotel2;

    @BeforeEach
    void setUp() {
        hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setDeleted(false);

        hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setDeleted(true);
    }

    @Test
    void testGetAllHotels() {
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));

        List<Hotel> hotels = hotelService.getAllHotels();

        assertEquals(2, hotels.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void testGetHotelsByCity() {
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));
        hotel1.setCity(City.builder().setId(1L).setName("Search Test City")
                .setCityCentreLatitude(52.368780)
                .setCityCentreLongitude(4.903303)
                .build());
        hotel2.setCity(City.builder().setId(2L).setName("Search Test City")
                .setCityCentreLatitude(52.344687)
                .setCityCentreLongitude(4.9084303)
                .build());

        List<Hotel> hotels = hotelService.getHotelsByCity(1L);

        assertEquals(1, hotels.size());
        assertEquals(hotel1, hotels.get(0));
    }

    @Test
    void testCreateNewHotel() {
        Hotel newHotel = new Hotel();
        when(hotelRepository.save(newHotel)).thenReturn(hotel1);

        Hotel createdHotel = hotelService.createNewHotel(newHotel);

        assertEquals(hotel1, createdHotel);
        verify(hotelRepository, times(1)).save(newHotel);
    }

    @Test
    void testCreateNewHotel_WithId() {
        Hotel newHotel = new Hotel();
        newHotel.setId(1L);

        assertThrows(BadRequestException.class, () -> hotelService.createNewHotel(newHotel));
    }

    @Test
    void testGetHotel() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel1));

        Optional<Hotel> foundHotel = hotelService.getHotel(1L);

        assertTrue(foundHotel.isPresent());
        assertEquals(hotel1, foundHotel.get());
    }

    @Test
    void testGetHotel_Deleted() {
        when(hotelRepository.findById(2L)).thenReturn(Optional.of(hotel2));

        Optional<Hotel> foundHotel = hotelService.getHotel(2L);

        assertFalse(foundHotel.isPresent());
    }

    @Test
    void testDeleteHotel() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel1));

        hotelService.deleteHotel(1L);

        assertTrue(hotel1.isDeleted());
        verify(hotelRepository, times(1)).save(hotel1);
    }

    @Test
    void testFindHotelByCityId() {
        when(hotelRepository.findHotelByCityId(1L)).thenReturn(Collections.singletonList(hotel1));

        List<Hotel> hotels = hotelService.findHotelByCityId(1L);

        assertEquals(1, hotels.size());
        assertEquals(hotel1, hotels.get(0));
    }
}