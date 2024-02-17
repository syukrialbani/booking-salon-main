package com.booking.service;

import java.util.List;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public String printServices(List<Service> serviceList){
        String result = "";
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    public void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.printf("| %-4s | %-10s | %-15s | %-30s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.println("+====================================================================================================+");
        for (Reservation reservation : reservationList) {
            System.out.printf("| %-4s | %-10s | %-15s | %-30s | %-15s | %-10s |\n",
                    num++, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getWorkstage());
        }
    }

    public static void showAllCustomer(List<Person> personList) {
        System.out.println("\nTampilan Customer\n");
        System.out.printf("%-4s %-6s %-8s %-16s %-12s %s\n", "No", "ID", "Nama", "Alamat", "Membership", "Uang");
        System.out.println("======================================================");
        int num = 1;
        for (Person person : personList) {
            if (person instanceof com.booking.models.Customer) {
                System.out.printf("%-4d %-6s %-8s %-16s %-12s %s\n", num++, person.getId(), person.getName(), person.getAddress(), ((com.booking.models.Customer) person).getMember().getMembershipName(), ((com.booking.models.Customer) person).getWallet());
            }
        }
    }

    public static void showAllEmployee(List<Person> personList) {
        System.out.printf("%-4s %-6s %-8s %-16s %s\n", "No", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("==================================");
        int num = 1;
        for (Person person : personList) {
            if (person instanceof com.booking.models.Employee) {
                System.out.printf("%-4d %-6s %-8s %-16s %s\n", num++, person.getId(), person.getName(), person.getAddress(), ((com.booking.models.Employee) person).getExperience());
            }
        }
    }

    public void showHistoryReservation(List<Reservation> reservationList) {
        int num = 1;
        double totalProfit = 0.0;
        System.out.printf("| %-4s | %-10s | %-15s | %-30s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.println("+====================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Finish") || reservation.getWorkstage().equalsIgnoreCase("Canceled")) {
                double price = reservation.getWorkstage().equalsIgnoreCase("Finish") ? reservation.getReservationPrice() : 0.0;
                System.out.printf("| %-4s | %-10s | %-15s | %-30s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), price, reservation.getWorkstage());
                totalProfit += price;
                num++;
            }
        }
        System.out.println("+====================================================================================================+");
        System.out.println("Total Keuntungan: \t\t\t\t\t\t\tRp. " + totalProfit);
    }
    
    public static void printReservation(Reservation reservation, int count) {
        StringBuilder serviceNames = new StringBuilder();
        System.out.println("Finish/Cancel Reservasi");
        System.out.printf("| %-4s | %-10s | %-15s | %-30s | %-15s\n",
        "No.", "ID", "Nama Customer", "Nama Service", "Total Biaya");
        System.out.println("+====================================================================================+");
        for (Service service : reservation.getServices()) {
            serviceNames.append(service.getServiceName()).append(", ");
        }
        String services = serviceNames.toString().replaceAll(", $", "");

        System.out.printf("%-4d\t%-10s\t%-15s\t%-30s\t%-15.2f\n",
                count, reservation.getReservationId(), reservation.getCustomer().getName(), services, reservation.getReservationPrice());
    }
    
    
}
