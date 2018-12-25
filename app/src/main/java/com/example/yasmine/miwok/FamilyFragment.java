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
public class FamilyFragment extends Fragment {



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


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);



        final ArrayList<word> words = new ArrayList<word>();
        words.add(new word("father", "әpә", R.drawable.family_father, R.raw.family_father
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("son", "angsi", R.drawable.family_son, R.raw.family_son
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("older brother", "taachi", R.drawable.family_older_brother,
                R.raw.family_older_brother, R.drawable.baseline_play_arrow_white_24));
        words.add(new word("younger brother", "chalitti", R.drawable.family_younger_brother,
                R.raw.family_younger_brother, R.drawable.baseline_play_arrow_white_24));
        words.add(new word("older sister", "teṭe", R.drawable.family_older_sister,
                R.raw.family_older_sister, R.drawable.baseline_play_arrow_white_24));
        words.add(new word("younger sister", "kolliti", R.drawable.family_younger_sister,
                R.raw.family_younger_sister, R.drawable.baseline_play_arrow_white_24));
        words.add(new word("grandmother ", "ama", R.drawable.family_grandmother,
                R.raw.family_grandmother, R.drawable.baseline_play_arrow_white_24));
        words.add(new word("grandfather", "paapa", R.drawable.family_grandfather,
                R.raw.family_grandfather, R.drawable.baseline_play_arrow_white_24));


        wordAdapter adapter = new wordAdapter(getActivity(),words,R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

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

