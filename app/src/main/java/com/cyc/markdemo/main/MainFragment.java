package com.cyc.markdemo.main;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cyc.markdemo.R;
import com.cyc.markdemo.addtask.AddTaskActivity;
import com.cyc.markdemo.data.Task;
import com.cyc.markdemo.data.resource.local.TaskDao;
import com.cyc.markdemo.mvp.BasePresenter;
import com.cyc.markdemo.taskdetail.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by cyc20 on 2017/12/28.
 */

public class MainFragment extends Fragment implements MainContract.View {


    private MainPresenter mPresenter;

    private MainAdapter mMainAdapter;

    ItemClickListener mItemClickListener = new ItemClickListener() {
        @Override
        public void onClick(Task clickedTask, View view) {
            mPresenter.openTaskDetails(clickedTask,view);

        }
    };

    public MainFragment() {
        //
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainAdapter = new MainAdapter(new ArrayList<Task>(0), mItemClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_main, container, false);
        ListView listView = rootView.findViewById(R.id.list_view);
        listView.setAdapter(mMainAdapter);

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view -> mPresenter.addNewTask());
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mMainAdapter.replace(tasks);
    }

    @Override
    public void showAddTask() {
        Intent intent=new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTaskDetailUi(String taskId,View view) {
        //todo Intent intent=new Intent(getContext(),);
        Rect rect=new Rect();
        view.getGlobalVisibleRect(rect);
        Intent intent=new Intent(getActivity(), TaskDetailActivity.class);
        Log.d("activity", "showTaskDetailUi: ");
        intent.setSourceBounds(rect);
        Bundle bundle=new Bundle();
        bundle.putString("taskId",taskId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public interface ItemClickListener {
        void onClick(Task clickedTask, View view);
    }

    private static class MainAdapter extends BaseAdapter {

        private List<Task> mTasks = new ArrayList<>();
        private ItemClickListener mItemClickListener;

        public MainAdapter(List<Task> tasks, ItemClickListener itemClickListener) {
            mTasks = checkNotNull(tasks);
            notifyDataSetChanged();
            mItemClickListener = itemClickListener;

        }

        @Override
        public int getCount() {
            return mTasks.size();
        }

        @Override
        public Object getItem(int i) {
            return mTasks.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View rootView = view;
            if (rootView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rootView = inflater.inflate(R.layout.card_item_main, viewGroup, false);

            }
            final Task task = (Task) getItem(i);
            TextView titleTv = rootView.findViewById(R.id.title_tv);
            titleTv.setText(task.getTitle());
            rootView.setOnClickListener(view1 -> mItemClickListener.onClick(task,view1));
            return rootView;

        }
        void replace(List<Task> tasks){
            mTasks=checkNotNull(tasks);
            notifyDataSetChanged();
        }
    }
}
