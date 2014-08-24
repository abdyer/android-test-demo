package org.andydyer.androidtestdemo;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.andydyer.androidtestdemo.api.ApiService;
import org.andydyer.androidtestdemo.api.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by andy on 8/23/14.
 */
public class EventListFragment extends ListFragment implements Callback<List<Event>> {

    @Inject ApiService apiService;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DemoApplication.getInstance().inject(this);
        requestEvents();
    }

    private void requestEvents() {
        getActivity().setProgressBarIndeterminateVisibility(true);
        apiService.getEvents("google", this);
    }

    @Override
    public void success(List<Event> events, Response response) {
        getActivity().setProgressBarIndeterminateVisibility(false);
        setListShown(true);
        final EventsAdapter adapter = new EventsAdapter(getActivity(), events);
        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = adapter.getItem(position);
                launchWebViewActivity(event.getRepo().getName());
            }
        });
    }

    @Override
    public void failure(RetrofitError error) {
        getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(getActivity(), R.string.unable_to_fech_events, Toast.LENGTH_SHORT).show();
    }

    private class EventsAdapter extends ArrayAdapter<Event> {

        public EventsAdapter(Context context, List<Event> objects) {
            super(context, -1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.event_list_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Event event = getItem(position);
            String line1 = String.format("%s by %s", event.getType(), event.getActor().getLogin());
            holder.textLine1.setText(line1);
            holder.textLine2.setText(getRelativeTime(event.getCreatedAt()));
            Picasso.with(getContext()).load(event.getActor().getAvatarUrl()).into(holder.avatar);
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchWebViewActivity(event.getActor().getLogin());
                }
            });

            return convertView;
        }

        private String getRelativeTime(String isoDateTime) {
            try {
                TimeZone utc = TimeZone.getTimeZone("UTC");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                format.setTimeZone(utc);
                GregorianCalendar calendar = new GregorianCalendar(utc);
                calendar.setTime(format.parse(isoDateTime));
                return DateUtils.getRelativeTimeSpanString(calendar.getTimeInMillis(),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            } catch (ParseException e) {
                return isoDateTime;
            }
        }
    }

    class ViewHolder {

        @InjectView(R.id.event_list_item_avatar) ImageView avatar;
        @InjectView(R.id.event_list_item_text_line1) TextView textLine1;
        @InjectView(R.id.event_list_item_text_line2) TextView textLine2;

        public ViewHolder(View view) {
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
