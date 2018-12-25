package com.example.yasmine.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class wordAdapter extends ArrayAdapter<word> {

    private static final String LOG_TAG = wordAdapter.class.getSimpleName();

    private int mColoreResourceID;

    public wordAdapter(Context context, ArrayList<word> words,int ColorResourceID) {
        super(context, 0, words);
       mColoreResourceID = ColorResourceID;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);


        }

        word trans = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID default word
        TextView defaultWord = (TextView) listItemView.findViewById(R.id.default_word);
        // Get the default translation from the current word object and
        // set this text on the default word TextView
        defaultWord.setText(trans.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID miwok word
        TextView miwokWord = (TextView) listItemView.findViewById(R.id.miwok_word);

        // Get the miwok translation from the current word object and
        // set this text on the miwok word TextView
        miwokWord.setText(trans.getMiwokeTranslation());

        // Find the ImageView in the list_item.xml layout with the ID image
        ImageView imageMiwokWord = (ImageView) listItemView.findViewById(R.id.image);

        // Get the image resource id from the current word object and
        // set this resource on the image ImageView

        if (trans.hasImage()) {
            imageMiwokWord.setImageResource(trans.getImageResourceId());

            imageMiwokWord.setVisibility(View.VISIBLE);

        }

        else {

           imageMiwokWord.setVisibility(View.GONE);
        }



        //set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);


        //find the color that resource ID map to
        int color = ContextCompat.getColor(getContext(),mColoreResourceID);

        //set the background color of the text container view
        textContainer.setBackgroundColor(color);

        ImageView playButton = (ImageView) listItemView.findViewById(R.id.play_button);
        playButton.setImageResource(trans.getIconResourceId());
        playButton.setVisibility(View.VISIBLE);



        return listItemView;
    }

}
