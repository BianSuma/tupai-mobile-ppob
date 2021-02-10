package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.component.RecyclerViewX;
import com.pajakku.tupaimobile.model.actdata.ActDataKodeNegara;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.ArrayList;
import java.util.List;

public class PickKodeNegaraActivity extends BaseActivity {

    private static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_KODE_NEGARA;

    private ActDataKodeNegara actData;

    private InpTextX inpFind;
    private RecyclerViewX recyclerViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_common_list_find);

        initData(savedInstanceState);

        findViewById(R.id.commonlistfind_btn_actionbar_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((AppCompatTextView)findViewById(R.id.commonlistfind_lbl_actionbar_title)).setText("Pilih Negara");

        findViewById(R.id.commonlistfind_btn_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubmitFind();
            }
        });

        inpFind = setCompInpTextX(R.id.commonlistfind_lay_find);
        inpFind.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    clickSubmitFind();
                    return true;
                }
                return false;
            }
        });

        recyclerViewX = setRecyclerViewX(R.id.commonlistfind_swiperefreshlayout, new CommonCallback<ClickItemListParam>() {
            @Override
            public void onSuccess(ClickItemListParam data) {
                PickedDTO p = new PickedDTO();
                p.name = ((HolderCommon2Val)data.object).mini+" " +((HolderCommon2Val)data.object).bold;
                p.object = data.object;
                clickDetail( p );
            }
        },  null);

        findViewById(R.id.commonlistfind_fab).setVisibility(View.GONE);

    }

    @Override
    public void onResume(){
        super.onResume();

        setAppRecyclerViewStart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//
        outState.putSerializable(ACTDATA_KEY, actData);
    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
//            Intent itn = getIntent();
//            saving = () itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataKodeNegara();
        }else{
            actData = (ActDataKodeNegara) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    public void fetch(RequestParamConfig rpc) {
        List<HolderCommon2Val> l = new ArrayList<>();

        l.add(new HolderCommon2Val("ID","Indonesia") );
        l.add(new HolderCommon2Val("AF","Afghanistan") );
        l.add(new HolderCommon2Val("AL","Albania") );
        l.add(new HolderCommon2Val("DZ","Algeria") );
        l.add(new HolderCommon2Val("AS","American Samoa") );
        l.add(new HolderCommon2Val("AD","Andorra") );
        l.add(new HolderCommon2Val("AO","Angola") );
        l.add(new HolderCommon2Val("AI","Anguilla") );
        l.add(new HolderCommon2Val("AQ","Antarctica") );
        l.add(new HolderCommon2Val("AG","Antigua and Barbuda") );
        l.add(new HolderCommon2Val("AR","Argentina") );
        l.add(new HolderCommon2Val("AM","Armenia") );
        l.add(new HolderCommon2Val("AW","Aruba") );
        l.add(new HolderCommon2Val("AU","Australia") );
        l.add(new HolderCommon2Val("AT","Austria") );
        l.add(new HolderCommon2Val("AZ","Azerbaijan") );
        l.add(new HolderCommon2Val("BS","Bahamas") );
        l.add(new HolderCommon2Val("BH","Bahrain") );
        l.add(new HolderCommon2Val("BD","Bangladesh") );
        l.add(new HolderCommon2Val("BB","Barbados") );
        l.add(new HolderCommon2Val("BY","Belarus") );
        l.add(new HolderCommon2Val("BE","Belgium") );
        l.add(new HolderCommon2Val("BZ","Belize") );
        l.add(new HolderCommon2Val("BJ","Benin") );
        l.add(new HolderCommon2Val("BM","Bermuda") );
        l.add(new HolderCommon2Val("BT","Bhutan") );
        l.add(new HolderCommon2Val("BO","Bolivia, Plurinational State of") );
        l.add(new HolderCommon2Val("BQ","Bonaire, Sint Eustatius and Saba") );
        l.add(new HolderCommon2Val("BA","Bosnia and Herzegovina") );
        l.add(new HolderCommon2Val("BW","Botswana") );
        l.add(new HolderCommon2Val("BV","Bouvet Island") );
        l.add(new HolderCommon2Val("BR","Brazil") );
        l.add(new HolderCommon2Val("IO","British Indian Ocean Territory") );
        l.add(new HolderCommon2Val("BN","Brunei Darussalam") );
        l.add(new HolderCommon2Val("BG","Bulgaria") );
        l.add(new HolderCommon2Val("BF","Burkina Faso") );
        l.add(new HolderCommon2Val("BI","Burundi") );
        l.add(new HolderCommon2Val("KH","Cambodia") );
        l.add(new HolderCommon2Val("CM","Cameroon") );
        l.add(new HolderCommon2Val("CA","Canada") );
        l.add(new HolderCommon2Val("CV","Cape Verde") );
        l.add(new HolderCommon2Val("KY","Cayman Islands") );
        l.add(new HolderCommon2Val("CF","Central African Republic") );
        l.add(new HolderCommon2Val("TD","Chad") );
        l.add(new HolderCommon2Val("CL","Chile") );
        l.add(new HolderCommon2Val("CN","China") );
        l.add(new HolderCommon2Val("CX","Christmas Island") );
        l.add(new HolderCommon2Val("CC","Cocos (Keeling) Islands") );
        l.add(new HolderCommon2Val("CO","Colombia") );
        l.add(new HolderCommon2Val("KM","Comoros") );
        l.add(new HolderCommon2Val("CG","Congo") );
        l.add(new HolderCommon2Val("CD","Congo, the Democratic Republic of the") );
        l.add(new HolderCommon2Val("CK","Cook Islands") );
        l.add(new HolderCommon2Val("CR","Costa Rica") );
        l.add(new HolderCommon2Val("CY","Cyprus") );
        l.add(new HolderCommon2Val("CZ","Czech Republic") );
        l.add(new HolderCommon2Val("DK","Denmark") );
        l.add(new HolderCommon2Val("DJ","Djibouti") );
        l.add(new HolderCommon2Val("DM","Dominica") );
        l.add(new HolderCommon2Val("DO","Dominican Republic") );
        l.add(new HolderCommon2Val("EC","Ecuador") );
        l.add(new HolderCommon2Val("EG","Egypt") );
        l.add(new HolderCommon2Val("SV","El Salvador") );
        l.add(new HolderCommon2Val("GQ","Equatorial Guinea") );
        l.add(new HolderCommon2Val("ER","Eritrea") );
        l.add(new HolderCommon2Val("EE","Estonia") );
        l.add(new HolderCommon2Val("ET","Ethiopia") );
        l.add(new HolderCommon2Val("FK","Falkland Islands (Malvinas)") );
        l.add(new HolderCommon2Val("FO","Faroe Islands") );
        l.add(new HolderCommon2Val("FJ","Fiji") );
        l.add(new HolderCommon2Val("FI","Finland") );
        l.add(new HolderCommon2Val("FR","France") );
        l.add(new HolderCommon2Val("GF","French Guiana") );
        l.add(new HolderCommon2Val("PF","French Polynesia") );
        l.add(new HolderCommon2Val("TF","French Southern Territories") );
        l.add(new HolderCommon2Val("GA","Gabon") );
        l.add(new HolderCommon2Val("GM","Gambia") );
        l.add(new HolderCommon2Val("GE","Georgia") );
        l.add(new HolderCommon2Val("DE","Germany") );
        l.add(new HolderCommon2Val("GH","Ghana") );
        l.add(new HolderCommon2Val("GI","Gibraltar") );
        l.add(new HolderCommon2Val("GR","Greece") );
        l.add(new HolderCommon2Val("GL","Greenland") );
        l.add(new HolderCommon2Val("GD","Grenada") );
        l.add(new HolderCommon2Val("GP","Guadeloupe") );
        l.add(new HolderCommon2Val("GU","Guam") );
        l.add(new HolderCommon2Val("GT","Guatemala") );
        l.add(new HolderCommon2Val("GG","Guernsey") );
        l.add(new HolderCommon2Val("GN","Guinea") );
        l.add(new HolderCommon2Val("GW","Guinea-Bissau") );
        l.add(new HolderCommon2Val("GY","Guyana") );
        l.add(new HolderCommon2Val("HT","Haiti") );
        l.add(new HolderCommon2Val("HM","Heard Island and McDonald Islands") );
        l.add(new HolderCommon2Val("VA","Holy See (Vatican City State)") );
        l.add(new HolderCommon2Val("HN","Honduras") );
        l.add(new HolderCommon2Val("HK","Hong Kong") );
        l.add(new HolderCommon2Val("HU","Hungary") );
        l.add(new HolderCommon2Val("IS","Iceland") );
        l.add(new HolderCommon2Val("IN","India") );
        l.add(new HolderCommon2Val("IR","Iran, Islamic Republic of") );
        l.add(new HolderCommon2Val("IQ","Iraq") );
        l.add(new HolderCommon2Val("IE","Ireland") );
        l.add(new HolderCommon2Val("IM","Isle of Man") );
        l.add(new HolderCommon2Val("IL","Israel") );
        l.add(new HolderCommon2Val("IT","Italy") );
        l.add(new HolderCommon2Val("JM","Jamaica") );
        l.add(new HolderCommon2Val("JP","Japan") );
        l.add(new HolderCommon2Val("JE","Jersey") );
        l.add(new HolderCommon2Val("JO","Jordan") );
        l.add(new HolderCommon2Val("KZ","Kazakhstan") );
        l.add(new HolderCommon2Val("KE","Kenya") );
        l.add(new HolderCommon2Val("KI","Kiribati") );
        l.add(new HolderCommon2Val("KP","Korea, Democratic People's Republic of") );
        l.add(new HolderCommon2Val("KR","Korea, Republic of") );
        l.add(new HolderCommon2Val("KW","Kuwait") );
        l.add(new HolderCommon2Val("KG","Kyrgyzstan") );
        l.add(new HolderCommon2Val("LA","Lao People's Democratic Republic") );
        l.add(new HolderCommon2Val("LV","Latvia") );
        l.add(new HolderCommon2Val("LB","Lebanon") );
        l.add(new HolderCommon2Val("LS","Lesotho") );
        l.add(new HolderCommon2Val("LR","Liberia") );
        l.add(new HolderCommon2Val("LY","Libya") );
        l.add(new HolderCommon2Val("LI","Liechtenstein") );
        l.add(new HolderCommon2Val("LT","Lithuania") );
        l.add(new HolderCommon2Val("LU","Luxembourg") );
        l.add(new HolderCommon2Val("MO","Macao") );
        l.add(new HolderCommon2Val("MK","Macedonia, the Former Yugoslav Republic of") );
        l.add(new HolderCommon2Val("MG","Madagascar") );
        l.add(new HolderCommon2Val("MW","Malawi") );
        l.add(new HolderCommon2Val("MY","Malaysia") );
        l.add(new HolderCommon2Val("MV","Maldives") );
        l.add(new HolderCommon2Val("ML","Mali") );
        l.add(new HolderCommon2Val("MT","Malta") );
        l.add(new HolderCommon2Val("MH","Marshall Islands") );
        l.add(new HolderCommon2Val("MQ","Martinique") );
        l.add(new HolderCommon2Val("MR","Mauritania") );
        l.add(new HolderCommon2Val("MU","Mauritius") );
        l.add(new HolderCommon2Val("YT","Mayotte") );
        l.add(new HolderCommon2Val("MX","Mexico") );
        l.add(new HolderCommon2Val("FM","Micronesia, Federated States of") );
        l.add(new HolderCommon2Val("MD","Moldova, Republic of") );
        l.add(new HolderCommon2Val("MC","Monaco") );
        l.add(new HolderCommon2Val("MN","Mongolia") );
        l.add(new HolderCommon2Val("ME","Montenegro") );
        l.add(new HolderCommon2Val("MS","Montserrat") );
        l.add(new HolderCommon2Val("MA","Morocco") );
        l.add(new HolderCommon2Val("MZ","Mozambique") );
        l.add(new HolderCommon2Val("MM","Myanmar") );
        l.add(new HolderCommon2Val("NA","Namibia") );
        l.add(new HolderCommon2Val("NR","Nauru") );
        l.add(new HolderCommon2Val("NP","Nepal") );
        l.add(new HolderCommon2Val("NL","Netherlands") );
        l.add(new HolderCommon2Val("NC","New Caledonia") );
        l.add(new HolderCommon2Val("NZ","New Zealand") );
        l.add(new HolderCommon2Val("NI","Nicaragua") );
        l.add(new HolderCommon2Val("NE","Niger") );
        l.add(new HolderCommon2Val("NG","Nigeria") );
        l.add(new HolderCommon2Val("NU","Niue") );
        l.add(new HolderCommon2Val("NF","Norfolk Island") );
        l.add(new HolderCommon2Val("MP","Northern Mariana Islands") );
        l.add(new HolderCommon2Val("NO","Norway") );
        l.add(new HolderCommon2Val("OM","Oman") );
        l.add(new HolderCommon2Val("PK","Pakistan") );
        l.add(new HolderCommon2Val("PW","Palau") );
        l.add(new HolderCommon2Val("PS","Palestine, State of") );
        l.add(new HolderCommon2Val("PA","Panama") );
        l.add(new HolderCommon2Val("PG","Papua New Guinea") );
        l.add(new HolderCommon2Val("PY","Paraguay") );
        l.add(new HolderCommon2Val("PE","Peru") );
        l.add(new HolderCommon2Val("PH","Philippines") );
        l.add(new HolderCommon2Val("PN","Pitcairn") );
        l.add(new HolderCommon2Val("PL","Poland") );
        l.add(new HolderCommon2Val("PT","Portugal") );
        l.add(new HolderCommon2Val("PR","Puerto Rico") );
        l.add(new HolderCommon2Val("QA","Qatar") );
        l.add(new HolderCommon2Val("RE","R\u00e9union") );
        l.add(new HolderCommon2Val("RO","Romania") );
        l.add(new HolderCommon2Val("RU","Russian Federation") );
        l.add(new HolderCommon2Val("RW","Rwanda") );
        l.add(new HolderCommon2Val("BL","Saint Barth\u00e9lemy") );
        l.add(new HolderCommon2Val("SH","Saint Helena, Ascension and Tristan da Cunha") );
        l.add(new HolderCommon2Val("KN","Saint Kitts and Nevis") );
        l.add(new HolderCommon2Val("LC","Saint Lucia") );
        l.add(new HolderCommon2Val("MF","Saint Martin (French part)") );
        l.add(new HolderCommon2Val("PM","Saint Pierre and Miquelon") );
        l.add(new HolderCommon2Val("VC","Saint Vincent and the Grenadines") );
        l.add(new HolderCommon2Val("WS","Samoa") );
        l.add(new HolderCommon2Val("SM","San Marino") );
        l.add(new HolderCommon2Val("ST","Sao Tome and Principe") );
        l.add(new HolderCommon2Val("SA","Saudi Arabia") );
        l.add(new HolderCommon2Val("SN","Senegal") );
        l.add(new HolderCommon2Val("RS","Serbia") );
        l.add(new HolderCommon2Val("SC","Seychelles") );
        l.add(new HolderCommon2Val("SL","Sierra Leone") );
        l.add(new HolderCommon2Val("SG","Singapore") );
        l.add(new HolderCommon2Val("SX","Sint Maarten (Dutch part)") );
        l.add(new HolderCommon2Val("SK","Slovakia") );
        l.add(new HolderCommon2Val("SI","Slovenia") );
        l.add(new HolderCommon2Val("SB","Solomon Islands") );
        l.add(new HolderCommon2Val("SO","Somalia") );
        l.add(new HolderCommon2Val("ZA","South Africa") );
        l.add(new HolderCommon2Val("GS","South Georgia and the South Sandwich Islands") );
        l.add(new HolderCommon2Val("SS","South Sudan") );
        l.add(new HolderCommon2Val("ES","Spain") );
        l.add(new HolderCommon2Val("LK","Sri Lanka") );
        l.add(new HolderCommon2Val("SD","Sudan") );
        l.add(new HolderCommon2Val("SR","Suriname") );
        l.add(new HolderCommon2Val("SJ","Svalbard and Jan Mayen") );
        l.add(new HolderCommon2Val("SZ","Swaziland") );
        l.add(new HolderCommon2Val("SE","Sweden") );
        l.add(new HolderCommon2Val("CH","Switzerland") );
        l.add(new HolderCommon2Val("SY","Syrian Arab Republic") );
        l.add(new HolderCommon2Val("TW","Taiwan, Province of China") );
        l.add(new HolderCommon2Val("TJ","Tajikistan") );
        l.add(new HolderCommon2Val("TZ","Tanzania, United Republic of") );
        l.add(new HolderCommon2Val("TH","Thailand") );
        l.add(new HolderCommon2Val("TL","Timor-Leste") );
        l.add(new HolderCommon2Val("TG","Togo") );
        l.add(new HolderCommon2Val("TK","Tokelau") );
        l.add(new HolderCommon2Val("TO","Tonga") );
        l.add(new HolderCommon2Val("TT","Trinidad and Tobago") );
        l.add(new HolderCommon2Val("TN","Tunisia") );
        l.add(new HolderCommon2Val("TR","Turkey") );
        l.add(new HolderCommon2Val("TM","Turkmenistan") );
        l.add(new HolderCommon2Val("TC","Turks and Caicos Islands") );
        l.add(new HolderCommon2Val("TV","Tuvalu") );
        l.add(new HolderCommon2Val("UG","Uganda") );
        l.add(new HolderCommon2Val("UA","Ukraine") );
        l.add(new HolderCommon2Val("AE","United Arab Emirates") );
        l.add(new HolderCommon2Val("GB","United Kingdom") );
        l.add(new HolderCommon2Val("US","United States") );
        l.add(new HolderCommon2Val("UM","United States Minor Outlying Islands") );
        l.add(new HolderCommon2Val("UY","Uruguay") );
        l.add(new HolderCommon2Val("UZ","Uzbekistan") );
        l.add(new HolderCommon2Val("VU","Vanuatu") );
        l.add(new HolderCommon2Val("VE","Venezuela, Bolivarian Republic of") );
        l.add(new HolderCommon2Val("VN","Viet Nam") );
        l.add(new HolderCommon2Val("VG","Virgin Islands, British") );
        l.add(new HolderCommon2Val("VI","Virgin Islands, U.S.") );
        l.add(new HolderCommon2Val("WF","Wallis and Futuna") );
        l.add(new HolderCommon2Val("EH","Western Sahara") );
        l.add(new HolderCommon2Val("YE","Yemen") );
        l.add(new HolderCommon2Val("ZM","Zambia") );
        l.add(new HolderCommon2Val("ZW","Zimbabwe") );
        l.add(new HolderCommon2Val("AX","\u00c5land Islands") );
        l.add(new HolderCommon2Val("CI","C\u00f4te d'Ivoire") );
        l.add(new HolderCommon2Val("HR","Croatia") );
        l.add(new HolderCommon2Val("CU","Cuba") );
        l.add(new HolderCommon2Val("CW","Cura\u00e7ao") );

        List<HolderCommon2Val> ll;
        if( ! actData.findKeyNotNull().isEmpty() ) {
            String key = actData.findKey.toUpperCase();
            ll = new ArrayList<>();
            for (HolderCommon2Val d : l) {
                if (d.mini.toUpperCase().contains(key) || d.bold.toUpperCase().contains(key)) {
                    ll.add(d);
                }
            }
        }else{
            ll = l;
        }

        recyclerViewX.setList(ll);
        recyclerViewX.setRefreshing(false);
    }

    private void clickSubmitFind(){
        String str = inpFind.getValue(false);
//        if(str.isEmpty()) return;
        actData.findKey = str;
        recyclerRefresh();
    }

    private void clickDetail(PickedDTO item){
        Intent itn = new Intent();
        itn.putExtra(AppConstant.SP_ACTIVITYRESULT, item);
        setResult(RESULT_OK, itn);
        finish();
    }

    public static void startAct(Activity act, int reqCode){
        Intent itn = new Intent(act, PickKodeNegaraActivity.class);
        act.startActivityForResult(itn, reqCode);
    }
}
