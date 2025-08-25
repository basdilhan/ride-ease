package com.rideease.democar_rental.Controller;

import com.rideease.democar_rental.Service.BookingService;
import com.rideease.democar_rental.Service.PaymentService;
import com.rideease.democar_rental.model.Booking;
import com.rideease.democar_rental.model.Car;
import com.rideease.democar_rental.Service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private CarService carService;

    @Autowired
    private BookingService bookingService;
    @Autowired
    private PaymentService paymentService;



    // Shows the main owner dashboard
    @GetMapping("/dashboard")
    public String showOwnerDashboard() {
        return "owner-dashboard";
    }

    // This method displays the "add vehicle" page
    @GetMapping("/add-vehicle")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("car", new Car());
        return "add-Cars";
    }

    // This method handles the form submission for adding a new vehicle
    @PostMapping("/add-vehicle")
    public String addVehicle(@ModelAttribute("car") Car car) {
        carService.saveCar(car);
        return "redirect:/owner/dashboard?carAdded=true";
    }

    // Main method for the manage vehicles page.
    @GetMapping("/manage-vehicles")
    public String showManageVehiclesPage(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("carForm", new Car());

        return "Manage-Vehicle";
    }

    // Prepares the page for editing by reloading it with the selected car's data in the form.
    @GetMapping("/edit-vehicle/{id}")
    public String showEditVehiclePage(@PathVariable Long id, Model model) {
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("carForm", carService.findCarById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id)));
        return "Manage-Vehicle";
    }

    @PostMapping("/save-vehicle")
    public String saveOrUpdateVehicle(@Valid @ModelAttribute("carForm") Car car, BindingResult result, Model model) {

        // If the validation check fails...
        if (result.hasErrors()) {
            // ...send the user back to the form page to see the errors.
            // We also need to send the list of cars again for the table at the bottom.
            model.addAttribute("cars", carService.getAllCars());
            return "Manage-Vehicle";
        }

        // If validation passes, save the car and redirect.
        boolean isNew = car.getCarId() == null;
        carService.saveCar(car);
        return "redirect:/owner/manage-vehicles?" + (isNew ? "added=true" : "updated=true");
    }



    @GetMapping("/bookings")
    public String showBookingsPage(Model model) {
        List<Booking> allBookings = bookingService.getAllBookings();
        model.addAttribute("bookings", allBookings);
        return "View-Bookings"; // Make sure this matches your HTML filename
    }

    @GetMapping("/payments")
    public String showPaymentsPage(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("totalEarnings", paymentService.calculateTotalEarnings());
        model.addAttribute("completedTransactions", paymentService.countCompletedTransactions());
        return "Pay-Detail";
    }
}