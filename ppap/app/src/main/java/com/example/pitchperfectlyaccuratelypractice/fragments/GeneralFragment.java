package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.activities.NoteModeFilterPageActivity;
import com.example.pitchperfectlyaccuratelypractice.common.Controller;
import com.example.pitchperfectlyaccuratelypractice.common.NotePlayer;

/**
 * general fragment, its children are notefragment, intervalfragment, triadfragment, notegraphfragment
 * <p>
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TriadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriadFragment#newInstance} factory method to
 * create an instance of this fragment.
 * </p>
 */

// This is a factory as it produces the fragment and these are optionally overridden by other classes
// This factory is used in the MainActivity
public class GeneralFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * request code got from mainactivity
     */
    // TODO put in Config or ...
    private static final int REQUEST_CODE_FROM_FILTER = MainActivity.REQUEST_CODE_FROM_FILTER;

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

    /**
     * stores the controller got from MainActivity
     */
    private Controller controller;

    /**
     * stores the play sound Button view
     */
    Button playSoundButton;

    /**
     * stores the help Button view
     */
    Button helpButton;

    /**
     * stores the navi menu button view (FIXME do we need it)
     */
    Button naviMenuButton;

    /**
     * stores the filter page button view
     */
    Button filterPageButton;

    boolean onCreated = false;

    /**
     * stores the notePlayer got from MainActivity
     */
    NotePlayer notePlayer;

    /**
     * R.layout.whateverlayout, set in the constructor of children
     */
    int resource;

    int background_color;

    ConstraintLayout constraintLayout;

    /**
     * additional things to set up in onCreateView
     * <p>
     *     called by onCreateView below, it is benefitial
     *     because a lot of fragment has same views need to set up
     * </p>
     */
    void setupAdditionalView() { }

    public void setBackgroundColor(int c) {
        constraintLayout.setBackgroundColor(c);
    }

    public void resetBackgroundColor() {
        constraintLayout.setBackgroundColor(background_color);
    }

    /**
     * setup views and listeners
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("PEPE", "" + this.getClass() + "Fragment onCreateView!");

        controller = ((MainActivity)(getActivity())).getController(); // FIXME temporary here
        notePlayer = ((MainActivity)(getActivity())).getNotePlayer(); // FIXME temporary here

        onCreated = true;
        View view = inflater.inflate(resource, container, false);
        constraintLayout = view.findViewById(R.id.layout_to_include);
        frequencyText = constraintLayout.findViewById(R.id.currentFrequencyTextView);

        arrowText = constraintLayout.findViewById(R.id.arrowTextView);
        currentPitchText  = constraintLayout.findViewById(R.id.currentPitchTextView);

        playSoundButton = constraintLayout.findViewById(R.id.playSoundButton);
        helpButton = constraintLayout.findViewById(R.id.helpButton);
        naviMenuButton = constraintLayout.findViewById(R.id.naviButton);
        filterPageButton = constraintLayout.findViewById(R.id.filterButton);

        if (constraintLayout == null || frequencyText == null ||  arrowText == null
        || currentPitchText == null || playSoundButton == null || helpButton == null
        || naviMenuButton == null || filterPageButton == null) {
            throw new AssertionError("Fragment onCreatView, some view is null");
        }

        // FIXME need to generalize
        playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notePlayer.playOneNote((int) controller.getExpectedFrequencies()[0]);
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
        // currently next question here
        helpButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.next_question();
                return false;
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            boolean click = true;
            View layout = inflater.inflate(R.layout.popout, container, false);
            PopupWindow popupWindow = new PopupWindow(layout, 300, 190, true);

            @Override
            public void onClick(View view) {

                if (click) {
//                    popupWindow.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                    popupWindow.update(50, 50, 300, 80);
                    click = false;
//                popupText = (TextView) layout.findViewById(R.id.popupText);
//                popupText.setText(yourString);
                } else {
                    popupWindow.dismiss();
                    click = true;
                }
            }
        });

        // additional things goes here
        setupAdditionalView();

        return view;
    }

    /**
     * empty public constructor
     */
    public GeneralFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * </p>
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

    /**
     * on create for a fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    /**
     * fragment on attach
     * @param context
     */
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

    /**
     * fragment on detach
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * fragment on resume
     */
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
     * future use, does nothing for now
     * <p>
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * </p>
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * update frequency text view
     * @param freq
     * @param expected
     */
    public void updateFrequencyText(Long freq, Double expected){
        if(!onCreated) return;
        frequencyText.setText(Long.toString(freq) + " Hz");
//        Log.d("", "updateFrequencyText: " + freq);
    }

    /**
     * update arrow text view (TODO need to generalize for tirad mode)
     * @param myString
     */
    public void updateArrowText(String myString){
        if(!onCreated) return;
        arrowText.setText(myString);
    }


    /**
     * update current pitch text showing on the right top corner
     * @param myString
     */
    public void updateCurrentPitchText(String myString){
        if(!onCreated) return;
        currentPitchText.setText(myString);
    }

    /**
     * update question texts, does nothing,
     * please do specific things in child
     * <p>
     *     A note has one text ("A4")
     *     A interval has two texts ("A4", "+ m3")
     *     A triad has three texts  ("C3", "E3", "G3")
     * </p>
     * @param texts
     */
    public void updateQuestionTexts(String[] texts){
    }


    /**
     * update arrow animation (TODO need to generalize for triad mode)
     * @param myAnimation
     */
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        arrowText.setAnimation(myAnimation);
    }

}
