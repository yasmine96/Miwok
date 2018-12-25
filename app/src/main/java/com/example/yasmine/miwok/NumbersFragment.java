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
public class NumbersFragment extends Fragment {

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

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> words = new ArrayList<word>();
        words.add(new word("one","lutti",R.drawable.number_one ,R.raw.number_one
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("two","otiiko",R.drawable.number_two,R.raw.number_two
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("three","tolookosu",R.drawable.number_three,R.raw.number_three
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("four","oyyisa",R.drawable.number_four,R.raw.number_four
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("five","massokka",R.drawable.number_five,R.raw.number_five
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("six","temmokka",R.drawable.number_six,R.raw.number_six
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("nine","wo’e",R.drawable.number_nine,R.raw.number_nine
                , R.drawable.baseline_play_arrow_white_24));
        words.add(new word("ten","na’aacha",R.drawable.number_ten,R.raw.number_ten
                , R.drawable.baseline_play_arrow_white_24));

        wordAdapter adapter = new wordAdapter(getActivity(), words,R.color.category_numbers);

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




             /*
                        if (position == 0) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_one);
                        } else if (position == 1) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_two);
                        } else if (position == 2) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_three);
                        } else if (position == 3) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_four);
                        } else if (position == 4) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_five);
                        } else if (position == 5) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_six);
                        } else if (position == 6) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_seven);
                        } else if (position == 7) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_eight);
                        } else if (position == 8) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_nine);
                        } else if (position == 9) {
                            mMediaPlayer = MediaPlayer.create(NumbersActivity.this, R.raw.number_ten);
                        }
                        mMediaPlayer.start();*/


    // When the activity is stopped, release the media player resources because we won't
    // be playing any more sounds.


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
