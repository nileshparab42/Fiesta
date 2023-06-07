package com.example.fiesta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tables#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tables extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView firstyear;

    public Tables() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tables.
     */
    // TODO: Rename and change types and number of parameters
    public static Tables newInstance(String param1, String param2) {
        Tables fragment = new Tables();
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
        View view =  inflater.inflate(R.layout.fragment_tables, container, false);
        firstyear=view.findViewById(R.id.firstyear);
        firstyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String filepath="https://firebasestorage.googleapis.com/v0/b/fiesta-36c5b.appspot.com/o/Timetables%2FTE%20.pdf?alt=media&token=77cde635-34a7-482a-a209-6aaa6ba76bab";
                intent.setDataAndType(Uri.parse(filepath), "application/pdf");
                startActivity(intent);


//                Intent intent = new Intent(getContext(),PdfActivity.class);
////                tid = arrPreticket.get(position).getTid();
////                intent.putExtra("tid",tid);
//                startActivity(intent);
            }
        });
        return view;
    }

}