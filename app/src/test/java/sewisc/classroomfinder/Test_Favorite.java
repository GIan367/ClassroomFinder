package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;

import static org.junit.Assert.*;
public class Test_Favorite {
    @Test
    public void favoriteGet(){
        Favorite test1 = new Favorite(1, "Bascom", "Admin", "Classroom 1");
        assertEquals(true, test1.getBuildingName().equals("Bascom"));
        assertEquals(true, test1.getDestination().equals("Classroom 1"));
        assertEquals(true, test1.getIndx() == 1);
        assertEquals(true, test1.getStartLocation().equals("Admin"));
    }
    @Test
    public void favoriteSet(){
        Favorite test1 = new Favorite(1, "Bascom", "Admin", "Classroom 1");
        assertEquals(true, test1.getBuildingName().equals("Bascom"));
        assertEquals(true, test1.getDestination().equals("Classroom 1"));
        assertEquals(true, test1.getIndx() == 1);
        assertEquals(true, test1.getStartLocation().equals("Admin"));
        test1.setBuildingName("Dairy Science");
        test1.setDestination("Nutrition Lab");
        test1.setIndx(3);
        test1.setStartLocation("Autoclave");
        assertEquals(true, test1.getBuildingName().equals("Dairy Science"));
        assertEquals(true, test1.getDestination().equals("Nutrition Lab"));
        assertEquals(true, test1.getIndx() == 3);
        assertEquals(true, test1.getStartLocation().equals("Autoclave"));
    }
}
