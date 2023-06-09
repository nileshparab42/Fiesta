package com.example.fiesta;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Groups#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Groups extends Fragment {

    CardView card;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Groups() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Groups.
     */
    // TODO: Rename and change types and number of parameters
    public static Groups newInstance(String param1, String param2) {
        Groups fragment = new Groups();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_groups, container, false);
        CardView card1 = (CardView) v.findViewById(R.id.card1);
        CardView card2 = (CardView) v.findViewById(R.id.card2);
        CardView card3 = (CardView) v.findViewById(R.id.card3);
        CardView card4 = (CardView) v.findViewById(R.id.card4);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent com = new Intent(getActivity(),Group.class);
                startActivity(com);

                Intent intent = new Intent(getContext(),Group.class);
                intent.putExtra("gname","College Committee");
                intent.putExtra("title","College \nCommittee");
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent com = new Intent(getActivity(),Group.class);
                startActivity(com);

                Intent intent = new Intent(getContext(),Group.class);
                intent.putExtra("gname","Technical Committee");
                intent.putExtra("title","Technical \nCommittee");
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent com = new Intent(getActivity(),Group.class);
                startActivity(com);

                Intent intent = new Intent(getContext(),Group.class);
                intent.putExtra("gname","Teachers Committee");
                intent.putExtra("title","Teachers \nCommittee");
                startActivity(intent);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent com = new Intent(getActivity(),Group.class);
                startActivity(com);

                Intent intent = new Intent(getContext(),Group.class);
                intent.putExtra("gname","Sports Committee");
                intent.putExtra("title","Sports \nCommittee");
                startActivity(intent);
            }
        });
        return v;
    }

}