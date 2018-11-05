package com.r4hu7.recipedoughnut.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Step;
import com.r4hu7.recipedoughnut.databinding.AdapterRecipeStepDescBinding;
import com.r4hu7.recipedoughnut.util.StepsNavigator;

import java.lang.ref.WeakReference;
import java.util.List;

public class StepDescAdapter extends RecyclerView.Adapter<StepDescAdapter.ViewHolder> {
    private List<Step> steps;
    private StepsNavigator stepsNavigator;

    public StepDescAdapter(List<Step> steps, StepsNavigator stepsNavigator) {
        this.steps = steps;
        this.stepsNavigator = stepsNavigator;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterRecipeStepDescBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_recipe_step_desc, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setStep(steps.get(i));
        viewHolder.setNavigator(this.stepsNavigator);
    }

    @Override
    public int getItemCount() {
        if (steps == null)
            return 0;
        return steps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AdapterRecipeStepDescBinding binding;

        ViewHolder(@NonNull AdapterRecipeStepDescBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setStep(Step step) {
            binding.setStep(step);
        }

        void setNavigator(StepsNavigator stepsNavigator) {
            this.binding.setNavigator(new WeakReference<>(stepsNavigator));
        }

    }
}
