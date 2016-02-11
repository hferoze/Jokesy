package com.nanodegree.android.jokesy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokesyFullMainFragment extends Fragment {

    private TextView mBuildVarTextView;

    public JokesyFullMainFragment() {
    }

    public static JokesyFullMainFragment getInstance(){
        JokesyFullMainFragment jokesyMainFragment = new JokesyFullMainFragment();
        return jokesyMainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mBuildVarTextView = (TextView) root.findViewById(R.id.build_var_textView);

        mBuildVarTextView.setText(getActivity().getApplicationContext().getPackageName());

        return root;
    }
}
