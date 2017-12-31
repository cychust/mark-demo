package com.cyc.markdemo.taskdetail;

import android.animation.Animator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.cyc.markdemo.R;
import com.cyc.markdemo.Utils.Transition;
import com.cyc.markdemo.addtask.AddTaskActivity;

/**
 * Created by cyc20 on 2017/12/31.
 */

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {


    @NonNull
    private static final String ARGUMENT_TASK_ID = "TASK_ID";

    private TaskDetailContract.Presenter mPresenter;

    private CardView mCardView;
    private TextView mTitleTV;
    private TextView mDescriptionTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_taskdetail_main, container, false);
        mCardView = rootView.findViewById(R.id.card_view);
        mTitleTV = mCardView.findViewById(R.id.title_tv);
        mDescriptionTV = rootView.findViewById(R.id.detail_description_tv);
        setHasOptionsMenu(true);

        long transitionDuration = 8000;
        Transition.enter(
                getActivity(),
                transitionDuration,
                new DecelerateInterpolator(),
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //finishEnter=true;
                        initOtherViews();
                    }

                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }
        );

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view -> mPresenter.showAddEditTask());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public TaskDetailFragment() {
        //
    }

    public static TaskDetailFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private void initOtherViews() {
        mDescriptionTV.setVisibility(View.VISIBLE);
        mDescriptionTV.setAlpha(0);
        mDescriptionTV.setTranslationY(-30);
        mDescriptionTV.animate()
                .setDuration(300)
                .alpha(1)
                .translationY(0);
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void deleteTask(String taskId) {

    }

    @Override
    public void showAddEditTask(String taskId) {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTaskUi(String title, String description) {

    }
}
