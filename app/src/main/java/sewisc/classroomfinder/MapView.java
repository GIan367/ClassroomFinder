package sewisc.classroomfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Zak on 3/15/2017.
 */

public class MapView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent intent = getIntent();
        int ref = intent.getIntExtra(MainActivity.EXTRA_FLOOR, 0);
        String building = intent.getStringExtra(MainActivity.EXTRA_BUILDING);
        String loc = intent.getStringExtra(MainActivity.EXTRA_LOC);
        String dest = intent.getStringExtra(MainActivity.EXTRA_DEST);

        if(dest != null) { // All text fields populated -- standard Room Finder AStar
            //TODO: call AStar, determine correct map image to draw on (currently dummy value)
            int id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
            drawLine(id, null);
        } else if(loc != null) { // No destination populated -- Bathroom Finder AStar
            //TODO: call AStar, determine correct map image to draw on (currently dummy value)
            int id = getResources().getIdentifier("east_towne1", "mipmap", getPackageName());
            drawLine(id, null);
        } else { // No text fields populated -- only displaying a floor
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageResource(ref);
        }
    }

    // Draw line on given map image reference from current location to destination via list of nodes
    // TODO: Call AStar to get nodes and pass here -- currently hardcoded to draw a simple line
    void drawLine(int ref, List<Node> nodes){
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ref, myOptions);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(25);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);


        Canvas canvas = new Canvas(mutableBitmap);
        //canvas.drawCircle(60, 50, 25, paint);

        // TODO: loop through list of nodes from AStar; currently draws a simple test line
        canvas.drawLine(0, 0, 691, 492, paint);

        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
    }



}
