package com.example.opgrad;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterFragmentThird extends Fragment {

    private TextView pageNumber;
    private EditText score;

    private TextView previous;
    private TextView next;

    private Button finishBtn;

    private String firstScore = "";
    private String secondScore = "";
    private String thirdScore = "";
    private String fourthScore = "";
    private String fifthScore = "";

    private TextView distance;
    private TextView numberOfEntries;

    private View mindABar;
    private View mindBBar;
    private TextView mindAText;
    private TextView mindBText;
    private View energyABar;
    private View energyBBar;
    private TextView energyAText;
    private TextView energyBText;
    private View natureABar;
    private View natureBBar;
    private TextView natureAText;
    private TextView natureBText;
    private View tacticsABar;
    private View tacticsBBar;
    private TextView tacticsAText;
    private TextView tacticsBText;
    private View selfABar;
    private View selfBBar;
    private TextView selfAText;
    private TextView selfBText;

    private LinearLayout.LayoutParams barParams;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_third, null);

        pageNumber = (TextView) view.findViewById(R.id.pageNumber_register_third);
        score = (EditText) view.findViewById(R.id.score_register_third);

        //점수는 0~100점 사이만 입력 가능
        score.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        distance = (TextView) view.findViewById(R.id.distance_register_third);
        numberOfEntries = (TextView) view.findViewById(R.id.numberOfEntries_register_third);

        mindABar = (View) view.findViewById(R.id.mindABar_register_third);
        mindBBar = (View) view.findViewById(R.id.mindBBar_register_third);
        mindAText = (TextView) view.findViewById(R.id.mindAText_register_third);
        mindBText = (TextView) view.findViewById(R.id.mindBText_register_third);
        energyABar = (View) view.findViewById(R.id.energyABar_register_third);
        energyBBar = (View) view.findViewById(R.id.energyBBar_register_third);
        energyAText = (TextView) view.findViewById(R.id.energyAText_register_third);
        energyBText = (TextView) view.findViewById(R.id.energyBText_register_third);
        natureABar = (View) view.findViewById(R.id.natureABar_register_third);
        natureBBar = (View) view.findViewById(R.id.natureBBar_register_third);
        natureAText = (TextView) view.findViewById(R.id.natureAText_register_third);
        natureBText = (TextView) view.findViewById(R.id.natureBText_register_third);
        tacticsABar = (View) view.findViewById(R.id.tacticsABar_register_third);
        tacticsBBar = (View) view.findViewById(R.id.tacticsBBar_register_third);
        tacticsAText = (TextView) view.findViewById(R.id.tacticsAText_register_third);
        tacticsBText = (TextView) view.findViewById(R.id.tacticsBText_register_third);
        selfABar = (View) view.findViewById(R.id.selfABar_register_third);
        selfBBar = (View) view.findViewById(R.id.selfBBar_register_third);
        selfAText = (TextView) view.findViewById(R.id.selfAText_register_third);
        selfBText = (TextView) view.findViewById(R.id.selfBText_register_third);

        previous = (TextView) view.findViewById(R.id.previous_register_third);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageNum = pageNumber.getText().toString();
                if(pageNum.equals("1")) {

                } else {
                    saveScore(pageNum);
                    if(scoreCheck(pageNum)) {
                        if(pageNum.equals("2")) {
                            changePage("1");
                        } else if(pageNum.equals("3")) {
                            changePage("2");
                        } else if(pageNum.equals("4")) {
                            changePage("3");
                        } else if(pageNum.equals("5")) {
                            changePage("4");
                        }
                    } else {
                        ((RegisterActivity)getActivity()).showNegativeDialog("점수를 입력하세요.");
                    }
                }
            }
        });
        next = (TextView) view.findViewById(R.id.next_register_third);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageNum = pageNumber.getText().toString();
                if(pageNum.equals("5")) {

                } else {
                    saveScore(pageNum);
                    if(scoreCheck(pageNum)) {
                        if(pageNum.equals("4")) {
                            changePage("5");
                        } else if(pageNum.equals("3")) {
                            changePage("4");
                        } else if(pageNum.equals("2")) {
                            changePage("3");
                        } else if(pageNum.equals("1")) {
                            changePage("2");
                        }
                    } else {
                        ((RegisterActivity)getActivity()).showNegativeDialog("점수를 입력하세요.");
                    }
                }
            }
        });

        finishBtn = (Button) view.findViewById(R.id.finishBtn_register_third);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageNum = pageNumber.getText().toString();
                saveScore(pageNum);
                if(scoreCheck("1") && scoreCheck("2") && scoreCheck("3") && scoreCheck("4") && scoreCheck("5")) {
                    ((RegisterActivity)getActivity()).evaluate1 =firstScore;
                    ((RegisterActivity)getActivity()).evaluate2 =secondScore;
                    ((RegisterActivity)getActivity()).evaluate3 =thirdScore;
                    ((RegisterActivity)getActivity()).evaluate4 =fourthScore;
                    ((RegisterActivity)getActivity()).evaluate5 =fifthScore;
                    ((RegisterActivity)getActivity()).registerSuccess();
                } else {
                    ((RegisterActivity)getActivity()).showNegativeDialog("평가를 마친 후 시도해주세요.");
                }
            }
        });

        return view;
    }

    public void saveScore(String pageNum) {
        switch(pageNum) {
            case "1":
                firstScore = score.getText().toString();
                break;
            case"2":
                secondScore = score.getText().toString();
                break;
            case"3":
                thirdScore = score.getText().toString();
                break;
            case"4":
                fourthScore = score.getText().toString();
                break;
            case"5":
                fifthScore = score.getText().toString();
                break;
        }
    }

    public void changePage(String pageNum) {
        pageNumber.setText(pageNum);
        ((RegisterActivity)getActivity()).reomveKeyboard();
        //pageNum에 맞는 가상 프로필 설정 + 저장되어있던 이전의 score 불러옴
        switch(pageNum) {
            case "1":
                distance.setText("3km");
                numberOfEntries.setText("3~4회");
                setPersonality(20, 10, 40, 75, 10);

                score.setText(firstScore);
                break;
            case"2":
                distance.setText("50km");
                numberOfEntries.setText("0회");
                setPersonality(70, 55, 90, 40, 85);

                score.setText(secondScore);
                break;
            case"3":
                distance.setText("20km");
                numberOfEntries.setText("0회");
                setPersonality(30, 40, 20, 15, 90);

                score.setText(thirdScore);
                break;
            case"4":
                distance.setText("140km");
                numberOfEntries.setText("5회 이상");
                setPersonality(80, 85, 70, 20, 45);

                score.setText(fourthScore);
                break;
            case"5":
                distance.setText("100km");
                numberOfEntries.setText("1~2회");
                setPersonality(50, 80, 10, 75, 30);

                score.setText(fifthScore);
                break;
        }
    }

    public boolean scoreCheck(String pageNum) {

        switch(pageNum) {
            case "1":
                if(firstScore.equals(""))
                    return false;
                else
                    return true;
            case"2":
                if(secondScore.equals(""))
                    return false;
                else
                    return true;
            case"3":
                if(thirdScore.equals(""))
                    return false;
                else
                    return true;
            case"4":
                if(fourthScore.equals(""))
                    return false;
                else
                    return true;
            case"5":
                if(fifthScore.equals(""))
                    return false;
                else
                    return true;
        }

        System.out.println("scoreCheck Error!");
        return false;
    }

    public void setPersonality(int mind, int energy, int nature, int tactics, int self)  {
        mindAText.setText(Integer.toString(100-mind));
        barParams = (LinearLayout.LayoutParams) mindABar.getLayoutParams();
        barParams.weight = 100 - mind;
        mindABar.setLayoutParams(barParams);
        mindBText.setText(Integer.toString(mind));
        barParams = (LinearLayout.LayoutParams) mindBBar.getLayoutParams();
        barParams.weight = mind;
        mindBBar.setLayoutParams(barParams);

        energyAText.setText(Integer.toString(100-energy));
        barParams = (LinearLayout.LayoutParams) energyABar.getLayoutParams();
        barParams.weight = 100 - energy;
        energyABar.setLayoutParams(barParams);
        energyBText.setText(Integer.toString(energy));
        barParams = (LinearLayout.LayoutParams) energyBBar.getLayoutParams();
        barParams.weight = energy;
        energyBBar.setLayoutParams(barParams);

        natureAText.setText(Integer.toString(100-nature));
        barParams = (LinearLayout.LayoutParams) natureABar.getLayoutParams();
        barParams.weight = 100 - nature;
        natureABar.setLayoutParams(barParams);
        natureBText.setText(Integer.toString(nature));
        barParams = (LinearLayout.LayoutParams) natureBBar.getLayoutParams();
        barParams.weight = nature;
        natureBBar.setLayoutParams(barParams);

        tacticsAText.setText(Integer.toString(100-tactics));
        barParams = (LinearLayout.LayoutParams) tacticsABar.getLayoutParams();
        barParams.weight = 100 - tactics;
        tacticsABar.setLayoutParams(barParams);
        tacticsBText.setText(Integer.toString(tactics));
        barParams = (LinearLayout.LayoutParams) tacticsBBar.getLayoutParams();
        barParams.weight = tactics;
        tacticsBBar.setLayoutParams(barParams);

        selfAText.setText(Integer.toString(100-self));
        barParams = (LinearLayout.LayoutParams) selfABar.getLayoutParams();
        barParams.weight = 100 - self;
        selfABar.setLayoutParams(barParams);
        selfBText.setText(Integer.toString(self));
        barParams = (LinearLayout.LayoutParams) selfBBar.getLayoutParams();
        barParams.weight = self;
        selfBBar.setLayoutParams(barParams);
    }
}