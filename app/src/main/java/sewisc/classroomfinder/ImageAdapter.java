package sewisc.classroomfinder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Zak on 3/15/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public void setmThumbIds(Integer[] mThumbIds) {
        this.mThumbIds = mThumbIds;
    }

    // Not used by our application, but must be implemented by BaseAdapter subclasses.
    // Simply return null as a placeholder.
    public Object getItem(int position) {
        return null;
    }

    // Not used by our application, but must be implemented by BaseAdapter subclasses.
    // Simply return 0 as a placeholder.
    public long getItemId(int position) {
        return 0;
    }

    public int getItemRef(int position) {
        return mThumbIds[position];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {};
}
