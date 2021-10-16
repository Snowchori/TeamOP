package com.example.opgrad;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragmentSecond extends Fragment {
    private View view;

    private CheckBox essential1CB;
    private CheckBox essential2CB;
    private CheckBox essential3CB;
    private CheckBox essential4CB;
    private CheckBox essential5CB;
    private CheckBox essential6CB;
    private CheckBox essential7CB;
    private CheckBox essential8CB;
    private CheckBox essential9CB;
    private CheckBox essential10CB;
    private CheckBox essential11CB;
    private CheckBox essential12CB;
    private CheckBox essential13CB;
    private CheckBox essential14CB;
    private CheckBox essential15CB;
    private CheckBox essential16CB;
    private CheckBox essential17CB;
    private CheckBox essential18CB;
    private CheckBox essential19CB;
    private CheckBox essential20CB;
    private CheckBox essential21CB;
    private CheckBox essential22CB;
    private CheckBox essential23CB;
    private CheckBox essential24CB;
    private CheckBox essential25CB;
    private CheckBox essential26CB;
    private CheckBox essential27CB;

    private CheckBox additional1CB;
    private CheckBox additional2CB;
    private CheckBox additional3CB;
    private CheckBox additional4CB;
    private CheckBox additional5CB;
    private CheckBox additional6CB;
    private CheckBox additional7CB;
    private CheckBox additional8CB;
    private CheckBox additional9CB;
    private CheckBox additional10CB;
    private CheckBox additional11CB;
    private CheckBox additional12CB;
    private CheckBox additional13CB;
    private CheckBox additional14CB;
    private CheckBox additional15CB;
    private CheckBox additional16CB;
    private CheckBox additional17CB;
    private CheckBox additional18CB;
    private CheckBox additional19CB;
    private CheckBox additional20CB;
    private CheckBox additional21CB;
    private CheckBox additional22CB;
    private CheckBox additional23CB;
    private CheckBox additional24CB;
    private CheckBox additional25CB;
    private CheckBox additional26CB;
    private CheckBox additional27CB;

    private TextView essential1TV;
    private TextView essential2TV;
    private TextView essential3TV;
    private TextView essential4TV;
    private TextView essential5TV;
    private TextView essential6TV;
    private TextView essential7TV;
    private TextView essential8TV;
    private TextView essential9TV;
    private TextView essential10TV;
    private TextView essential11TV;
    private TextView essential12TV;
    private TextView essential13TV;
    private TextView essential14TV;
    private TextView essential15TV;
    private TextView essential16TV;
    private TextView essential17TV;
    private TextView essential18TV;
    private TextView essential19TV;
    private TextView essential20TV;
    private TextView essential21TV;
    private TextView essential22TV;
    private TextView essential23TV;
    private TextView essential24TV;
    private TextView essential25TV;
    private TextView essential26TV;
    private TextView essential27TV;

    private TextView additional1TV;
    private TextView additional2TV;
    private TextView additional3TV;
    private TextView additional4TV;
    private TextView additional5TV;
    private TextView additional6TV;
    private TextView additional7TV;
    private TextView additional8TV;
    private TextView additional9TV;
    private TextView additional10TV;
    private TextView additional11TV;
    private TextView additional12TV;
    private TextView additional13TV;
    private TextView additional14TV;
    private TextView additional15TV;
    private TextView additional16TV;
    private TextView additional17TV;
    private TextView additional18TV;
    private TextView additional19TV;
    private TextView additional20TV;
    private TextView additional21TV;
    private TextView additional22TV;
    private TextView additional23TV;
    private TextView additional24TV;
    private TextView additional25TV;
    private TextView additional26TV;
    private TextView additional27TV;


    private Button previousBtn;
    private Button nextBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.recommend_second, null);

        essential1CB = (CheckBox) view.findViewById(R.id.essential1CB_recommend_second);
        essential1CB.setOnClickListener(restrictEssentialCBListener);
        essential2CB = (CheckBox) view.findViewById(R.id.essential2CB_recommend_second);
        essential2CB.setOnClickListener(restrictEssentialCBListener);
        essential3CB = (CheckBox) view.findViewById(R.id.essential3CB_recommend_second);
        essential3CB.setOnClickListener(restrictEssentialCBListener);
        essential4CB = (CheckBox) view.findViewById(R.id.essential4CB_recommend_second);
        essential4CB.setOnClickListener(restrictEssentialCBListener);
        essential5CB = (CheckBox) view.findViewById(R.id.essential5CB_recommend_second);
        essential5CB.setOnClickListener(restrictEssentialCBListener);
        essential6CB = (CheckBox) view.findViewById(R.id.essential6CB_recommend_second);
        essential6CB.setOnClickListener(restrictEssentialCBListener);
        essential7CB = (CheckBox) view.findViewById(R.id.essential7CB_recommend_second);
        essential7CB.setOnClickListener(restrictEssentialCBListener);
        essential8CB = (CheckBox) view.findViewById(R.id.essential8CB_recommend_second);
        essential8CB.setOnClickListener(restrictEssentialCBListener);
        essential9CB = (CheckBox) view.findViewById(R.id.essential9CB_recommend_second);
        essential9CB.setOnClickListener(restrictEssentialCBListener);
        essential10CB = (CheckBox) view.findViewById(R.id.essential10CB_recommend_second);
        essential10CB.setOnClickListener(restrictEssentialCBListener);
        essential11CB = (CheckBox) view.findViewById(R.id.essential11CB_recommend_second);
        essential11CB.setOnClickListener(restrictEssentialCBListener);
        essential12CB = (CheckBox) view.findViewById(R.id.essential12CB_recommend_second);
        essential12CB.setOnClickListener(restrictEssentialCBListener);
        essential13CB = (CheckBox) view.findViewById(R.id.essential13CB_recommend_second);
        essential13CB.setOnClickListener(restrictEssentialCBListener);
        essential14CB = (CheckBox) view.findViewById(R.id.essential14CB_recommend_second);
        essential14CB.setOnClickListener(restrictEssentialCBListener);
        essential15CB = (CheckBox) view.findViewById(R.id.essential15CB_recommend_second);
        essential15CB.setOnClickListener(restrictEssentialCBListener);
        essential16CB = (CheckBox) view.findViewById(R.id.essential16CB_recommend_second);
        essential16CB.setOnClickListener(restrictEssentialCBListener);
        essential17CB = (CheckBox) view.findViewById(R.id.essential17CB_recommend_second);
        essential17CB.setOnClickListener(restrictEssentialCBListener);
        essential18CB = (CheckBox) view.findViewById(R.id.essential18CB_recommend_second);
        essential18CB.setOnClickListener(restrictEssentialCBListener);
        essential19CB = (CheckBox) view.findViewById(R.id.essential19CB_recommend_second);
        essential19CB.setOnClickListener(restrictEssentialCBListener);
        essential20CB = (CheckBox) view.findViewById(R.id.essential20CB_recommend_second);
        essential20CB.setOnClickListener(restrictEssentialCBListener);
        essential21CB = (CheckBox) view.findViewById(R.id.essential21CB_recommend_second);
        essential21CB.setOnClickListener(restrictEssentialCBListener);
        essential22CB = (CheckBox) view.findViewById(R.id.essential22CB_recommend_second);
        essential22CB.setOnClickListener(restrictEssentialCBListener);
        essential23CB = (CheckBox) view.findViewById(R.id.essential23CB_recommend_second);
        essential23CB.setOnClickListener(restrictEssentialCBListener);
        essential24CB = (CheckBox) view.findViewById(R.id.essential24CB_recommend_second);
        essential24CB.setOnClickListener(restrictEssentialCBListener);
        essential25CB = (CheckBox) view.findViewById(R.id.essential25CB_recommend_second);
        essential25CB.setOnClickListener(restrictEssentialCBListener);
        essential26CB = (CheckBox) view.findViewById(R.id.essential26CB_recommend_second);
        essential26CB.setOnClickListener(restrictEssentialCBListener);
        essential27CB = (CheckBox) view.findViewById(R.id.essential27CB_recommend_second);
        essential27CB.setVisibility(View.INVISIBLE);

        additional1CB = (CheckBox) view.findViewById(R.id.additional1CB_recommend_second);
        additional2CB = (CheckBox) view.findViewById(R.id.additional2CB_recommend_second);
        additional3CB = (CheckBox) view.findViewById(R.id.additional3CB_recommend_second);
        additional4CB = (CheckBox) view.findViewById(R.id.additional4CB_recommend_second);
        additional5CB = (CheckBox) view.findViewById(R.id.additional5CB_recommend_second);
        additional6CB = (CheckBox) view.findViewById(R.id.additional6CB_recommend_second);
        additional7CB = (CheckBox) view.findViewById(R.id.additional7CB_recommend_second);
        additional8CB = (CheckBox) view.findViewById(R.id.additional8CB_recommend_second);
        additional9CB = (CheckBox) view.findViewById(R.id.additional9CB_recommend_second);
        additional10CB = (CheckBox) view.findViewById(R.id.additional10CB_recommend_second);
        additional11CB = (CheckBox) view.findViewById(R.id.additional11CB_recommend_second);
        additional12CB = (CheckBox) view.findViewById(R.id.additional12CB_recommend_second);
        additional13CB = (CheckBox) view.findViewById(R.id.additional13CB_recommend_second);
        additional14CB = (CheckBox) view.findViewById(R.id.additional14CB_recommend_second);
        additional15CB = (CheckBox) view.findViewById(R.id.additional15CB_recommend_second);
        additional16CB = (CheckBox) view.findViewById(R.id.additional16CB_recommend_second);
        additional17CB = (CheckBox) view.findViewById(R.id.additional17CB_recommend_second);
        additional18CB = (CheckBox) view.findViewById(R.id.additional18CB_recommend_second);
        additional19CB = (CheckBox) view.findViewById(R.id.additional19CB_recommend_second);
        additional20CB = (CheckBox) view.findViewById(R.id.additional20CB_recommend_second);
        additional21CB = (CheckBox) view.findViewById(R.id.additional21CB_recommend_second);
        additional22CB = (CheckBox) view.findViewById(R.id.additional22CB_recommend_second);
        additional23CB = (CheckBox) view.findViewById(R.id.additional23CB_recommend_second);
        additional24CB = (CheckBox) view.findViewById(R.id.additional24CB_recommend_second);
        additional25CB = (CheckBox) view.findViewById(R.id.additional25CB_recommend_second);
        additional26CB = (CheckBox) view.findViewById(R.id.additional26CB_recommend_second);
        additional27CB = (CheckBox) view.findViewById(R.id.additional27CB_recommend_second);
        additional27CB.setVisibility(View.INVISIBLE);


        essential1TV = (TextView) view.findViewById(R.id.essential1TV_recommend_second);
        essential2TV = (TextView) view.findViewById(R.id.essential2TV_recommend_second);
        essential3TV = (TextView) view.findViewById(R.id.essential3TV_recommend_second);
        essential4TV = (TextView) view.findViewById(R.id.essential4TV_recommend_second);
        essential5TV = (TextView) view.findViewById(R.id.essential5TV_recommend_second);
        essential6TV = (TextView) view.findViewById(R.id.essential6TV_recommend_second);
        essential7TV = (TextView) view.findViewById(R.id.essential7TV_recommend_second);
        essential8TV = (TextView) view.findViewById(R.id.essential8TV_recommend_second);
        essential9TV = (TextView) view.findViewById(R.id.essential9TV_recommend_second);
        essential10TV = (TextView) view.findViewById(R.id.essential10TV_recommend_second);
        essential11TV = (TextView) view.findViewById(R.id.essential11TV_recommend_second);
        essential12TV = (TextView) view.findViewById(R.id.essential12TV_recommend_second);
        essential13TV = (TextView) view.findViewById(R.id.essential13TV_recommend_second);
        essential14TV = (TextView) view.findViewById(R.id.essential14TV_recommend_second);
        essential15TV = (TextView) view.findViewById(R.id.essential15TV_recommend_second);
        essential16TV = (TextView) view.findViewById(R.id.essential16TV_recommend_second);
        essential17TV = (TextView) view.findViewById(R.id.essential17TV_recommend_second);
        essential18TV = (TextView) view.findViewById(R.id.essential18TV_recommend_second);
        essential19TV = (TextView) view.findViewById(R.id.essential19TV_recommend_second);
        essential20TV = (TextView) view.findViewById(R.id.essential20TV_recommend_second);
        essential21TV = (TextView) view.findViewById(R.id.essential21TV_recommend_second);
        essential22TV = (TextView) view.findViewById(R.id.essential22TV_recommend_second);
        essential23TV = (TextView) view.findViewById(R.id.essential23TV_recommend_second);
        essential24TV = (TextView) view.findViewById(R.id.essential24TV_recommend_second);
        essential25TV = (TextView) view.findViewById(R.id.essential25TV_recommend_second);
        essential26TV = (TextView) view.findViewById(R.id.essential26TV_recommend_second);
        essential27TV = (TextView) view.findViewById(R.id.essential27TV_recommend_second);


        additional1TV = (TextView) view.findViewById(R.id.additional1TV_recommend_second);
        additional2TV = (TextView) view.findViewById(R.id.additional2TV_recommend_second);
        additional3TV = (TextView) view.findViewById(R.id.additional3TV_recommend_second);
        additional4TV = (TextView) view.findViewById(R.id.additional4TV_recommend_second);
        additional5TV = (TextView) view.findViewById(R.id.additional5TV_recommend_second);
        additional6TV = (TextView) view.findViewById(R.id.additional6TV_recommend_second);
        additional7TV = (TextView) view.findViewById(R.id.additional7TV_recommend_second);
        additional8TV = (TextView) view.findViewById(R.id.additional8TV_recommend_second);
        additional9TV = (TextView) view.findViewById(R.id.additional9TV_recommend_second);
        additional10TV = (TextView) view.findViewById(R.id.additional10TV_recommend_second);
        additional11TV = (TextView) view.findViewById(R.id.additional11TV_recommend_second);
        additional12TV = (TextView) view.findViewById(R.id.additional12TV_recommend_second);
        additional13TV = (TextView) view.findViewById(R.id.additional13TV_recommend_second);
        additional14TV = (TextView) view.findViewById(R.id.additional14TV_recommend_second);
        additional15TV = (TextView) view.findViewById(R.id.additional15TV_recommend_second);
        additional16TV = (TextView) view.findViewById(R.id.additional16TV_recommend_second);
        additional17TV = (TextView) view.findViewById(R.id.additional17TV_recommend_second);
        additional18TV = (TextView) view.findViewById(R.id.additional18TV_recommend_second);
        additional19TV = (TextView) view.findViewById(R.id.additional19TV_recommend_second);
        additional20TV = (TextView) view.findViewById(R.id.additional20TV_recommend_second);
        additional21TV = (TextView) view.findViewById(R.id.additional21TV_recommend_second);
        additional22TV = (TextView) view.findViewById(R.id.additional22TV_recommend_second);
        additional23TV = (TextView) view.findViewById(R.id.additional23TV_recommend_second);
        additional24TV = (TextView) view.findViewById(R.id.additional24TV_recommend_second);
        additional25TV = (TextView) view.findViewById(R.id.additional25TV_recommend_second);
        additional26TV = (TextView) view.findViewById(R.id.additional26TV_recommend_second);
        additional27TV = (TextView) view.findViewById(R.id.additional27TV_recommend_second);


        previousBtn = (Button) view.findViewById(R.id.previousBtn_recommend_second);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecommendActivity)getActivity()).changeFragment(((RecommendActivity)getActivity()).firstFragment);
            }
        });

        nextBtn = (Button) view.findViewById(R.id.nextBtn_recommend_second);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> essentialList = new ArrayList<String>();
                List<String> additionalList = new ArrayList<String>();

                if(essentialCBCount() == 0) {
                    ((RecommendActivity)getActivity()).showNegativeDialog("반드시 필요한 역량은\n최소한 하나 이상 체크해야합니다.");
                } else {
                    ((RecommendActivity)getActivity()).capacityEssential = createEssentialList((ArrayList<String>) essentialList);
                    ((RecommendActivity)getActivity()).capacityPlus = createAdditionalList((ArrayList<String>) additionalList);
                    ((RecommendActivity)getActivity()).changeFragment(((RecommendActivity)getActivity()).thirdFragment);
                }
            }
        });

        return view;
    }

    View.OnClickListener restrictEssentialCBListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(essentialCBCount() == ((RecommendActivity)getActivity()).NOTM) {
                ((CheckBox) v).toggle();
                ((RecommendActivity) getActivity()).showNegativeDialog("팀원 수 보다 많이 체크할 수 없습니다.");
            }
        }
    };

    public int essentialCBCount() {
        int count = 0;
        essential1CB = (CheckBox) view.findViewById(R.id.essential1CB_recommend_second);
        essential2CB = (CheckBox) view.findViewById(R.id.essential2CB_recommend_second);
        essential3CB = (CheckBox) view.findViewById(R.id.essential3CB_recommend_second);
        essential4CB = (CheckBox) view.findViewById(R.id.essential4CB_recommend_second);
        essential5CB = (CheckBox) view.findViewById(R.id.essential5CB_recommend_second);
        essential6CB = (CheckBox) view.findViewById(R.id.essential6CB_recommend_second);
        essential7CB = (CheckBox) view.findViewById(R.id.essential7CB_recommend_second);
        essential8CB = (CheckBox) view.findViewById(R.id.essential8CB_recommend_second);
        essential9CB = (CheckBox) view.findViewById(R.id.essential9CB_recommend_second);
        essential10CB = (CheckBox) view.findViewById(R.id.essential10CB_recommend_second);
        essential11CB = (CheckBox) view.findViewById(R.id.essential11CB_recommend_second);
        essential12CB = (CheckBox) view.findViewById(R.id.essential12CB_recommend_second);
        essential13CB = (CheckBox) view.findViewById(R.id.essential13CB_recommend_second);
        essential14CB = (CheckBox) view.findViewById(R.id.essential14CB_recommend_second);
        essential15CB = (CheckBox) view.findViewById(R.id.essential15CB_recommend_second);
        essential16CB = (CheckBox) view.findViewById(R.id.essential16CB_recommend_second);
        essential17CB = (CheckBox) view.findViewById(R.id.essential17CB_recommend_second);
        essential18CB = (CheckBox) view.findViewById(R.id.essential18CB_recommend_second);
        essential19CB = (CheckBox) view.findViewById(R.id.essential19CB_recommend_second);
        essential20CB = (CheckBox) view.findViewById(R.id.essential20CB_recommend_second);
        essential21CB = (CheckBox) view.findViewById(R.id.essential21CB_recommend_second);
        essential22CB = (CheckBox) view.findViewById(R.id.essential22CB_recommend_second);
        essential23CB = (CheckBox) view.findViewById(R.id.essential23CB_recommend_second);
        essential24CB = (CheckBox) view.findViewById(R.id.essential24CB_recommend_second);
        essential25CB = (CheckBox) view.findViewById(R.id.essential25CB_recommend_second);
        essential26CB = (CheckBox) view.findViewById(R.id.essential26CB_recommend_second);

        if(essential1CB.isChecked())
            count++;
        if(essential2CB.isChecked())
            count++;
        if(essential3CB.isChecked())
            count++;
        if(essential4CB.isChecked())
            count++;
        if(essential5CB.isChecked())
            count++;
        if(essential6CB.isChecked())
            count++;
        if(essential7CB.isChecked())
            count++;
        if(essential8CB.isChecked())
            count++;
        if(essential9CB.isChecked())
            count++;
        if(essential10CB.isChecked())
            count++;
        if(essential11CB.isChecked())
            count++;
        if(essential12CB.isChecked())
            count++;
        if(essential13CB.isChecked())
            count++;
        if(essential14CB.isChecked())
            count++;
        if(essential15CB.isChecked())
            count++;
        if(essential16CB.isChecked())
            count++;
        if(essential17CB.isChecked())
            count++;
        if(essential18CB.isChecked())
            count++;
        if(essential19CB.isChecked())
            count++;
        if(essential20CB.isChecked())
            count++;
        if(essential21CB.isChecked())
            count++;
        if(essential22CB.isChecked())
            count++;
        if(essential23CB.isChecked())
            count++;
        if(essential24CB.isChecked())
            count++;
        if(essential25CB.isChecked())
            count++;
        if(essential26CB.isChecked())
            count++;

        return count;
    }

    public ArrayList<String> createEssentialList(ArrayList<String> essentialList){
        if(essential1CB.isChecked())
            essentialList.add(essential1TV.getText().toString());
        if(essential2CB.isChecked())
            essentialList.add(essential2TV.getText().toString());
        if(essential3CB.isChecked())
            essentialList.add(essential3TV.getText().toString());
        if(essential4CB.isChecked())
            essentialList.add(essential4TV.getText().toString());
        if(essential5CB.isChecked())
            essentialList.add(essential5TV.getText().toString());
        if(essential6CB.isChecked())
            essentialList.add(essential6TV.getText().toString());
        if(essential7CB.isChecked())
            essentialList.add(essential7TV.getText().toString());
        if(essential8CB.isChecked())
            essentialList.add(essential8TV.getText().toString());
        if(essential9CB.isChecked())
            essentialList.add(essential9TV.getText().toString());
        if(essential10CB.isChecked())
            essentialList.add(essential10TV.getText().toString());
        if(essential11CB.isChecked())
            essentialList.add(essential11TV.getText().toString());
        if(essential12CB.isChecked())
            essentialList.add(essential12TV.getText().toString());
        if(essential13CB.isChecked())
            essentialList.add(essential13TV.getText().toString());
        if(essential14CB.isChecked())
            essentialList.add(essential14TV.getText().toString());
        if(essential15CB.isChecked())
            essentialList.add(essential15TV.getText().toString());
        if(essential16CB.isChecked())
            essentialList.add(essential16TV.getText().toString());
        if(essential17CB.isChecked())
            essentialList.add(essential17TV.getText().toString());
        if(essential18CB.isChecked())
            essentialList.add(essential18TV.getText().toString());
        if(essential19CB.isChecked())
            essentialList.add(essential19TV.getText().toString());
        if(essential20CB.isChecked())
            essentialList.add(essential20TV.getText().toString());
        if(essential21CB.isChecked())
            essentialList.add(essential21TV.getText().toString());
        if(essential22CB.isChecked())
            essentialList.add(essential22TV.getText().toString());
        if(essential23CB.isChecked())
            essentialList.add(essential23TV.getText().toString());
        if(essential24CB.isChecked())
            essentialList.add(essential24TV.getText().toString());
        if(essential25CB.isChecked())
            essentialList.add(essential25TV.getText().toString());
        if(essential26CB.isChecked())
            essentialList.add(essential26TV.getText().toString());

        return essentialList;
    }


    public ArrayList<String> createAdditionalList(ArrayList<String> additionalList){
        if(additional1CB.isChecked())
            additionalList.add(additional1TV.getText().toString());
        if(additional2CB.isChecked())
            additionalList.add(additional2TV.getText().toString());
        if(additional3CB.isChecked())
            additionalList.add(additional3TV.getText().toString());
        if(additional4CB.isChecked())
            additionalList.add(additional4TV.getText().toString());
        if(additional5CB.isChecked())
            additionalList.add(additional5TV.getText().toString());
        if(additional6CB.isChecked())
            additionalList.add(additional6TV.getText().toString());
        if(additional7CB.isChecked())
            additionalList.add(additional7TV.getText().toString());
        if(additional8CB.isChecked())
            additionalList.add(additional8TV.getText().toString());
        if(additional9CB.isChecked())
            additionalList.add(additional9TV.getText().toString());
        if(additional10CB.isChecked())
            additionalList.add(additional10TV.getText().toString());
        if(additional11CB.isChecked())
            additionalList.add(additional11TV.getText().toString());
        if(additional12CB.isChecked())
            additionalList.add(additional12TV.getText().toString());
        if(additional13CB.isChecked())
            additionalList.add(additional13TV.getText().toString());
        if(additional14CB.isChecked())
            additionalList.add(additional14TV.getText().toString());
        if(additional15CB.isChecked())
            additionalList.add(additional15TV.getText().toString());
        if(additional16CB.isChecked())
            additionalList.add(additional16TV.getText().toString());
        if(additional17CB.isChecked())
            additionalList.add(additional17TV.getText().toString());
        if(additional18CB.isChecked())
            additionalList.add(additional18TV.getText().toString());
        if(additional19CB.isChecked())
            additionalList.add(additional19TV.getText().toString());
        if(additional20CB.isChecked())
            additionalList.add(additional20TV.getText().toString());
        if(additional21CB.isChecked())
            additionalList.add(additional21TV.getText().toString());
        if(additional22CB.isChecked())
            additionalList.add(additional22TV.getText().toString());
        if(additional23CB.isChecked())
            additionalList.add(additional23TV.getText().toString());
        if(additional24CB.isChecked())
            additionalList.add(additional24TV.getText().toString());
        if(additional25CB.isChecked())
            additionalList.add(additional25TV.getText().toString());
        if(additional26CB.isChecked())
            additionalList.add(additional26TV.getText().toString());

        return additionalList;
    }
}