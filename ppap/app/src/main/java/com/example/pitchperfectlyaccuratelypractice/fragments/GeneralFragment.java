package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.activities.NoteModeFilterPageActivity;
import com.example.pitchperfectlyaccuratelypractice.activities.NotePlayer;
import com.example.pitchperfectlyaccuratelypractice.activities.updateViewInterface;
import com.example.pitchperfectlyaccuratelypractice.common.ModelController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TriadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralFragment extends Fragment implements updateViewInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // TODO put in Config or ...
    private static final int REQUEST_CODE_FROM_FILTER = MainActivity.REQUEST_CODE_FROM_FILTER;

    /**
     * stores questionTextView
     */
    /**
     * stores arrowsTextView
     */
    TextView arrowText;
    /**
     * stores frequencyTextView
     */
    TextView frequencyText;
    /**
     * stores currentPitchTextView
     */
    TextView currentPitchText;

    ModelController modelController;

    Button playSoundButton;

    Button helpButton;

    Button naviMenuButton;

    Button filterPageButton;

    boolean onCreated = false;

    NotePlayer notePlayer;


    /**
     * R.layout.whateverlayout, set in the constructor of children
     */
    int resource;

    ConstraintLayout constraintLayout;

    void setupAdditionalView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("PEPE", "" + this.getClass() + "Fragment onCreateView!");

        modelController = ((MainActivity)(getActivity())).getModelController(); // FIXME temporary here
        notePlayer = ((MainActivity)(getActivity())).getNotePlayer(); // FIXME temporary here

        onCreated = true;
        View view = inflater.inflate(resource, container, false);
        constraintLayout = view.findViewById(R.id.note_include);
        frequencyText = constraintLayout.findViewById(R.id.currentFrequencyTextView);

        arrowText = constraintLayout.findViewById(R.id.arrowTextView1);
        currentPitchText  = constraintLayout.findViewById(R.id.currentPitchTextView);

        playSoundButton = constraintLayout.findViewById(R.id.playSoundButton);
        helpButton = constraintLayout.findViewById(R.id.helpButton);
        naviMenuButton = constraintLayout.findViewById(R.id.naviButton);
        filterPageButton = constraintLayout.findViewById(R.id.filterButton);

        setupAdditionalView();

        if (constraintLayout == null || frequencyText == null ||  arrowText == null
        || currentPitchText == null || playSoundButton == null || helpButton == null
        || naviMenuButton == null || filterPageButton == null) {
            throw new AssertionError("Fragment onCreatView, some view is null");
        }

        // FIXME need to generalize
        playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notePlayer.playOneNote((int)modelController.getExpectedFrequencies()[0]);
            }
        });

        filterPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filter_intent = new Intent(getActivity(), NoteModeFilterPageActivity.class);

                // let the main activity handle the intent
                getActivity().startActivityForResult(filter_intent, REQUEST_CODE_FROM_FILTER);
            }
        });

        // TODO help button and navibutton
        helpButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                modelController.next_question();
                return false;
            }
        });

        return view;
    }

    public GeneralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralFragment newInstance(String param1, String param2) {
        GeneralFragment fragment = new GeneralFragment();
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



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
    public void onResume() {
        Button button = getView().findViewById(R.id.naviButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        super.onResume();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateFrequencyText(Long freq, Double expected){
        if(!onCreated) return;
        frequencyText.setText(Long.toString(freq) + " Hz");
//        Log.d("", "updateFrequencyText: " + freq);
    }

    public void updateArrowText(String myString){
        if(!onCreated) return;
        arrowText.setText(myString);
    }

    public void updateCurrentPitchText(String myString){
        if(!onCreated) return;
        currentPitchText.setText(myString);
    }

    public void updateQuestionTexts(String[] texts){
    }



    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        arrowText.setAnimation(myAnimation);
    }

}
