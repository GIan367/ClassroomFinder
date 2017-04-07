package sewisc.classroomfinder;

import org.junit.Test;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;

import static org.junit.Assert.*;


public class Test_Location {
    @Test
    public void locationGets(){
        Location test1 = new Location(1, "Humanities", 1121, 1);
        assertEquals(true, test1.getBuildingID() == 1);
        assertEquals(true, test1.getName().equals("Humanities"));
        assertEquals(true, test1.getFloorNumber() == 1);
        assertEquals(true, test1.getID() == 1121);
    }
    public void locationSets(){
        Location test1 = new Location(1, "Humanities", 1121, 1);
        assertEquals(true, test1.getBuildingID() == 1221);
        assertEquals(true, test1.getName().equals("Humanities"));
        assertEquals(true, test1.getFloorNumber() == 1);
        assertEquals(true, test1.getID() == 1);
        test1.setBuildingID(2);
        test1.setFloorNumber(2);
        test1.setID(2);
        test1.setName("Bascom");
        assertEquals(true, test1.getBuildingID() == 2);
        assertEquals(true, test1.getName().equals("Bascom"));
        assertEquals(true, test1.getFloorNumber() == 2);
        assertEquals(true, test1.getID() == 2);
    }

}
