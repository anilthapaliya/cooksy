package com.bca.cooksy.views.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bca.cooksy.R;

public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ImageView image = view.findViewById(R.id.image);
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.image_fade_in);
        image.startAnimation(fadeIn);

        TextView txtTitle = view.findViewById(R.id.tvTitle);
        Animation leftToRight = AnimationUtils.loadAnimation(getContext(), R.anim.from_left);
        txtTitle.startAnimation(leftToRight);

        TextView txtSubtitle = view.findViewById(R.id.tvSubtitle);
        Animation topToBottom = AnimationUtils.loadAnimation(getContext(), R.anim.top_to_bottom);
        txtSubtitle.startAnimation(topToBottom);

        return view;
    }

}
