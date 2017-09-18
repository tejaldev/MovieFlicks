package com.movie.flicks.video;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Helper class to initialize
 *
 * @author tejalpar
 */
public class YouTubePlayerViewHelper {
    private static final String YOUTUBE_API_KEY = "AIzaSyBQVQLo75B35ueG_hH143sRz2QjC1IB5ec";

    public static void initializeAndCueVideo(YouTubePlayerView youTubePlayerView, final String videoKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY,
            new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer, boolean b) {

                    // cueVideo will just load the video and wait for user to initiate the play
                    youTubePlayer.cueVideo(videoKey);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                    YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
    }

    public static void initializeAndPlayVideo(YouTubePlayerView youTubePlayerView, final String videoKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // loadVideo will load & play the video
                        youTubePlayer.loadVideo(videoKey);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
