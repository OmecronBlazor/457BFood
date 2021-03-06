package com.example.app;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
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
    private ImageButton mUrlButton;
    private ImageView mImageButton;
    private ControllerView mControllerView;
    private EventElement mEvent;
    private int foodIter = 0;
    public static ArrayList<Food> mFoodList;
    public FoodRecommendationFragment thisFragment;
    protected Dialog dialog;
    private final String FEEDBACK = "Feedback";
    public List<ModificationParams> assessmentList = new ArrayList<ModificationParams>();
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
                showFoodStatGraphs();
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
        View rootView = inflater.inflate(R.layout.fragment_food_recommendation, container, false);

        mControllerView = new ControllerView(rootView.getContext(), false);
        mTitleTextView = (TextView) rootView.findViewById(R.id.title_text_view);
        mImageButton = (ImageView) rootView.findViewById(R.id.food_art_image_view);
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
                            Toast toast = Toast.makeText(thisFragment.getView().getContext(), "This is the start of the list.", Toast.LENGTH_SHORT);
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

    public void showFoodStatGraphs() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_food_stat_graphs);
        dialog.setTitle("Food Stats");
        BarGraphView sournessBarGraph = (BarGraphView) dialog.findViewById(R.id.sourness_bar_graph);
        BarGraphView saltinessBarGraph = (BarGraphView) dialog.findViewById(R.id.saltiness_bar_graph);
        BarGraphView sweetnessBarGraph = (BarGraphView) dialog.findViewById(R.id.sweetness_bar_graph);
        BarGraphView bitternessBarGraph = (BarGraphView) dialog.findViewById(R.id.bitterness_bar_graph);
        BarGraphView fattinessBarGraph = (BarGraphView) dialog.findViewById(R.id.fattiness_bar_graph);

        sournessBarGraph.setBarValue(mFoodList.get(foodIter).sourness());
        saltinessBarGraph.setBarValue(mFoodList.get(foodIter).saltiness());
        sweetnessBarGraph.setBarValue(mFoodList.get(foodIter).sweetness());
        bitternessBarGraph.setBarValue(mFoodList.get(foodIter).bitterness());
        fattinessBarGraph.setBarValue(mFoodList.get(foodIter).fattiness());

        dialog.show();
    }

    public void updateUIFor(final Food food) {
        mTitleTextView.setText(food.name());
        mTitleTextView.setEnabled(true);
        mTitleTextView.setSelected(true);
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

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?tbm=isch&q="+food.name()));
                startActivity(intent);
            }
        });
    }

    public Dialog showPreferenceSelectionDialog(final boolean nextFood){
        dialog = new Dialog(this.getActivity());
        dialog.setContentView(R.layout.dialog_food_feedback);
        dialog.setTitle(FEEDBACK);

        final RadioGroup sournessRadioGroup = (RadioGroup) dialog.findViewById(R.id.sourness_radio_group);
        final RadioGroup saltinessRadioGroup = (RadioGroup) dialog.findViewById(R.id.saltiness_radio_group);
        final RadioGroup sweetnessRadioGroup = (RadioGroup) dialog.findViewById(R.id.sweetness_radio_group);
        final RadioGroup bitternessRadioGroup = (RadioGroup) dialog.findViewById(R.id.bitterness_radio_group);
        final RadioGroup fattinessRadioGroup = (RadioGroup) dialog.findViewById(R.id.fattiness_radio_group);
        if(assessmentList.get(foodIter)!=null) {
            //Show assessed values if the food is already assessed, otherwise radio button to perfect
            if (assessmentList.get(foodIter).sourness() == ModificationType.TOO_MUCH) {
                ((RadioButton) (sournessRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(foodIter).sourness() == ModificationType.TOO_LOW) {
                ((RadioButton) (sournessRadioGroup.getChildAt(0))).setChecked(true);
            }
            if (assessmentList.get(foodIter).saltiness() == ModificationType.TOO_MUCH) {
                ((RadioButton) (saltinessRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(foodIter).saltiness() == ModificationType.TOO_LOW) {
                ((RadioButton) (saltinessRadioGroup.getChildAt(0))).setChecked(true);
            }
            if (assessmentList.get(foodIter).sweetness() == ModificationType.TOO_MUCH) {
                ((RadioButton) (sweetnessRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(foodIter).sweetness() == ModificationType.TOO_LOW) {
                ((RadioButton) (sweetnessRadioGroup.getChildAt(0))).setChecked(true);
            }
            if (assessmentList.get(foodIter).bitterness() == ModificationType.TOO_MUCH) {
                ((RadioButton) (bitternessRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(foodIter).bitterness() == ModificationType.TOO_LOW) {
                ((RadioButton) (bitternessRadioGroup.getChildAt(0))).setChecked(true);
            }
            if (assessmentList.get(foodIter).fattiness() == ModificationType.TOO_MUCH) {
                ((RadioButton) (fattinessRadioGroup.getChildAt(2))).setChecked(true);
            } else if (assessmentList.get(foodIter).fattiness() == ModificationType.TOO_LOW) {
                ((RadioButton) (fattinessRadioGroup.getChildAt(0))).setChecked(true);
            }
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


                assessmentList.get(foodIter).setSourness(ModificationType.getModificationType(selection_sourness));
                assessmentList.get(foodIter).setSaltiness(ModificationType.getModificationType(selection_saltiness));
                assessmentList.get(foodIter).setSweetness(ModificationType.getModificationType(selection_sweetness));
                assessmentList.get(foodIter).setBitterness(ModificationType.getModificationType(selection_bitterness));
                assessmentList.get(foodIter).setFattiness(ModificationType.getModificationType(selection_fattiness));


                dialog.dismiss();

                mAssessmentItem.setIcon(R.drawable.selectdata_icon_checked);

                if (nextFood) {
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

