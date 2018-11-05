package com.r4hu7.recipedoughnut.ui.adapter;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Step;
import com.r4hu7.recipedoughnut.databinding.AdapterRecipeStepBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private ArrayList<Step> steps;

    public StepAdapter(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterRecipeStepBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_recipe_step, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setStep(steps.get(i));
        if (steps.get(i).haveVideo()) {
            viewHolder.player = ExoPlayerFactory.newSimpleInstance(viewHolder.binding.getRoot().getContext(), new DefaultTrackSelector());
            viewHolder.player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            viewHolder.playerView.setPlayer(viewHolder.player);
            viewHolder.player.setPlayWhenReady(viewHolder.playWhenReady);
            viewHolder.player.seekTo(viewHolder.currentWindow, viewHolder.playbackPosition);
            Uri uri = Uri.parse(viewHolder.binding.getStep().getVideoURL());
            viewHolder.player.prepare(buildMediaSource(uri), true, false);
        }
    }

    @Override
    public int getItemCount() {
        if (steps == null)
            return 0;
        return steps.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        releasePlayer(holder);
    }

    ExtractorMediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoPlayer-recipeDoughnut")).
                createMediaSource(uri);
    }

    public void releasePlayer(ViewHolder viewHolder) {
        if (viewHolder.player != null) {
            viewHolder.playbackPosition = viewHolder.player.getCurrentPosition();
            viewHolder.currentWindow = viewHolder.player.getCurrentWindowIndex();
            viewHolder.playWhenReady = viewHolder.player.getPlayWhenReady();
            viewHolder.player.release();
            viewHolder.player = null;
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        releasePlayer(holder);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
        releasePlayer(holder);
        return super.onFailedToRecycleView(holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleExoPlayer player;
        @BindView(R.id.pvPrimary)
        PlayerView playerView;
        private AdapterRecipeStepBinding binding;
        private int currentWindow;
        private long playbackPosition;
        private boolean playWhenReady = true;

        ViewHolder(@NonNull AdapterRecipeStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        void setStep(Step step) {
            binding.setStep(step);
        }


    }
}
