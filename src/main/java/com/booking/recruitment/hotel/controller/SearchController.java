package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import com.booking.recruitment.hotel.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
  private final SearchService searchService;

  @Autowired
  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }


  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getClosestHotel(@PathVariable Long id) {
    return searchService.getClosestHotel(id);
  }

  /*@GetMapping("/{cityId}")
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getClosestHotel(@PathVariable Long cityId) {
    return hotelService.getClosestHotel(cityId);
  }*/
}
