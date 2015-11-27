package com.uni.kevintruong.flextime.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.models.GeoLocation;

/**
 * Created by kwan8 on 2015-11-17.
 */
public class GeoLocationAdapter extends ArrayAdapter<GeoLocation>
{
    public GeoLocationAdapter(Context context, GeoLocation[] resource)
    {
        super(context, R.layout.session_row, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View locationView = layoutInflater.inflate(R.layout.geolocation_row, parent, false);

        GeoLocation singleGeoLocation = getItem(position);
        TextView locationName = (TextView) locationView.findViewById(R.id.locationName);
        locationName.setText(singleGeoLocation.getName());

        return locationView;
    }
}
