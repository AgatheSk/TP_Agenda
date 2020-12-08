package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FixedTerminationEventTest {

    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);

    // November 1st, 2020, 22:30
    LocalDateTime nov_1__2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);

    // January 5, 2021
    LocalDate jan_5_2021 = LocalDate.of(2021, 1, 5);

    // 120 minutes
    Duration min_120 = Duration.ofMinutes(120);

    // A Weekly Repetitive event ending at a given date
    FixedTerminationEvent fixedTermination = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, jan_5_2021);

    // A Weekly Repetitive event ending after a give number of occurrrences
    FixedTerminationEvent fixedRepetitions = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, 10);
    
    @Test
    public void canCalculateNumberOfOccurrencesFromTerminationDate() {
        assertEquals(10, fixedTermination.getNumberOfOccurrences(), "Cet événement doits se répéter 10 fois");
    }

    @Test
    public void canCalculateTerminationDateFromNumberOfOccurrences() {
        LocalDate termination = LocalDate.of(2021,1, 3);
        assertEquals(termination, fixedRepetitions.getTerminationDate(), "Cet événement doits se terminer le 3 janvier");
    }
    
    @Test
    public void eventIsInItsStartDay() {
        assertTrue(fixedTermination.isInDay(nov_1_2020), "Un événement a lieu dans son jour de début");
        assertTrue(fixedRepetitions.isInDay(nov_1_2020), "Un événement a lieu dans son jour de début");
    }

    @Test
    public void eventIsNotInDayBefore() {
        assertFalse(fixedTermination.isInDay(nov_1_2020.minus(1, ChronoUnit.DAYS)), "Un événement n'a pas lieu avant son jour de début");
        assertFalse(fixedRepetitions.isInDay(nov_1_2020.minus(1, ChronoUnit.DAYS)), "Un événement n'a pas lieu avant son jour de début");
    }

    @Test
    public void eventOccurs10WeeksAfter() {
        assertTrue(fixedTermination.isInDay(nov_1_2020.plus(6, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
        assertTrue(fixedRepetitions.isInDay(nov_1_2020.plus(6, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
    }

    @Test
    public void eventIsNotInExceptionDays() {
        fixedTermination.addException(nov_1_2020.plus(2, ChronoUnit.WEEKS)); // ne se produit pas à W+2
        fixedTermination.addException(nov_1_2020.plus(4, ChronoUnit.WEEKS)); // ne se produit pas à W+4
        assertTrue(fixedTermination.isInDay(nov_1_2020.plus(1, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
        assertFalse(fixedTermination.isInDay(nov_1_2020.plus(2, ChronoUnit.WEEKS)), "Cet événement ne se produit pas à W+2");
        assertTrue(fixedTermination.isInDay(nov_1_2020.plus(3, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
        assertFalse(fixedTermination.isInDay(nov_1_2020.plus(4, ChronoUnit.WEEKS)), "Cet événement ne se produit pas à W+4");
    }

}
