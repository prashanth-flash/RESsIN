package com.android.ressin;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToDoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToDoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoFragment extends Fragment implements
        View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "data";
    private static final String ARG_PARAM2 = "position";
    private static final String TAG = "ToDoFragment";

    // TODO: Rename and change types of parameters
    private List<ToDoObject> myDataset = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    private DatabaseReference mDatabase;

    public ToDoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ToDoFragment newInstance(List<ToDoObject> data) {
        ToDoFragment fragment = new ToDoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_todo,container,false);
        initSearch(RootView);
        RootView.findViewById(R.id.fab).setOnClickListener(this);
        mRecyclerView = RootView.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return RootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myDataset.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    myDataset.add(new ToDoObject(ds.getKey(), ds.getValue(String.class)));
                }
                CardAdapter dAdapter = new CardAdapter(myDataset, getContext(), new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }

                    @Override
                    public void onTextFieldClick(View view) {

                    }

                    @Override
                    public void editClicked(int position) {

                    }

                    @Override
                    public void deleteClicked(int position) {
                    }

                    @Override
                    public void shiftClicked(int position) {

                    }
                });
                mRecyclerView.setAdapter(dAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toString());
            }
        };
        mDatabase.child("ToDo").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(dataListener);
    }
    private void initSearch(View RootView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = RootView.findViewById(R.id.search_bar);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getContext(), HomeActivity.class)));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                genDialog();
                break;
        }
    }

    private void genDialog() {
        DialogFragment fragment = new TextDialogFragment();
        fragment.show(getFragmentManager(), "Input");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
