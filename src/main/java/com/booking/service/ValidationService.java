package com.booking.service;

import com.booking.models.Person;

import java.util.List;

public class ValidationService {

    public static boolean isValidCustomerId(String customerId, List<Person> personList) {
        for (Person person : personList) {
            if (person instanceof com.booking.models.Customer && person.getId().equals(customerId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidEmployeeId(String employeeId, List<Person> personList) {
        for (Person person : personList) {
            if (person instanceof com.booking.models.Employee && person.getId().equals(employeeId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidServiceId(String serviceId, List<com.booking.models.Service> serviceList) {
        for (com.booking.models.Service service : serviceList) {
            if (service.getServiceId().equals(serviceId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidReservationId(String reservationId, List<com.booking.models.Reservation> reservationList) {
        for (com.booking.models.Reservation reservation : reservationList) {
            if (reservation.getReservationId().equals(reservationId)) {
                return true;
            }
        }
        return false;
    }

    public static Person getPersonById(String id, List<Person> personList) {
        for (Person person : personList) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }
}
