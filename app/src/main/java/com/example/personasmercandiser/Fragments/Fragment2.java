package com.example.personasmercandiser.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personasmercandiser.R;

public class Fragment2 extends Fragment {

    View view;

    public Fragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO Create photo from user
        view = inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }
}
