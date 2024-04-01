package org.clkr.mobile.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.clkr.mobile.R;
import org.clkr.mobile.model.Presentation;

import java.util.List;

public class PresentationAdapter extends ArrayAdapter<Presentation> {

    public PresentationAdapter(@NonNull Context context, @NonNull List<Presentation> presentations) {
        super(context, R.layout.item_presentation, presentations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_presentation, null);
        }

        //TODO: convertView.findViewById(R.id.deleteGroupButton);

        return convertView;
    }
}
