package soberich.magicdate7.view.ui.resultlist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import dagger.android.support.AndroidSupportInjection;
import soberich.magicdate7.R;
import soberich.magicdate7.viewmodel.ResEntriesViewModel;

/**
 *
 * Created by soberich on 9/17/17.
 */

public class ResEntriesListFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ResEntriesViewModel viewModel;
    private ResEntryListAdapter adapter;

    public ResEntriesListFragment() {}

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_scrolling, container, false);
        expandableListView = root.findViewById(R.id.expandable_list_view);
        Log.d("TAG", "onCreateView() ");
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(ResEntriesViewModel.class);
        adapter =
        new ResEntryListAdapter(getContext(), viewModel.getAllResEntries());
        viewModel.getNewObservableResEntries().observe(this, resEntries -> {
            //update UI
            adapter.notifyDataSetChanged();
            for(int i = 0; i < viewModel.getAllResEntries().size(); i++) {
                if(!viewModel.getAllResEntries().get(i).isExpanded())
                    expandableListView.collapseGroup(i);
                else
                    expandableListView.expandGroup(i);
            }
        });
        expandableListView.setAdapter(adapter);

        expandableListView.post(() -> {
            //height is ready
            expandableListView.setIndicatorBoundsRelative(expandableListView.getWidth() - 80
                                                        , expandableListView.getWidth());
        });

        setExpandableListTouchEvents();
    }

    private void setExpandableListTouchEvents() {
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            //makeToast(String.valueOf(adapter.getGroup(groupPosition)) + " List Expanded.");
            viewModel.getAllResEntries().get(groupPosition).setExpanded(true);
        });
        expandableListView.setOnGroupCollapseListener(groupPosition -> {
            //makeToast(String.valueOf(adapter.getGroup(groupPosition)) + " List Collapsed.");
            viewModel.getAllResEntries().get(groupPosition).setExpanded(false);
        });
        expandableListView.setOnChildClickListener(
                    (parent, v, groupPosition, childPosition, id) -> {
                        makeToast(String.valueOf(adapter.getChild(groupPosition, childPosition)));
                        return false;
                    }
                );
    }

    private void makeToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
    }
}
