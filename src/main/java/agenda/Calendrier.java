/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author Stéphane
 */
public class Calendrier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Duration d1 = Duration.ofDays(1);
        Duration d2 = Duration.ofMinutes(120);
        
        LocalDateTime d = LocalDateTime.of(2020, 8 , 12, 22, 30);
        
        Agenda a = new Agenda();
        Event e1 = new Event("Aniiversaire 1", LocalDateTime.of(2020, 8, 13, 22, 29), d2);
        
        Event e2 = new Event("RDV 2", LocalDateTime.of(2020, 8, 13, 22, 30), d2);
        
        
        a.addEvent(e1);
        a.addEvent(e2);

        
    }
    
}
