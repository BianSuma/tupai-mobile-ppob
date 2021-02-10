package com.pajakku.tupaimobile.component;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.BaseInputStatus;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.DateFunc;

import java.util.Calendar;

public class InpDate extends BaseInput {
//    private View head;
    private TextInputLayout inpLay;
    private TextInputEditText editText;
//    private List<AppCompatRadioButton> comps;
    private Long value;
    private CommonCallback<BaseInput> commonCallback;
    public Long millisMax;

    private CommonCallback<BaseInput> cbPre;
//    public TblSalaryperiod tblSalaryperiod;
//    public LongMinMax longMinMax;

    public InpDate(Activity ctx, View rv, int id, CommonCallback<BaseInput> cb, CommonCallback<BaseInput> cbPre){
        super(ctx, rv);
        commonCallback = cb;
        this.cbPre = cbPre;
        millisMax = System.currentTimeMillis();
        setInpLay(id);
    }

    // idTextInpLay bisa berupa ID dari TextInputLayout / TextInputEditText
    private void setInpLay(int idTextInpLay){
        inpLay = (TextInputLayout)findViewById(idTextInpLay);
        if(inpLay != null) editText = (TextInputEditText) inpLay.getEditText();
        else editText = (TextInputEditText)findViewById(idTextInpLay);

        editText.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_date,0);
        editText.setMovementMethod(null);
        editText.setKeyListener(null);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbPre != null) cbPre.onSuccess(InpDate.this);
//                if(tblSalaryperiod != null) {
//                    ListDayPayActivity.startAct(InpDateXX.this, tblSalaryperiod);
//                }else{
//                    showDatePicker();
//                }
                showDatePicker();
            }
        });
    }

    // SET VALUE

    public void setValueUnchange(Long v){
        if(v == null ) return;
        value = v;
//        editText.removeTextChangedListener(textWatcher);
//        editText.setText(DateFunc.msToSimpleDate(v, Utility.getTimeZoneGMT()));
        editText.setText(DateFunc.msToSimpleDate(v));
//        editText.addTextChangedListener(textWatcher);
    }

    public void setValueUnchange(String d){  // "dd-MM-yyyy
        if(d == null) return;
        setValueUnchange( DateFunc.dateIndoToLong(d, true) );
    }

    // GET VALUE

    public Long getValue(boolean nullable){
        if(value == null) return (nullable?null:0L);
        return value;
    }

    public String getValueStr(boolean nullable){
        if(value == null) return nullable?null:"";
        return DateFunc.msToListDate(value);
    }

    private void showDatePicker(){

        Long ms = getValue(true);
        if(ms == null) ms = System.currentTimeMillis();

//        Long millisMin = 1L;
        Long millisMin = null;
//        millisMax = System.currentTimeMillis() - 400000000000L;

//        if(longMinMax != null){
//            millisMin = longMinMax.min;
//        }

        final Calendar cal = Calendar.getInstance();
        if(ms != null) cal.setTimeInMillis(ms);
        else {
            if(millisMax!=null) cal.setTimeInMillis(millisMax);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);

                setValueUnchange( cal.getTimeInMillis() );
                if(commonCallback != null) commonCallback.onSuccess(InpDate.this);
            }

        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        DatePicker dp = datePickerDialog.getDatePicker();
        if(millisMin!=null) dp.setMinDate(millisMin);
        if(millisMax != null) dp.setMaxDate(millisMax);

        datePickerDialog.show();

        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
    }

    @Override
    public BaseInputStatus inputValidate() {
        BaseInputStatus bis = new BaseInputStatus();

        long val = 0;
        if(value != null) val = value;

        if(constraintMandatory){
            if( val == 0 ){
                bis.valid = false;
                bis.invalidTextRes = R.string.clutility_validator_mandatory;
            }
        }

        return bis;
    }

    @Override
    protected boolean isShow() {
        if(inpLay != null) return inpLay.getVisibility() == View.VISIBLE;
        else return editText.getVisibility() == View.VISIBLE;
    }

    public void setVisible(Boolean b){
        if(b == null) return;
        int vis = b ? View.VISIBLE: View.GONE;
        if(inpLay != null) inpLay.setVisibility(vis);
        if(editText != null) editText.setVisibility(vis);
        setVisibleInfoWarn(b);
    }
}
