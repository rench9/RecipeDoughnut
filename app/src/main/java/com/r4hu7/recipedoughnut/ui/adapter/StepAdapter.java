package com.r4hu7.recipedoughnut.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private List<Step> steps;

    public StepAdapter(List<Step> steps) {
        this.steps = steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        AdapterStepDescBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_step_desc, viewGroup, false);
        return new ViewHolder(null);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

//        viewHolder.setStep(steps.get(i));
    }

    @Override
    public int getItemCount() {
        if (steps == null)
            return 0;
        return steps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        private AdapterStepDescBinding binding;
//
//        ViewHolder(@NonNull AdapterStepDescBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }

        ViewHolder(@NonNull View binding) {
            super(binding);
        }
//        void setStep(Step step) {
//            binding.setStep(step);
//        }

    }
}
