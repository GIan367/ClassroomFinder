package sewisc.classroomfinder;

/**
 * Created by rfugs on 4/4/17.
 */
import android.content.Context;
import android.test.InstrumentationTestCase;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class Test_ImageAdapter extends InstrumentationTestCase {
    @Test
    public void testGetCount() {
        Context emptyContext = getInstrumentation().getContext();
        ImageAdapter adapter = new ImageAdapter(emptyContext);
        assertEquals(0, adapter.getCount());
    }

    @Test
    public void testSetmThumbIds() {
        Context emptyContext = getInstrumentation().getContext();
        ImageAdapter adapter = new ImageAdapter(emptyContext);
        Integer[] array = {
                0, 1, 2, 3
        };
        adapter.setmThumbIds(array);
        assertEquals(4, adapter.getCount());
    }

    @Test
    public void testGetItem() {
        Context emptyContext = getInstrumentation().getContext();
        ImageAdapter adapter = new ImageAdapter(emptyContext);
        Integer[] array = {
                0, 1, 2, 3
        };
        adapter.setmThumbIds(array);
        assertEquals(2, adapter.getItemRef(3));
    }
}
