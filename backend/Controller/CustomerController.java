package com.rideease.democar_rental.Controller;

import com.rideease.democar_rental.Service.BookingService;
import com.rideease.democar_rental.Service.CarService;
import com.rideease.democar_rental.dto.BookingDto;
import com.rideease.democar_rental.model.Booking;
import com.rideease.democar_rental.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CarService carService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/dashboard")
    public String showCustomerDashboard() {
        return "customer-dashboard";
    }

    @GetMapping("/search-cars")
    public String showSearchCarsPage(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Car> carList = carService.searchCars(keyword);
        model.addAttribute("cars", carList);
        model.addAttribute("keyword", keyword);
        return "search-cars";
    }

    @GetMapping("/create-booking")
    public String showCreateBookingPage(Model model) {
        model.addAttribute("bookingDto", new BookingDto());
        model.addAttribute("cars", carService.getAllCars());
        return "create-booking";
    }

    @PostMapping("/create-booking")
    public String processBooking(@ModelAttribute("bookingDto") BookingDto bookingDto) {
        bookingService.createBooking(bookingDto);
        return "redirect:/customer/my-bookings?success=true";
    }


    @GetMapping("/my-bookings")
    public String showMyBookingsPage(Model model) {
        List<Booking> bookingList = bookingService.findBookingsForCurrentUser();
        model.addAttribute("bookings", bookingList);
        return "my-bookings";
    }
    @PostMapping("/cancel-booking/{id}")
    public String cancelBooking(@PathVariable("id") Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "redirect:/customer/my-bookings?cancelled=true";
    }
}