package com.example.yasmine.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;


    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            // Pause playback
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {

                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }

            // Your app has been granted audio focus again
            // Raise volume to normal, restart playback if necessary
            //Resume playback
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();

            }
            // Stop playback
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                mMediaPlayer.stop();
                releaseMediaPlayer();

            }


        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };


    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<word> words = new ArrayList<word>();
        words.add(new word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red,
                R.drawable.baseline_play_arrow_white_24));
        words.add(new word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,
                R.raw.color_mustard_yellow, R.drawable.baseline_play_arrow_white_24));
        words.add(new word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,
                R.raw.color_dusty_yellow, R.drawable.baseline_play_arrow_white_24));
        words.add(new word("green", "chokokki", R.drawable.color_green, R.raw.color_green
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("black", "kululli", R.drawable.color_black, R.raw.color_black
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("white", "kelelli", R.drawable.color_white, R.raw.color_white
                , R.drawable.baseline_play_arrow_white_24));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        wordAdapter adapter = new wordAdapter(getActivity(), words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on

                word word = words.get(position);


                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(getActivity() , word.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }



            }
        });

        return rootView;
    }



    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}




