package com.example.opensesameapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by User on 3/14/2017.
 */

public class PlatformListAdapter extends ArrayAdapter<Platform> {

    private static final String TAG = "PlatformListAdapter";

    DatabaseHelper myDB;
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView platform;
        TextView password;
        Button button;
    }

    public PlatformListAdapter(Context context, int resource, ArrayList<Platform> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the platforms information
        String account = getItem(position).getplatform();
        String password = getItem(position).getpassword();

        //Create the platform object with the information
        Platform platform = new Platform(account,password);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.platform = (TextView) convertView.findViewById(R.id.listPlatform);
            holder.password = (TextView) convertView.findViewById(R.id.listPass);
            holder.button = (Button) convertView.findViewById(R.id.listBtn);



            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        /* FOR ANIMATION
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;
        */

        // FOR DELETE BUTTON START
        myDB = new DatabaseHelper(mContext);
        holder = (ViewHolder) convertView.getTag();
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();\
                String chosenPlatform =  getItem(position).getpassword(); //backwards for some reason
                Toast.makeText(getContext(), "Deleted information for "+ chosenPlatform +" account", Toast.LENGTH_SHORT).show();
                myDB.deleteData(getItem(position).getpassword());


            }
        });



        // FOR DELETE BUTTON END



        holder.platform.setText(platform.getplatform());
        holder.password.setText(platform.getpassword());


        return convertView;
    }
}
