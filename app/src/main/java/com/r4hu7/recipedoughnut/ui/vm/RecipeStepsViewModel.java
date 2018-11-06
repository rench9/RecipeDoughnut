package com.r4hu7.recipedoughnut.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.r4hu7.recipedoughnut.data.LoadItemCallback;
import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.data.remote.response.model.Step;

public class RecipeStepsViewModel extends ViewModel {
    private int recipeId;

    private RecipeRepository repository;
    private ObservableList<Step> steps = new ObservableArrayList<>();
    private ObservableInt stepIndex = new ObservableInt();


    public int currentWindow;
    public long playbackPosition;
    public boolean playWhenReady = true;

    public RecipeStepsViewModel() {
    }

    @BindingAdapter("videoSrc")
    public static void setVideo(PlayerView playerView, String url) {

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(playerView.getContext(), trackSelector);

        playerView.setPlayer(player);

        player.setPlayWhenReady(true);
//        player.seekTo(0, 200);

        Uri uri = Uri.parse(url);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    public void loadSteps() {
        repository.getRecipe(recipeId, new LoadItemCallback<Recipe>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(Recipe recipe) {
                steps.addAll(recipe.getSteps());
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }

    public ObservableList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ObservableList<Step> steps) {
        this.steps = steps;
    }

    public ObservableInt getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex.set(stepIndex);
    }

}
