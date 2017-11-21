package com.Deadline.leon.deadline;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class CustomAdapter extends SimpleAdapter implements View.OnClickListener {
    /**TODO LW7 - (FIXED) GET CUSTOM ADAPTER WORKING
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    private List<Map<String, String>> deadlines;

    private int[] colors = new int[]
            {
            /*PROJECT COLOR*/0x993671b5,
            /*TASK COLOR*/0x992ec1a3,
            /*JOB COLOR*/0x994aaa6b
            };
    public int colorID = -1;
    private int i = 0;
    @SuppressWarnings("unchecked")
    public CustomAdapter(Context context,
                         /*TODO LW13 - FIGURE OUT EXACTLY WHAT THIS PART IS DOING*/
                         List<? extends Map<String, String>> data,
                         int resource,
                         String[] from,
                         int[] to) {
        super(context, data, resource, from, to);
        this.deadlines = (List<Map<String, String>>) data;
    }

    //Pretty sure this doesn't matter but I would leave it anyways
    @Override
    public void onClick(View v)
    {
        int z = 0;

        v.setBackgroundColor(0x992ec1a3);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        //int i = 0;

        /*
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);

        /*TODO LW14 - FIGURE OUT IF THIS IS ACTUALLY HAPPENING
        color of every Element is being set to the final color in the list instead of the color of
        individual item. colorID is changing but not correctly?
        */



        if(i < HomeScreen.global.getDeadlines().length)
        {
            if(HomeScreen.global.getDeadlines()[i] != null)
            {
                colorID = HomeScreen.global.getIDArray()[i];
            }
                i++;
        }

        if(colorID >= 0 && colorID < colors.length)
        {

            view.setBackgroundColor(colors[HomeScreen.global.IDArray[position]]);
        }
        //*/
        return view;
    }
}
