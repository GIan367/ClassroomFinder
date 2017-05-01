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
        assertEquals(1121, test1.getBuildingID());
        assertEquals("Humanities", test1.getName());
        assertEquals(1, test1.getFloorNumber());
        assertEquals(1, test1.getID());
    }
    @Test
    public void locationSets(){
        Location test1 = new Location(1, "Humanities", 1121, 1);
        assertEquals(1121, test1.getBuildingID());
        assertEquals("Humanities", test1.getName());
        assertEquals(1, test1.getFloorNumber());
        assertEquals(1, test1.getID());
        test1.setBuildingID(2);
        test1.setFloorNumber(2);
        test1.setID(2);
        test1.setName("Bascom");
        assertEquals(2, test1.getBuildingID());
        assertEquals("Bascom", test1.getName());
        assertEquals(2, test1.getFloorNumber());
        assertEquals(2, test1.getID());
    }

}
