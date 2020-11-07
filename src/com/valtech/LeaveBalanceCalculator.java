package com.valtech;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LeaveBalanceCalculator {

    // source : https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html
    //          https://mkyong.com/java/java-display-double-in-2-decimal-points/
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static void main(String[] args) {

        try {
            // call checkArgs() to check arguments, return the first argument in a date format and the second argument as integer
            Args argument = checkArgs(args);

            // call balanceCalculator() to get balance
            double balance  = balanceCalculator(LocalDate.now(), argument.getDate()) - argument.getLeave();

            // check that I have leave days
            if (balance < 0) System.out.println("Negative balance");
            else System.out.println("Leave balance : " + decimalFormat.format(balance));

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static double balanceCalculator(LocalDate currentdate, LocalDate entredDate) {

        // balance days at the current month
        double balanceDaysCurrentMonth = (currentdate.lengthOfMonth() - currentdate.getDayOfMonth()) * 2.0 / currentdate.lengthOfMonth();
        // balance days at the entred date month
        double balanceDaysLastMonth = entredDate.getDayOfMonth() * 2.0 / entredDate.lengthOfMonth();

        // balance days at the entred date
        return balanceDaysCurrentMonth + balanceDaysLastMonth + ((entredDate.getYear() - currentdate.getYear()) * 12.0 + entredDate.getMonthValue() - currentdate.getMonthValue() - 1.0)*2.0;
    }

    private static Args checkArgs(String[] args) throws IllegalArgumentException {
        try{
            // source : https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
            return new Args(LocalDate.parse(args[0] , DateTimeFormatter.ofPattern("yyyy-MM-dd")), Integer.parseInt(args[1]));

        } catch(NumberFormatException | DateTimeParseException | IndexOutOfBoundsException e ){
            throw new IllegalArgumentException("Wrong parameters : first arg must be date : \"yyyy-MM-dd\", second arg must be an integer");
        }
    }

    public static class Args {
        LocalDate date;
        int leave;

        public Args(LocalDate date, int leave) {
            super();
            this.date = date;
            this.leave = leave;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public int getLeave() {
            return leave;
        }

        public void setLeave(int leave) {
            this.leave = leave;
        }
    }

}
