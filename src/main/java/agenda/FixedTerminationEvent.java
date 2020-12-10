package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Description : A repetitive event that terminates after a given date, or after
 * a given number of occurrences
 */
public class FixedTerminationEvent extends RepetitiveEvent {
    
    // Attributs
    private long numberOfOccurrences;
    private LocalDate fin;

    
    /**
     * Constructs a fixed terminationInclusive event ending at a given date
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param terminationInclusive the date when this event ends
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, LocalDate terminationInclusive) {
        super(title, start, duration, frequency);
        verifDate(start.toLocalDate(), terminationInclusive);
        this.fin = terminationInclusive;
        
        if (this.getFrequency().equals(ChronoUnit.DAYS)) {
            this.numberOfOccurrences = ChronoUnit.DAYS.between(start.toLocalDate(), terminationInclusive) + 1;
        }
        if (this.getFrequency().equals(ChronoUnit.WEEKS)) {
            this.numberOfOccurrences = ChronoUnit.WEEKS.between(start.toLocalDate(), terminationInclusive) + 1;
        }
        if (this.getFrequency().equals(ChronoUnit.MONTHS)) {
            this.numberOfOccurrences = ChronoUnit.MONTHS.between(start.toLocalDate(), terminationInclusive) + 1;
        }
        
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param numberOfOccurrences the number of occurrences of this repetitive event
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, long numberOfOccurrences) {
        super(title, start, duration, frequency);
        this.setNumberOfOccurrences(numberOfOccurrences);
        this.fin = toDate(numberOfOccurrences);
    }
    
    public void verifDate(LocalDate dbt, LocalDate fin){
        if (dbt.isAfter(fin))
            throw new UnsupportedOperationException("Fin avant le début");
    }

    /**
     *
     * @return the termination date of this repetitive event
     */
    public LocalDate getTerminationDate() {
        return fin;
    }

    public long getNumberOfOccurrences() {
        return numberOfOccurrences;
    }
    
    public void setNumberOfOccurrences(long occ){
        if (occ<0)
            throw new UnsupportedOperationException("Le nombre d'occurence est négatif");
        this.numberOfOccurrences=occ;
    }
    
    /**
     * Convertit un nombre d'occurence en une date précise
     * @param o 
     * @return  l : la date de fin
     */
    public LocalDate toDate(long o){
        LocalDateTime l=null;
        if(o<1){
        return this.getStart().toLocalDate();
        }
        switch(this.myFrequency){
            case DAYS:
                l=this.getStart().plusDays(o-1);
                System.out.println(o);
                break;
            case WEEKS:
                l=this.getStart().plusWeeks(o-1);
                break;
            case MONTHS:
                l=this.getStart().plusMonths(o-1);
                break;
            default:
                break;
        }
        return l.toLocalDate();
    }
    
    @Override
    public boolean isInDay(LocalDate aDay) {
        if(this.myExceptions.contains(aDay) || (aDay.isAfter(fin))) {
            return false;
        }
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
        
}
