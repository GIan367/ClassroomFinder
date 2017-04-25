package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class Test_GridAdapter {
    @Test
    public void testGetCount() {
        ArrayList<String> items = new ArrayList<>();
        for(int i=0; i < 10; i++) {
            items.add("item" + Integer.toString(i));
        }

        GridAdapter adapter = new GridAdapter(items);
        assertEquals(10, adapter.getCount());
    }

    @Test
    public void testGetItem() {
        System.out.println("start");
        ArrayList<String> items = new ArrayList<>();
        for(int i=0; i < 10; i++) {
            items.add("item" + Integer.toString(i));
        }

        GridAdapter adapter = new GridAdapter(items);
        assertEquals("item5", adapter.getItem(5));
    }
}
