package com.nanodegree.android.jokesy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class JokesyMainFragment extends Fragment {

    private TextView mBuildVarTextView;

    public JokesyMainFragment() {
    }

    public static JokesyMainFragment getInstance(){
        JokesyMainFragment jokesyMainFragment = new JokesyMainFragment();
        return jokesyMainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mBuildVarTextView = (TextView) root.findViewById(R.id.build_var_textView);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.
                Builder().addTestDevice("E01C7FF09832E03B2A72363D23F13F39").
                build();
        mAdView.loadAd(adRequest);

        mBuildVarTextView.setText(getActivity().getApplicationContext().getPackageName());

        return root;
    }
}
