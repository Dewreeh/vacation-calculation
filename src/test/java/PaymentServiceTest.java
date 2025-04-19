import org.junit.jupiter.api.Test;
import org.repin.service.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {


    @Test
    public void GetPaymentWithoutDatesSuccess(){

        PaymentService paymentService = new PaymentService();

        BigDecimal salary = BigDecimal.valueOf(1000000);
        int vacationDays = 5;

        LocalDate startDate = null;
        LocalDate endDate = null;

        BigDecimal res = paymentService.getVacationPayment(salary, vacationDays, startDate, endDate);

        assertEquals(BigDecimal.valueOf(14220), res);
    }

    @Test
    public void GetPaymentWithDatesSuccess(){

        PaymentService paymentService = new PaymentService();

        BigDecimal salary = BigDecimal.valueOf(1000000);
        int vacationDays = 5;

        LocalDate startDate = LocalDate.of(2025, 4, 14);
        LocalDate endDate = LocalDate.of(2025, 4, 18);

        BigDecimal res = paymentService.getVacationPayment(salary, vacationDays, startDate, endDate);

        assertEquals(BigDecimal.valueOf(14220), res);
    }

    @Test
    public void GetPaymentWithoutDatesError(){

        PaymentService paymentService = new PaymentService();

        BigDecimal salary = BigDecimal.valueOf(1000000);
        int vacationDays = 5;

        LocalDate startDate = null;
        LocalDate endDate = null;

        BigDecimal res = paymentService.getVacationPayment(salary, vacationDays, startDate, endDate);

        assertNotEquals(BigDecimal.valueOf(14223), res);
    }

    @Test
    public void GetPaymentWithDatesError(){

        PaymentService paymentService = new PaymentService();

        BigDecimal salary = BigDecimal.valueOf(1000000);
        int vacationDays = 5;

        LocalDate startDate = LocalDate.of(2025, 4, 14);
        LocalDate endDate = LocalDate.of(2025, 4, 18);

        BigDecimal res = paymentService.getVacationPayment(salary, vacationDays, startDate, endDate);

        assertNotEquals(BigDecimal.valueOf(14224), res);
    }


    @Test
    public void GetPaymentWithDatesExcludeWeekendsSuccess(){

        PaymentService paymentService = new PaymentService();

        BigDecimal salary = BigDecimal.valueOf(1000000);
        int vacationDays = 7;

        LocalDate startDate = LocalDate.of(2025, 4, 14);
        LocalDate endDate = LocalDate.of(2025, 4, 20); //19 и 20 выходные, так что результат должен быть как и в 14-18

        BigDecimal res = paymentService.getVacationPayment(salary, vacationDays, startDate, endDate);

        assertEquals(BigDecimal.valueOf(14220), res);
    }


}
