package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ReservationService {
    private static final Scanner scanner = new Scanner(System.in);
    private static int reservationCounter = 1;

    public static void createReservation(List<Person> personList, List<Service> serviceList, List<Reservation> reservationList) {
        System.out.print("Silahkan Masukkan Customer Id: ");
        String customerId = scanner.next();
        if (!ValidationService.isValidCustomerId(customerId, personList)) {
            System.out.println("Format Customer Id tidak valid atau Customer tidak ditemukan.");
            return;
        }

        System.out.println("No\tID\tNama\t\tHarga");
        for (Person person : personList) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.println(employee.getId() + "\t" + employee.getName() + "\t\t" + employee.getExperience());
            }
        }

        String employeeId;
        Employee employee = null;
        do {
            System.out.print("Silahkan Masukkan Employee Id: ");
            employeeId = scanner.next();
            if (!ValidationService.isValidEmployeeId(employeeId, personList)) {
                System.out.println("Format Employee Id tidak valid atau Employee tidak ditemukan. Silakan coba lagi.");
            } else {
                employee = (Employee) ValidationService.getPersonById(employeeId, personList);
            }
        } while (employee == null);

        System.out.println("No\tID\tNama\t\tHarga");
        for (Service service : serviceList) {
            System.out.println(service.getServiceId() + "\t" + service.getServiceName() + "\t\t" + service.getPrice());
        }

        List<Service> selectedServices = new ArrayList<>();
        Set<String> selectedServiceIds = new HashSet<>();
        
        while (true) {
            System.out.print("Silahkan Masukkan Service Id: ");
            String serviceId = scanner.next();

            if (!ValidationService.isValidServiceId(serviceId, serviceList)) {
                System.out.println("Format Service Id tidak valid atau Service tidak ditemukan.");
                continue;
            }

            if (selectedServiceIds.contains(serviceId)) {
                System.out.println("Layanan dengan ID " + serviceId + " sudah dipilih sebelumnya.");
                continue;
            }
        
            Service selectedService = serviceList.stream()
                    .filter(service -> service.getServiceId().equals(serviceId))
                    .findFirst()
                    .orElse(null);
        
            if (selectedService != null) {
                selectedServices.add(selectedService);
                selectedServiceIds.add(serviceId);
                System.out.println("Service " + selectedService.getServiceName() + " berhasil ditambahkan.");
            } else {
                System.out.println("Service tidak ditemukan.");
            }
        
            System.out.print("Ingin pilih service yang lain (Y/T)? ");
            String choice = scanner.next();
            if (!choice.equalsIgnoreCase("Y")) {
                break;
            }
        }

        Reservation reservation = new Reservation();
        reservation.setReservationId(generateReservationId());
        reservation.setCustomer((Customer) ValidationService.getPersonById(customerId, personList));
        reservation.setEmployee((Employee) ValidationService.getPersonById(employeeId, personList));
        reservation.setServices(selectedServices);
        reservation.setWorkstage("In Process");

        double totalBookingCost = selectedServices.stream().mapToDouble(Service::getPrice).sum();
        reservation.setReservationPrice(totalBookingCost);

        reservationList.add(reservation);

        System.out.println("Booking Berhasil!");
        System.out.println("Total Biaya Booking : Rp. " + totalBookingCost);
    }

    private static String generateReservationId() {
        String formattedCounter = String.format("%03d", reservationCounter++);
        return "Rsv-" + formattedCounter;
    }

    public static void editReservationWorkstage(List<Reservation> reservationList) {
        int count = 1;
        List<Reservation> inProcessReservations = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("In Process")) {
                PrintService.printReservation(reservation, count);
                inProcessReservations.add(reservation);
                count++;
            }
        }
    
        if (inProcessReservations.isEmpty()) {
            System.out.println("Tidak ada reservasi yang sedang diproses.");
            return;
        }

        System.out.print("Silahkan Masukkan Reservation Id: ");
        String reservationId = scanner.next();
        if (!ValidationService.isValidReservationId(reservationId, reservationList)) {
            System.out.println("Format Reservation Id tidak valid atau Reservation tidak ditemukan.");
            return;
        }

        Reservation selectedReservation = null;
        for (Reservation reservation : inProcessReservations) {
            if (reservation.getReservationId().equals(reservationId)) {
                selectedReservation = reservation;
                break;
            }
        }
    
        if (selectedReservation == null) {
            System.out.println("Reservation yang dicari tidak tersedia.");
            return;
        }

        if (!selectedReservation.getWorkstage().equalsIgnoreCase("In Process")) {
            System.out.println("Reservation yang dicari sudah selesai.");
            return;
        }

        System.out.println("Selesaikan reservasi:");
        System.out.println("1. Finish");
        System.out.println("2. Cancel");
        System.out.print("Pilihan Anda: ");
        int choice = scanner.nextInt();
    
        switch (choice) {
            case 1:
                selectedReservation.setWorkstage("Finish");
                System.out.println("Reservasi dengan id " + reservationId + " sudah selesai.");
                break;
            case 2:
                selectedReservation.setWorkstage("Canceled");
                System.out.println("Reservasi dengan id " + reservationId + " dibatalkan.");
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
    
}
