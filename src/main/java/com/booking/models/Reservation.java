package com.booking.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workstage;
    };

    public double calculateReservationPrice(){
        double totalPrice = 0;
        for (Service service : services) {
            totalPrice += service.getPrice();
        }
        if (customer.getMember() != null) {
            switch (customer.getMember().getMembershipName().toLowerCase()) {
                case "silver":
                    totalPrice *= 0.95;
                    break;
                case "gold":
                    totalPrice *= 0.9;
                    break;
                default:
                    break;
            }
        }
        return totalPrice;
    }
}
