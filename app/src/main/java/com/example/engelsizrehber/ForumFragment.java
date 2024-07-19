package com.example.engelsizrehber;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class ForumFragment extends Fragment {

    private RecyclerView recyclerView;
    private ForumAdapter adapter;

    private FloatingActionButton fab1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        fab1 = view.findViewById(R.id.fab1);

        Forum forum = new Forum();
        forum.baslik = "Merhaba, Engelsiz Rehber!";
        forum.yazar = "Eren";
        ArrayList<Forum> forum1 = new ArrayList<>(Arrays.asList(forum,forum,forum,forum,forum,forum));

        adapter = new ForumAdapter(getActivity(), forum1);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddForum.class);
                startActivity(intent);
            }
        });
        return view;
    }
}