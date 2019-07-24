package com.example.pitchperfectlyaccuratelypractice.model;

import android.content.Context;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.modeFragments.FragmentFactory;
import com.example.pitchperfectlyaccuratelypractice.modeFragments.ModeFragment;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Interval;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Song;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.IntervalModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.NoteGraphModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.NoteModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.PerModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.SongModeSetting;
import com.example.pitchperfectlyaccuratelypractice.perModeSetting.TriadModeSetting;
import com.example.pitchperfectlyaccuratelypractice.question.IntervalQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.NoteQuestion;
import com.example.pitchperfectlyaccuratelypractice.question.Question;
import com.example.pitchperfectlyaccuratelypractice.question.QuestionFactory;
import com.example.pitchperfectlyaccuratelypractice.tools.MyMidiTool;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


/**
 * model stores current config, current question, current fragment, current mode
 */
public class Model {
    private static final String TAG  = "Model";

    /**  stores current config */
    private Config currentConfig = new Config();
    /**  stores current question */
    private Question currentQuestion;
    /**  stores current current mode */
    private Mode currentMode;
    /**  stores current fragment */
    private ModeFragment currentFragment;

    private SongList songList;
    /**  factory to produce different fragments */
    private FragmentFactory fragmentFactory = new FragmentFactory();
    /**  factory to produce different questions */
    private QuestionFactory questionFactory = new QuestionFactory();
    /**  observers (to observe data change of this model) */
    private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

    private Context context;

    private static PerModeSetting perModeSettings[];

    /**
     *
     */
    public Model(Context ct) {
        context = ct;
        currentConfig = new Config();
        currentMode = Mode.NotePractice;
        currentQuestion = questionFactory.create(currentMode);
        currentFragment = fragmentFactory.create(currentMode);
        songList = new SongList();
        perModeSettings = new PerModeSetting[]{ new NoteModeSetting(), new NoteGraphModeSetting(), new IntervalModeSetting(), new TriadModeSetting(), new SongModeSetting() };
        setupSongs();
    }


    private void setupSongs() {
        addASong(R.raw.auld_lang_syne, "Auld Lang Syne");
        addASong(R.raw.london_bridge_is_falling_down, "London Bridge is Falling Down");
        addASong(R.raw.twinkle_twinkle_little_star, "Twinkle Twinkle Little Star");
        addASong(R.raw.carrying_you, "Carring You");
        addASong(R.raw.dango_daikazoku, "団子大家族");
    }



    private void addASong(int id, String title) {
        songList.add(new Song(id, title, MyMidiTool.getMidiFileFromId(context, id)));
    }

    public void setPerModeSetting(PerModeSetting p){
//        currentConfig.set_error_allowance_rate();
//        currentConfig.set_least_stable_time_in_milliseconds();
//        currentConfig.set_milli_seconds_to_show_correct();
//        currentConfig.set_flag_auto_playback_answer();
        switch (p.mode){
            case NotePractice:
                perModeSettings[0] = p;
                break;
            case NoteGraphPractice:
                perModeSettings[1] = p;
                break;
            case IntervalPractice:
                perModeSettings[2] = p;
                break;
            case TriadPractice:
                perModeSettings[3] = p;
                break;
            case SongPlaying:
                perModeSettings[4] = p;
                break;
            default:
                break;
        }
    }


    public static PerModeSetting getPerModeSettingWithMode(Mode mode){
        switch (mode) {
            case NotePractice:
                return perModeSettings[0];
            case NoteGraphPractice:
                return perModeSettings[1];
            case IntervalPractice:
                return perModeSettings[2];
            case TriadPractice:
                return perModeSettings[3];
            case SongPlaying:
                return perModeSettings[4];
            default:
                throw new AssertionError("access PerModeSetting out of boundary");
        }
    }

    /**
     * getter for current fragment
     * @return
     */
    public ModeFragment getCurrentFragment() {
        return currentFragment;
    }

    /**
     * change current fragment given current mode and notify observers
     */
    public void setCurrentFragmentUsingCurrentMode() {
        notifyListeners(this, "currentFragment", this.currentFragment, this.currentFragment = fragmentFactory.create(currentMode));
    }

    /**
     * set note pool in current question and notify observers
     * @param notes
     */
    public void setNotePool(Note[] notes) {
        Question oldQuestion = currentQuestion;
        currentQuestion.setNotePool(notes);
        notifyListeners(this, "currentQuestion", oldQuestion, this.currentQuestion);
    }

    /**
     * set note pool in current question and notify observers
     * @param intervals
     */
    public void setIntervalPool(Interval[] intervals) {
        if (currentMode != Mode.IntervalPractice) {
            throw new AssertionError("Please set interval pool when you are practicing interval");
        }
        Question oldQuestion = currentQuestion;
        ((IntervalQuestion)currentQuestion).setIntervalPool(intervals);
        notifyListeners(this, "currentQuestion", oldQuestion, this.currentQuestion);
    }

    /**
     * set current question and notify observers
     */
    public void setCurrentQuestion(Question q) {
        notifyListeners(this, "currentQuestion", this.currentQuestion, this.currentQuestion = q);
    }

    /**
     * getter for current question
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * set current config and notify observers
     */
    public void setCurrentConfig(Config config) {
        notifyListeners(this, "currentConfig", this.currentConfig, this.currentConfig = config);
    }

    /**
     * getter for current config
     */
    public Config getCurrentConfig() {
        return currentConfig;
    }

    /**
     * set current mode and notify observers
     */

    public void setCurrentMode(Mode mode) {
        notifyListeners(this, "currentMode", this.currentMode, this.currentMode = mode);
    }


    /**
     * getter for current mode
     */
    public Mode getCurrentMode() {
        return currentMode;
    }

    /**
     * add an observer
     * @param new_listener
     */
    public void addChangeListener(PropertyChangeListener new_listener) {
        listeners.add(new_listener);
    }

    /**
     * notify all observers about an object change
     * @param object
     * @param property
     * @param oldValue
     * @param newValue
     */
    private void notifyListeners(Object object, String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener listener: listeners) {
            listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public SongList getSongList() {
        return songList;
    }
}
