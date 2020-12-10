package agenda;

import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Description : A repetitive Event
 */
public class RepetitiveEvent extends Event {
    ChronoUnit myFrequency;
    Set<LocalDate> myExceptions = new TreeSet<>();
    
    /**
     * Constructs a repetitive event
     *
     * @param title the title of this event
     * @param start the start of this event
     * @param duration myDuration in seconds
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */
    public RepetitiveEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency) {
        super(title, start, duration);
        this.myFrequency=frequency;
    }

    /**
     * Adds an exception to the occurrence of this repetitive event
     *
     * @param date the event will not occur at this date
     */
    public void addException(LocalDate date) {
        this.myExceptions.add(date);
    }

    @Override
    public boolean isInDay(LocalDate aDay) {
        if(this.myExceptions.contains(aDay))
            return false;
        switch(this.myFrequency){
            case DAYS:
                return ( aDay.isEqual(this.getStart().toLocalDate())) ||((aDay.isAfter(this.getStart().toLocalDate())));
            case WEEKS:
               return ( ( aDay.isEqual(this.getStart().toLocalDate())) ||((aDay.isAfter(this.getStart().toLocalDate()))) && (aDay.getDayOfWeek()==this.getStart().getDayOfWeek()));
            case MONTHS:
                return ( ( aDay.isEqual(this.getStart().toLocalDate())) ||((aDay.isAfter(this.getStart().toLocalDate()))) && (aDay.getDayOfMonth()==this.getStart().getDayOfMonth()));
            default:
                return false;
        }
        
    }
     
    /**
     *
     * @return the type of repetition
     */
    public ChronoUnit getFrequency() {
        return myFrequency;
    }

}
