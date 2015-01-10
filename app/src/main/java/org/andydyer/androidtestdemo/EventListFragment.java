package org.andydyer.androidtestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.andydyer.androidtestdemo.api.ApiService;
import org.andydyer.androidtestdemo.api.Event;
import org.andydyer.androidtestdemo.api.Events;
import org.andydyer.androidtestdemo.ui.widgets.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by andy on 8/23/14.
 */
public class EventListFragment extends Fragment implements Callback<Events> {

    @Inject ApiService apiService;

    @InjectView(android.R.id.list) RecyclerView recyclerView;

    private EventsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DemoApplication.getInstance().inject(this);

        if(savedInstanceState == null) {
            adapter = new EventsAdapter();
            //TODO: Show progress spinner while loading
            apiService.getEvents("google", this);
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void success(Events events, Response response) {
        adapter.addAll(events);
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), R.string.unable_to_fech_events, Toast.LENGTH_SHORT).show();
    }

    private class EventsAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Event> events = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Event event = events.get(position);
            String line1 = String.format("%s by %s", event.getType(), event.getActor().getLogin());
            holder.textLine1.setText(line1);
            holder.textLine2.setText(event.getCreatedAtRelativeTime());
            Picasso.with(getActivity()).load(event.getActor().getAvatarUrl()).into(holder.avatar);
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchWebViewActivity(event.getActor().getLogin());
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchWebViewActivity(event.getRepo().getName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        public void addAll(List<Event> items) {
            int startPosition = events.size();
            events.addAll(items);
            notifyItemRangeInserted(startPosition, items.size());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.event_list_item_avatar) ImageView avatar;
        @InjectView(R.id.event_list_item_text_line1) TextView textLine1;
        @InjectView(R.id.event_list_item_text_line2) TextView textLine2;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    private void launchWebViewActivity(String path) {
        String url = String.format("http://github.com/%s", path);
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_TITLE, path);
        intent.putExtra(WebViewActivity.EXTRA_URL, url);
        startActivity(intent);
    }
}
