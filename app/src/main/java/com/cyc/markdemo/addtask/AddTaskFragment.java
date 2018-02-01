package com.cyc.markdemo.addtask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cyc.markdemo.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by cyc20 on 2017/12/30.
 */

public class AddTaskFragment extends Fragment implements AddTaskContract.View {


    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    // private String mTitle;
    // private String mDescription;
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private AddTaskContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addtask, container, false);
        mTitleEditText = rootView.findViewById(R.id.title_input_edit);
        mDescriptionEditText = rootView.findViewById(R.id.description_input_edit);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveTask(mTitleEditText.getText().toString(), mDescriptionEditText.getText().toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public AddTaskFragment() {

    }

    public static AddTaskFragment newInstance() {
        return new AddTaskFragment();
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setTitle(String title) {
        mTitleEditText.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescriptionEditText.setText(description);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showTaskList() {
        getActivity().finish();
    }
}
