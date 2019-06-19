package com.example.personasmercandiser.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.personasmercandiser.R;

public class Fragment1 extends Fragment {

    FloatingActionButton addNewProductFAB;
    ExpandableListView expandableListView;
    View view;

    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        expandableListView = view.findViewById(R.id.ProductListView);
        addNewProductFAB = view.findViewById(R.id.AddNewProductFAB);


        return view;
    }

}
