package org.repin.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Period;
import java.util.Set;

@Service
public class PaymentService {

    private static final Set<MonthDay> HOLIDAYS = Set.of(
            MonthDay.of(1, 1),
            MonthDay.of(1, 2),
            MonthDay.of(1, 3),
            MonthDay.of(1, 4),
            MonthDay.of(1, 5),
            MonthDay.of(1, 6),
            MonthDay.of(1, 7),
            MonthDay.of(1, 8),
            MonthDay.of(2, 23),
            MonthDay.of(3, 8),
            MonthDay.of(5, 1),
            MonthDay.of(5, 9),
            MonthDay.of(6, 12),
            MonthDay.of(11, 4)
    );

    public BigDecimal getVacationPayment(BigDecimal annualSalary, int vacationDaysCount, LocalDate startDate, LocalDate endDate){

        int paidDays;

        if(startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Дата начала позже даты окончания");
            }

            if (vacationDaysCount != Period.between(startDate, endDate).getDays() + 1) {
                throw new IllegalArgumentException("Количество дней отпуска не соответсвует разнице между датами начала и окончания");
            }
            paidDays = getCountOfPaidDays(startDate, endDate);
        } else {
            paidDays = vacationDaysCount;
        }


        BigDecimal averageDailySalary = getAverageSalaryPerDay(annualSalary);

        BigDecimal vacationPayment = getPayment(averageDailySalary, paidDays);

        return vacationPayment;
    }

    private BigDecimal getAverageSalaryPerDay(BigDecimal annualSalary){
        //логика расчёта взята с https://t-j.ru/guide/otpusk-stitaem/#one
        return annualSalary.divide(BigDecimal.valueOf(12*29.3), RoundingMode.HALF_UP);
    }

    private BigDecimal getPayment(BigDecimal averageDailySalary, int vacationDaysCount){
        //логика расчёта взята с https://t-j.ru/guide/otpusk-stitaem/#one
        return averageDailySalary.multiply(BigDecimal.valueOf(vacationDaysCount));
    }

    private int getCountOfPaidDays(LocalDate startDate, LocalDate endDate){

        LocalDate currentDate = startDate;
        int count = 0;

        while(!currentDate.isAfter(endDate)){

            if(isWorkingDay(currentDate)) {
                count++;
            }

            currentDate = currentDate.plusDays(1);
        }

        return count;
    }

    private boolean isWorkingDay(LocalDate date){
        int dayOfWeek = date.getDayOfWeek().getValue();

        MonthDay monthDay = MonthDay.of(date.getMonth(), date.getDayOfMonth());

        return !(dayOfWeek == 6 || dayOfWeek == 7)
                && !HOLIDAYS.contains(monthDay);
    }
}
