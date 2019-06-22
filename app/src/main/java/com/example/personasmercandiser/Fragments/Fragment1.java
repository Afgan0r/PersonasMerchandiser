package com.example.personasmercandiser.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.personasmercandiser.CreateProductActivity;
import com.example.personasmercandiser.DatabaseHelper;
import com.example.personasmercandiser.MainScreenActivity;
import com.example.personasmercandiser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment1 extends Fragment {

    FloatingActionButton addNewProductFAB, editProductsFAB, saveJobFAB;
    ExpandableListView expandableListView;
    View view;
    DatabaseHelper db;
    private int jobId;
    boolean isEdit;
    private String[] groupsTitle;


    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        addNewProductFAB = view.findViewById(R.id.addNewProductFAB);
        editProductsFAB = view.findViewById(R.id.EditProductsFAB);
        saveJobFAB = view.findViewById(R.id.SaveJobFab);
        db = new DatabaseHelper(getActivity());
        isEdit = false;

        Bundle bundle = this.getArguments();
        jobId = bundle.getInt("JobId", 0);

        // If job has products, then fill expandable list view
        if (db.getProductsName(jobId).length != 0) {
            fillList(jobId);
        }
        listeners();
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
        editProductsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupsTitle == null) {
                    Toast.makeText(getActivity(), "Список пуст!", Toast.LENGTH_SHORT).show();
                } else {
                    isEdit = !isEdit;
                    if (isEdit) {
                        Toast.makeText(getActivity(), "Выберите элемент для редактирования. Чтобы закончить редактирование нажмите на ту же самую кнопку.", Toast.LENGTH_LONG).show();
                        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Редактирование")
                                        .setMessage("Что вы хотите сделать?")
                                        .setPositiveButton("Удалить элемент", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db.deleteProduct(groupsTitle[groupPosition]);
                                                fillList(jobId);
                                            }
                                        })
                                        .setNegativeButton("Изменить элемент", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent createProductActivity = new Intent(getActivity(), CreateProductActivity.class);
                                                createProductActivity.putExtra("Nomenclature", groupsTitle[groupPosition]);
                                                createProductActivity.putExtra("JobId", jobId);
                                                db.deleteProduct(groupsTitle[groupPosition]);
                                                startActivity(createProductActivity);
                                            }
                                        })
                                        .show();
                                return false;
                            }
                        });
                    } else {
                        expandableListView.setOnGroupClickListener(null);
                    }
                }
            }
        });
        saveJobFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupsTitle == null) {
                    Toast.makeText(getActivity(), "Список пуст!", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Сохранение")
                            .setMessage("После сохранения вы не сможете изменить список и фото, вы уверены что хотите сохранить?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getActivity(), MainScreenActivity.class));
                                }
                            })
                            .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }
        });
    }

    public void fillList(int jobId) {
        groupsTitle = db.getProductsName(jobId);
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
