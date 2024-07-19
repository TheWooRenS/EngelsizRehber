package com.example.engelsizrehber;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class EventsFragment extends Fragment {

    EventsAdapter adapter;

    ArrayList<Etkinlik> etkinlikler = new ArrayList<>(
            Arrays.asList(
                    new Etkinlik("satranç","turnuva yapılacak","12.05.2024","15.05.2024","Pendik")
            )

    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events,container,false);

        RecyclerView recyclerView1 = v.findViewById(R.id.recyclerview1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new EventsAdapter(v.getContext(),etkinlikler);
        recyclerView1.setAdapter(adapter);

        return v;
    }
}