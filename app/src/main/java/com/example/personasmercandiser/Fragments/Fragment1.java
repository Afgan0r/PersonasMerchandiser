package com.example.personasmercandiser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.example.personasmercandiser.CreateProductActivity;
import com.example.personasmercandiser.DatabaseHelper;
import com.example.personasmercandiser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment1 extends Fragment {

    FloatingActionButton addNewProductFAB;
    ExpandableListView expandableListView;
    View view;
    DatabaseHelper db;
    private int jobId;


    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        addNewProductFAB = view.findViewById(R.id.AddNewProductFAB);
        db = new DatabaseHelper(getActivity());

        Bundle bundle = this.getArguments();
        jobId = 0;
        if (bundle != null) {
            jobId = bundle.getInt("JobId", 0);
        }

        listeners();
        // If job has products, then fill expandable list view
        if (db.getProductsName(jobId).length != 0) {
            fillList(jobId);
        }
        return view;
    }

    private void listeners() {
        addNewProductFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createProductActivity = new Intent(getActivity(), CreateProductActivity.class);
                createProductActivity.putExtra("JobId", jobId);
                startActivity(createProductActivity);
            }
        });
    }

    public void fillList(int jobId) {
        String[] groupsTitle = db.getProductsName(jobId);
        expandableListView = view.findViewById(R.id.ProductListView);

        // Map for group titles
        ArrayList<Map<String, String>> groupData;
        // Map for one item in group
        ArrayList<Map<String, String>> childDataItem;
        // Map for all items in group
        ArrayList<ArrayList<Map<String, String>>> childData;
        // Map for key and value pair
        Map<String, String> m;

        groupData = new ArrayList<Map<String, String>>();
        for (String currGroup : groupsTitle) {
            m = new HashMap<String, String>();
            m.put("groupName", currGroup);
            groupData.add(m);
        }

        // Describe key for group titles
        String[] groupFrom = new String[]{"groupName"};
        int[] groupTo = new int[]{android.R.id.text1};

        childData = new ArrayList<ArrayList<Map<String, String>>>();
        // Array for items in one group
        String[] elements;

        for (String currGroup : groupsTitle) {
            childDataItem = new ArrayList<Map<String, String>>();
            // Receiving count and price for product
            elements = db.getProductInf(currGroup);

            m = new HashMap<String, String>();
            m.put("elemInf", "Количество - " + elements[0]);
            childDataItem.add(m);

            m = new HashMap<String, String>();
            m.put("elemInf", "Цена за штуку - " + elements[1]);
            childDataItem.add(m);

            childData.add(childDataItem);
        }

        // Describe key for items
        String[] childFrom = new String[]{"elemInf"};
        int[] childTo = new int[]{android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                getContext(),
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo
        );
        expandableListView.setAdapter(adapter);
    }

}
