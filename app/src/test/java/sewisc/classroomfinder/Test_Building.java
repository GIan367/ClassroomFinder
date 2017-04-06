package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;

import static org.junit.Assert.*;
public class Test_Building {
    @Test
    public void buildingGet(){
        Building test1 = new Building(1, "Bascom");
        assertEquals(true, test1.getID() == 1);
        assertEquals(true, test1.getName().equals("Bascom"));
    }

    @Test
    public void buildingSet(){
        Building test1 = new Building(1, "Bascom");
        assertEquals(true, test1.getID() == 1);
        assertEquals(true, test1.getName().equals("Bascom"));
        test1.setID(5);
        test1.setName("Dairy Science");
        assertTrue(test1.getID() == 5);
        assertTrue(test1.getName() == "Dairy Science");
    }
}
