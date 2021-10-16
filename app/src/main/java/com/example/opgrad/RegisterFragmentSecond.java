package com.example.opgrad;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RegisterFragmentSecond extends Fragment {

    private RadioButton maleBox;
    private RadioButton femaleBox;
    private EditText ageText;
    private EditText emailText;

    private EditText nameText;
    private EditText phoneText;

    private String gender = "";
    private String name = "";
    private String phone = "";
    private String age = "";
    private String email = "";
    private String address = "";
    private String classification = "";
    private String noe = "";
    private String hoperole = "";
    private int mind =0;
    private int energy = 0;
    private int nature = 0;
    private int tactics = 0;
    private int ego = 0;

    private Spinner emailSpinner;
    private Button searchAddressBtn;
    private EditText addressNumberText;
    private EditText addressText;
    private double mLat=0;
    private double mLng=0;

    private Spinner classicationSpinner;
    private Spinner noeSpinner;

    private String capability = "";

    private CheckBox capability1CB;
    private CheckBox capability2CB;
    private CheckBox capability3CB;
    private CheckBox capability4CB;
    private CheckBox capability5CB;
    private CheckBox capability6CB;
    private CheckBox capability7CB;
    private CheckBox capability8CB;
    private CheckBox capability9CB;
    private CheckBox capability10CB;
    private CheckBox capability11CB;
    private CheckBox capability12CB;
    private CheckBox capability13CB;
    private CheckBox capability14CB;
    private CheckBox capability15CB;
    private CheckBox capability16CB;
    private CheckBox capability17CB;
    private CheckBox capability18CB;
    private CheckBox capability19CB;
    private CheckBox capability20CB;
    private CheckBox capability21CB;
    private CheckBox capability22CB;
    private CheckBox capability23CB;
    private CheckBox capability24CB;
    private CheckBox capability25CB;
    private CheckBox capability26CB;
    private CheckBox capability27CB;

    private EditText hoperoleText;

    private TextView mindAText;
    private EditText mindBText;
    private TextView energyAText;
    private EditText energyBText;
    private TextView natureAText;
    private EditText natureBText;
    private TextView tacticsAText;
    private EditText tacticsBText;
    private TextView selfAText;
    private EditText selfBText;

    private View mindABar;
    private View mindBBar;
    private View energyABar;
    private View energyBBar;
    private View natureABar;
    private View natureBBar;
    private View tacticsABar;
    private View tacticsBBar;
    private View selfABar;
    private View selfBBar;

    private LinearLayout.LayoutParams barParams;

    private Button nextBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_second, null);

        maleBox = (RadioButton) view.findViewById(R.id.maleBox_register_second);
        femaleBox = (RadioButton) view.findViewById(R.id.femaleBox_register_second);

        ageText=(EditText) view.findViewById(R.id.age_register_second);
        nameText = (EditText) view.findViewById(R.id.name_register_second);
        phoneText = (EditText) view.findViewById(R.id.phone_register_second);
        emailText = (EditText) view.findViewById(R.id.email_register_second);
        emailSpinner = (Spinner) view.findViewById(R.id.emailSpinner_register_second);

        searchAddressBtn = (Button) view.findViewById(R.id.searchAddressBtn_register_second);
        searchAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((RegisterActivity)getActivity()), SearchAddress.class);
                startActivityForResult(intent,1);
            }
        });
        addressNumberText= (EditText) view.findViewById(R.id.addressNumber_register_second);
        addressText= (EditText) view.findViewById(R.id.address_register_second);

        classicationSpinner = (Spinner) view.findViewById(R.id.classificationSpinner_register_second);
        noeSpinner = (Spinner) view.findViewById(R.id.numberOfEntriesSpinner_register_second);


        capability1CB = (CheckBox) view.findViewById(R.id. capability1CB_register_second);
        capability2CB = (CheckBox) view.findViewById(R.id. capability2CB_register_second);
        capability3CB = (CheckBox) view.findViewById(R.id. capability3CB_register_second);
        capability4CB = (CheckBox) view.findViewById(R.id. capability4CB_register_second);
        capability5CB = (CheckBox) view.findViewById(R.id. capability5CB_register_second);
        capability6CB = (CheckBox) view.findViewById(R.id. capability6CB_register_second);
        capability7CB = (CheckBox) view.findViewById(R.id. capability7CB_register_second);
        capability8CB = (CheckBox) view.findViewById(R.id. capability8CB_register_second);
        capability9CB = (CheckBox) view.findViewById(R.id. capability9CB_register_second);
        capability10CB = (CheckBox) view.findViewById(R.id.capability10CB_register_second);
        capability11CB = (CheckBox) view.findViewById(R.id.capability11CB_register_second);
        capability12CB = (CheckBox) view.findViewById(R.id.capability12CB_register_second);
        capability13CB = (CheckBox) view.findViewById(R.id.capability13CB_register_second);
        capability14CB = (CheckBox) view.findViewById(R.id.capability14CB_register_second);
        capability15CB = (CheckBox) view.findViewById(R.id.capability15CB_register_second);
        capability16CB = (CheckBox) view.findViewById(R.id.capability16CB_register_second);
        capability17CB = (CheckBox) view.findViewById(R.id.capability17CB_register_second);
        capability18CB = (CheckBox) view.findViewById(R.id.capability18CB_register_second);
        capability19CB = (CheckBox) view.findViewById(R.id.capability19CB_register_second);
        capability20CB = (CheckBox) view.findViewById(R.id.capability20CB_register_second);
        capability21CB = (CheckBox) view.findViewById(R.id.capability21CB_register_second);
        capability22CB = (CheckBox) view.findViewById(R.id.capability22CB_register_second);
        capability23CB = (CheckBox) view.findViewById(R.id.capability23CB_register_second);
        capability24CB = (CheckBox) view.findViewById(R.id.capability24CB_register_second);
        capability25CB = (CheckBox) view.findViewById(R.id.capability25CB_register_second);
        capability26CB = (CheckBox) view.findViewById(R.id.capability26CB_register_second);
        capability27CB = (CheckBox) view.findViewById(R.id.capability27CB_register_second);
        capability27CB.setVisibility(View.INVISIBLE);

        hoperoleText = (EditText) view.findViewById(R.id.hopeRole_register_second);

        mindAText = (TextView) view.findViewById(R.id.mindAText_register_second);
        mindBText = (EditText) view.findViewById(R.id.mindBText_register_second);
        mindABar = (View) view.findViewById(R.id.mindABar_register_second);
        mindBBar = (View) view.findViewById(R.id.mindBBar_register_second);
        mindBText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        mindBText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if(mindBText.getText().toString().equals("")) {
                    mindAText.setText("");

                    barParams = (LinearLayout.LayoutParams) mindBBar.getLayoutParams();
                    barParams.weight = 50;
                    mindBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) mindABar.getLayoutParams();
                    barParams.weight = 50;
                    mindABar.setLayoutParams(barParams);
                } else {
                    int mindB = Integer.parseInt(mindBText.getText().toString());
                    String mindA = Integer.toString((100 - mindB));
                    mindAText.setText(mindA);

                    barParams = (LinearLayout.LayoutParams) mindBBar.getLayoutParams();
                    barParams.weight = mindB;
                    mindBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) mindABar.getLayoutParams();
                    barParams.weight = 100-mindB;
                    mindABar.setLayoutParams(barParams);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
        energyAText = (TextView) view.findViewById(R.id.energyAText_register_second);
        energyBText = (EditText) view.findViewById(R.id.energyBText_register_second);
        energyABar = (View) view.findViewById(R.id.energyABar_register_second);
        energyBBar = (View) view.findViewById(R.id.energyBBar_register_second);
        energyBText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        energyBText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if(energyBText.getText().toString().equals("")) {
                    energyAText.setText("");

                    barParams = (LinearLayout.LayoutParams) energyBBar.getLayoutParams();
                    barParams.weight = 50;
                    energyBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) energyABar.getLayoutParams();
                    barParams.weight = 50;
                    energyABar.setLayoutParams(barParams);
                } else {
                    int energyB = Integer.parseInt(energyBText.getText().toString());
                    String energyA = Integer.toString((100 - energyB));
                    energyAText.setText(energyA);

                    barParams = (LinearLayout.LayoutParams) energyBBar.getLayoutParams();
                    barParams.weight = energyB;
                    energyBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) energyABar.getLayoutParams();
                    barParams.weight = 100-energyB;
                    energyABar.setLayoutParams(barParams);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
        natureAText = (TextView) view.findViewById(R.id.natureAText_register_second);
        natureBText = (EditText) view.findViewById(R.id.natureBText_register_second);
        natureABar = (View) view.findViewById(R.id.natureABar_register_second);
        natureBBar = (View) view.findViewById(R.id.natureBBar_register_second);
        natureBText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        natureBText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if(natureBText.getText().toString().equals("")) {
                    natureAText.setText("");

                    barParams = (LinearLayout.LayoutParams) natureBBar.getLayoutParams();
                    barParams.weight = 50;
                    natureBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) natureABar.getLayoutParams();
                    barParams.weight = 50;
                    natureABar.setLayoutParams(barParams);
                } else {
                    int natureB = Integer.parseInt(natureBText.getText().toString());
                    String natureA = Integer.toString((100 - natureB));
                    natureAText.setText(natureA);

                    barParams = (LinearLayout.LayoutParams) natureBBar.getLayoutParams();
                    barParams.weight = natureB;
                    natureBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) natureABar.getLayoutParams();
                    barParams.weight = 100-natureB;
                    natureABar.setLayoutParams(barParams);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
        tacticsAText = (TextView) view.findViewById(R.id.tacticsAText_register_second);
        tacticsBText = (EditText) view.findViewById(R.id.tacticsBText_register_second);
        tacticsABar = (View) view.findViewById(R.id.tacticsABar_register_second);
        tacticsBBar = (View) view.findViewById(R.id.tacticsBBar_register_second);
        tacticsBText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        tacticsBText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if(tacticsBText.getText().toString().equals("")) {
                    tacticsAText.setText("");

                    barParams = (LinearLayout.LayoutParams) tacticsBBar.getLayoutParams();
                    barParams.weight = 50;
                    tacticsBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) tacticsABar.getLayoutParams();
                    barParams.weight = 50;
                    tacticsABar.setLayoutParams(barParams);
                } else {
                    int tacticsB = Integer.parseInt(tacticsBText.getText().toString());
                    String tacticsA = Integer.toString((100 - tacticsB));
                    tacticsAText.setText(tacticsA);

                    barParams = (LinearLayout.LayoutParams) tacticsBBar.getLayoutParams();
                    barParams.weight = tacticsB;
                    tacticsBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) tacticsABar.getLayoutParams();
                    barParams.weight = 100-tacticsB;
                    tacticsABar.setLayoutParams(barParams);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
        selfAText = (TextView) view.findViewById(R.id.selfAText_register_second);
        selfBText = (EditText) view.findViewById(R.id.selfBText_register_second);
        selfABar = (View) view.findViewById(R.id.selfABar_register_second);
        selfBBar = (View) view.findViewById(R.id.selfBBar_register_second);
        selfBText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        selfBText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if(selfBText.getText().toString().equals("")) {
                    selfAText.setText("");

                    barParams = (LinearLayout.LayoutParams) selfBBar.getLayoutParams();
                    barParams.weight = 50;
                    selfBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) selfABar.getLayoutParams();
                    barParams.weight = 50;
                    selfABar.setLayoutParams(barParams);
                } else {
                    int selfB = Integer.parseInt(selfBText.getText().toString());
                    String selfA = Integer.toString((100 - selfB));
                    selfAText.setText(selfA);

                    barParams = (LinearLayout.LayoutParams) selfBBar.getLayoutParams();
                    barParams.weight = selfB;
                    selfBBar.setLayoutParams(barParams);
                    barParams = (LinearLayout.LayoutParams) selfABar.getLayoutParams();
                    barParams.weight = 100-selfB;
                    selfABar.setLayoutParams(barParams);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });


        nextBtn = (Button) view.findViewById(R.id.nextBtn_register_second);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).reomveKeyboard();

                List<Integer> field=new ArrayList<Integer>();
                List<Integer> responfield=new ArrayList<Integer>();

                if(maleBox.isChecked())
                    gender = "M";
                if(femaleBox.isChecked())
                    gender = "F";

                if(capability1CB.isChecked())
                    capability = "isChecked!";
                if(capability2CB.isChecked())
                    capability = "isChecked!";
                if(capability3CB.isChecked())
                    capability = "isChecked!";
                if(capability4CB.isChecked())
                    capability = "isChecked!";
                if(capability5CB.isChecked())
                    capability = "isChecked!";
                if(capability6CB.isChecked())
                    capability = "isChecked!";
                if(capability7CB.isChecked())
                    capability = "isChecked!";
                if(capability8CB.isChecked())
                    capability = "isChecked!";
                if(capability9CB.isChecked())
                    capability = "isChecked!";
                if(capability10CB.isChecked())
                    capability = "isChecked!";
                if(capability11CB.isChecked())
                    capability = "isChecked!";
                if(capability12CB.isChecked())
                    capability = "isChecked!";
                if(capability13CB.isChecked())
                    capability = "isChecked!";
                if(capability14CB.isChecked())
                    capability = "isChecked!";
                if(capability15CB.isChecked())
                    capability = "isChecked!";
                if(capability16CB.isChecked())
                    capability = "isChecked!";
                if(capability17CB.isChecked())
                    capability = "isChecked!";
                if(capability18CB.isChecked())
                    capability = "isChecked!";
                if(capability19CB.isChecked())
                    capability = "isChecked!";
                if(capability20CB.isChecked())
                    capability = "isChecked!";
                if(capability21CB.isChecked())
                    capability = "isChecked!";
                if(capability22CB.isChecked())
                    capability = "isChecked!";
                if(capability23CB.isChecked())
                    capability = "isChecked!";
                if(capability24CB.isChecked())
                    capability = "isChecked!";
                if(capability25CB.isChecked())
                    capability = "isChecked!";
                if(capability26CB.isChecked())
                    capability = "isChecked!";

                if(gender.equals("") || nameText.getText().toString().equals("") || addressText.getText().toString().equals("")|| noeSpinner.getSelectedItem().toString().equals("")||
                        classicationSpinner.getSelectedItem().toString().equals("")|| mindBText.getText().toString().equals("")|| energyBText.getText().toString().equals("")||
                        natureBText.getText().toString().equals("")|| tacticsBText.getText().toString().equals("")|| selfBText.getText().toString().equals("")|| capability.equals("")) {
                    ((RegisterActivity)getActivity()).showNegativeDialog("빈 칸 없이 입력해주세요.");
                } else {
                    name = nameText.getText().toString();
                    phone = phoneText.getText().toString();
                    age = ageText.getText().toString();
                    email = emailText.getText().toString()+"@"+emailSpinner.getSelectedItem().toString();
                    address = addressText.getText().toString();
                    classification = classicationSpinner.getSelectedItem().toString();
                    noe = noeSpinner.getSelectedItem().toString();
                    hoperole = hoperoleText.getText().toString();
                    mind= Integer.parseInt(mindBText.getText().toString());
                    energy= Integer.parseInt(energyBText.getText().toString());
                    nature= Integer.parseInt(natureBText.getText().toString());
                    tactics= Integer.parseInt(tacticsBText.getText().toString());
                    ego= Integer.parseInt(selfBText.getText().toString());

                    if (capability1CB.isChecked())
                        responfield.add(1);
                    if (capability2CB.isChecked())
                        responfield.add(2);
                    if (capability3CB.isChecked())
                        responfield.add(3);
                    if (capability4CB.isChecked())
                        responfield.add(4);
                    if (capability5CB.isChecked())
                        responfield.add(5);
                    if (capability6CB.isChecked())
                        responfield.add(6);
                    if (capability7CB.isChecked())
                        responfield.add(7);
                    if (capability8CB.isChecked())
                        responfield.add(8);
                    if (capability9CB.isChecked())
                        responfield.add(9);
                    if (capability10CB.isChecked())
                        responfield.add(10);
                    if (capability11CB.isChecked())
                        responfield.add(11);
                    if (capability12CB.isChecked())
                        responfield.add(12);
                    if (capability13CB.isChecked())
                        responfield.add(13);
                    if (capability14CB.isChecked())
                        responfield.add(14);
                    if (capability15CB.isChecked())
                        responfield.add(15);
                    if (capability16CB.isChecked())
                        responfield.add(16);
                    if (capability17CB.isChecked())
                        responfield.add(17);
                    if (capability18CB.isChecked())
                        responfield.add(18);
                    if (capability19CB.isChecked())
                        responfield.add(19);
                    if (capability20CB.isChecked())
                        responfield.add(20);
                    if (capability21CB.isChecked())
                        responfield.add(21);
                    if (capability22CB.isChecked())
                        responfield.add(22);
                    if (capability23CB.isChecked())
                        responfield.add(23);
                    if (capability24CB.isChecked())
                        responfield.add(24);
                    if (capability25CB.isChecked())
                        responfield.add(25);
                    if (capability26CB.isChecked())
                        responfield.add(26);
                    ((RegisterActivity)getActivity()).userGender = gender;
                    ((RegisterActivity)getActivity()).userName = name;
                    ((RegisterActivity)getActivity()).userPhone = phone;
                    ((RegisterActivity)getActivity()).userAge = age;
                    ((RegisterActivity)getActivity()).userEMail = email;
                    ((RegisterActivity)getActivity()).userAddress = address;
                    ((RegisterActivity)getActivity()).userLatitude = Double.toString(mLat);
                    ((RegisterActivity)getActivity()).userLongitude =  Double.toString(mLng);
                    ((RegisterActivity)getActivity()).userClassification = classification;
                    ((RegisterActivity)getActivity()).userNoE = noe;
                    ((RegisterActivity)getActivity()).userHopeRole = hoperole;
                    ((RegisterActivity)getActivity()).userMind = Integer.toString(mind);
                    ((RegisterActivity)getActivity()).userEnergy = Integer.toString(energy);
                    ((RegisterActivity)getActivity()).userNature = Integer.toString(nature);
                    ((RegisterActivity)getActivity()).userTactics = Integer.toString(tactics);
                    ((RegisterActivity)getActivity()).userEgo = Integer.toString(ego);
                    ((RegisterActivity)getActivity()).userResponField = responfield;

                    if(!((RegisterActivity)getActivity()).isSecondNotice)
                        ((RegisterActivity)getActivity()).showSecondNotice();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String addressnum = data.getExtras().getString("addressnum");
                String address = data.getExtras().getString("address");

                addressNumberText.setText(addressnum);
                addressText.setText(address);

                Geocoder mGeocoder=new Geocoder(getContext());
                try{
                    List<Address> mResultLocation=mGeocoder.getFromLocationName(address,1);
                    mLat=mResultLocation.get(0).getLatitude();
                    mLng=mResultLocation.get(0).getLongitude();
                }catch (IOException e){
                    e.printStackTrace();
                }
            } else {   // RESULT_CANCEL

            }
        }
    }



}