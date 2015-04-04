package com.example.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.EventElement;
import algorithm.Food;
import algorithm.ModificationParams;
import algorithm.ModificationType;

/**
 * Created by Steven on 2015-03-18.
 */
public class FoodRecommendationFragment extends Fragment {

    private TextView mTitleTextView;
    private TextView mArtistTextView;
    private ImageButton mUrlButton;
    private ControllerView mControllerView;
    private EventElement mEvent;
    private int foodIter = 0;
    public static ArrayList<Food> mFoodList;
    public FoodRecommendationFragment thisFragment;
    protected Dialog dialog;
    private final String FEEDBACK = "Feedback";
    public List<ModificationParams> assessmentList = new ArrayList<ModificationParams>();
    boolean manualPrefChoice = false;
    protected MenuItem mAssessmentItem;

    public Menu mMenu;

    public FoodRecommendationFragment(){}

    public FoodRecommendationFragment(ArrayList<Food> foodList, EventElement event) {
        this.mEvent = event;
        Collections.shuffle(foodList);
        this.mFoodList = foodList;
        this.thisFragment = this;
        for(int x=0;x<foodList.size();x++) {
            ModificationParams newone = new ModificationParams();
            this.assessmentList.add(newone);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        mMenu = menu;
        this.mAssessmentItem = this.mMenu.findItem(R.id.action_feedback);
        updateUIFor(mFoodList.get(foodIter));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                showPreferenceSelectionDialog(false);
                return true;
            case R.id.action_graphs:
                showSongStatGraphs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_external_player, container, false);

        mControllerView = new ControllerView(rootView.getContext(), false);
        mTitleTextView = (TextView) rootView.findViewById(R.id.title_text_view);
        mUrlButton = (ImageButton) rootView.findViewById(R.id.url_link_button);
        ((MainActivity) getActivity()).setActionBarTitle(mEvent.event_name());

        mControllerView.setAnchorView((FrameLayout) rootView.findViewById(R.id.controller_view_container));
        mControllerView.setUpForExternalPlayer();
        mControllerView.setPrevNextListeners(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!assessmentList.get(foodIter).isNull()) {
                            updatePreferences(mEvent, mFoodList.get(foodIter),
                                assessmentList.get(foodIter).sourness().mod_name(),
                                assessmentList.get(foodIter).saltiness().mod_name(),
                                assessmentList.get(foodIter).sweetness().mod_name(),
                                assessmentList.get(foodIter).bitterness().mod_name(),
                                assessmentList.get(foodIter).fattiness().mod_name());
                            if (foodIter == mFoodList.size() - 1) {
                                foodIter++;
                                //get next recommendation
                                ArrayList<Food> list = MainActivity.dbhandler.getRecommendation(mEvent.event_name());
                                Collections.shuffle(list);
                                mFoodList.addAll(list);
                            } else {
                                foodIter++;
                                updateUIFor(mFoodList.get(foodIter));
                            }
                        } else {
                            showPreferenceSelectionDialog(true);
                        }
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (foodIter == 0) {
                            Toast toast = Toast.makeText(thisFragment.getView().getContext(), "This is the start of the playlist.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        }
                        foodIter--;
                        //setURL
                        updateUIFor(mFoodList.get(foodIter));
                        System.out.println(mFoodList.get(foodIter).name());
                        //setText
                    }
                }
        );
        mControllerView.show(0);
        return rootView;
    }

    public void showSongStatGraphs() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_song_stat_graphs);
        dialog.setTitle("Song Stats");
        /*BarGraphView heavinessBarGraph = (BarGraphView) dialog.findViewById(R.id.heaviness_bar_graph);
        BarGraphView tempoBarGraph = (BarGraphView) dialog.findViewById(R.id.tempo_bar_graph);
        BarGraphView complexityBarGraph = (BarGraphView) dialog.findViewById(R.id.complexity_bar_graph);*/

        //heavinessBarGraph.setBarValue(mFoodList.get(songIter).heaviness());
        //tempoBarGraph.setBarValue(mFoodList.get(songIter).tempo());
        //complexityBarGraph.setBarValue(mFoodList.get(songIter).complexity());

        dialog.show();}

    public void updateUIFor(final Food food) {
        mTitleTextView.setText(food.name());
        mTitleTextView.setEnabled(true);
        mTitleTextView.setSelected(true);
        /*mArtistTextView.setText(song.artist());
        mArtistTextView.setEnabled(true);
        mArtistTextView.setSelected(true);*/
        if(assessmentList.get(foodIter).isNull()){
            mAssessmentItem.setIcon(R.drawable.selectdata_icon);
        }
        else{
            mAssessmentItem.setIcon(R.drawable.selectdata_icon_checked);
        }

        mUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, food.name());
                startActivity(intent);
            }
        });
    }

    public Dialog showPreferenceSelectionDialog(final boolean nextSong){
        dialog = new Dialog(this.getActivity());
        dialog.setContentView(R.layout.dialog_song_feedback);
        dialog.setTitle(FEEDBACK);

        final RadioGroup sournessRadioGroup = (RadioGroup) dialog.findViewById(R.id.sourness_radio_group);
        final RadioGroup saltinessRadioGroup = (RadioGroup) dialog.findViewById(R.id.saltiness_radio_group);
        final RadioGroup sweetnessRadioGroup = (RadioGroup) dialog.findViewById(R.id.sweetness_radio_group);
        final RadioGroup bitternessRadioGroup = (RadioGroup) dialog.findViewById(R.id.bitterness_radio_group);
        final RadioGroup fattinessRadioGroup = (RadioGroup) dialog.findViewById(R.id.fattiness_radio_group);
        if(assessmentList.get(foodIter)!=null) {
            //Show assessed values if the song is already assessed, otherwise radio button to perfect
            /*if (assessmentList.get(songIter).getH() == ModificationType.TOO_MUCH) {
                ((RadioButton) (heavinessRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(songIter).getH() == ModificationType.TOO_LOW) {
                ((RadioButton) (heavinessRadioGroup.getChildAt(0))).setChecked(true);
            }
            if (assessmentList.get(songIter).getT() == ModificationType.TOO_MUCH) {
                ((RadioButton) (tempoRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(songIter).getT() == ModificationType.TOO_LOW) {
                ((RadioButton) (tempoRadioGroup.getChildAt(0))).setChecked(true);
            }
            if (assessmentList.get(songIter).getC() == ModificationType.TOO_MUCH) {
                ((RadioButton) (complexityRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(songIter).getC() == ModificationType.TOO_LOW) {
                ((RadioButton) (complexityRadioGroup.getChildAt(0))).setChecked(true);
            }*/
        }
        Button submitButton = (Button) dialog.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selection_sourness = ((RadioButton) dialog.findViewById(sournessRadioGroup.getCheckedRadioButtonId())).getText().toString();
                String selection_saltiness = ((RadioButton) dialog.findViewById(saltinessRadioGroup.getCheckedRadioButtonId())).getText().toString();
                String selection_sweetness = ((RadioButton) dialog.findViewById(sweetnessRadioGroup.getCheckedRadioButtonId())).getText().toString();
                String selection_bitterness = ((RadioButton) dialog.findViewById(bitternessRadioGroup.getCheckedRadioButtonId())).getText().toString();
                String selection_fattiness = ((RadioButton) dialog.findViewById(fattinessRadioGroup.getCheckedRadioButtonId())).getText().toString();


                assessmentList.get(foodIter).setSourness(ModificationType.getModificationType(selection_sourness)); //getModPreferences(HEAVINESS_TYPE, selection_heaviness);
                assessmentList.get(foodIter).setSaltiness(ModificationType.getModificationType(selection_saltiness));//getModPreferences(TEMPO_TYPE, selection_tempo);
                assessmentList.get(foodIter).setSweetness(ModificationType.getModificationType(selection_sweetness));//getModPreferences(COMPLEXITY_TYPE, selection_complexity);
                assessmentList.get(foodIter).setBitterness(ModificationType.getModificationType(selection_bitterness));//getModPreferences(COMPLEXITY_TYPE, selection_complexity);
                assessmentList.get(foodIter).setFattiness(ModificationType.getModificationType(selection_fattiness));//getModPreferences(COMPLEXITY_TYPE, selection_complexity);


                dialog.dismiss();

                mAssessmentItem.setIcon(R.drawable.selectdata_icon_checked);

                if (nextSong) {
                    updatePreferences(mEvent, mFoodList.get(foodIter),
                            assessmentList.get(foodIter).sourness().mod_name(),
                            assessmentList.get(foodIter).saltiness().mod_name(),
                            assessmentList.get(foodIter).sweetness().mod_name(),
                            assessmentList.get(foodIter).bitterness().mod_name(),
                            assessmentList.get(foodIter).fattiness().mod_name());
                    if (foodIter == mFoodList.size() - 1) {
                            foodIter++;
                            //get a new recommendation
                            ArrayList<Food> list = MainActivity.dbhandler.getRecommendation(mEvent.event_name());
                            Collections.shuffle(list);
                            mFoodList.addAll(list);
                    } else {
                        foodIter++;
                        updateUIFor(mFoodList.get(foodIter));
                    }
                }
            }
        });

        dialog.show();
        return dialog;
    }

    protected void updatePreferences(EventElement eventElement, Food food, String sourness_pref,
                                     String saltiness_pref, String sweetness_pref, String bitterness_pref,
                                     String fattiness_pref){

        eventElement.UpdateAllPreferences(food,
                ModificationType.getModificationType(sourness_pref),
                ModificationType.getModificationType(saltiness_pref),
                ModificationType.getModificationType(sweetness_pref),
                ModificationType.getModificationType(bitterness_pref),
                ModificationType.getModificationType(fattiness_pref));

       MainActivity.dbhandler.updateEvent(eventElement);

        mEvent = eventElement;
    }


}

