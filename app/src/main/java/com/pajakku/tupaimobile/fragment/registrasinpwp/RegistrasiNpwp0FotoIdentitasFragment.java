package com.pajakku.tupaimobile.fragment.registrasinpwp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.activity.KodeWilayahActivity;
import com.pajakku.tupaimobile.activity.PickKodeNegaraActivity;
import com.pajakku.tupaimobile.activity.RegistrasiNpwpActivity;
import com.pajakku.tupaimobile.api.ApiReqUpload;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpFile;
import com.pajakku.tupaimobile.component.InpPicker;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.UploadedKtp;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RegistrasiNpwp0FotoIdentitasFragment extends BaseFragment {

    private InpPicker inpNegara;
    private InpFile inpFile;

    private ActDataRegistrasiNpwp getActData(){
        return ((RegistrasiNpwpActivity)activity).actData;
    }

    public RegistrasiNpwp0FotoIdentitasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity context = activity;
        final ActDataRegistrasiNpwp actData = getActData();
        final ReqValidasi1 saving = actData.savingValidasi1;
        View v = onCreateView(R.layout.fragment_registrasinpwp0_fotoidentitas, inflater, container, savedInstanceState);

        inpNegara = setCompInpPicker(R.id.registrasinpwp0fotoidentitas_inp_negara, AppConstant.ACTRES_COMMON, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                PickKodeNegaraActivity.startAct(context, ((InpPicker)data).actRes);
            }
        }, new CommonCallback<PickedDTO>() {
            @Override
            public void onSuccess(PickedDTO data) {
                actData.tmpValidasi1kdNegaraWp = (HolderCommon2Val)data.object;
                saving.kdNegaraWp = actData.tmpValidasi1kdNegaraWp.mini;
            }
        });
        inpNegara.constraintMandatory = true;
        inpNegara.setWarn(R.id.registrasinpwp0fotoidentitas_lblwarn_negara);

        inpFile = setCompInpFile(R.id.registrasinpwp0fotoidentitas_inp_foto, AppConstant.ACTRES_FILE_BROWSER, null, new CommonCallback<FileBrowserItem>() {
            @Override
            public void onSuccess(FileBrowserItem data) {
                actData.savingKtpPath = data.path;
            }
        });
        inpFile.constraintMandatory = true;
        inpFile.setWarn(R.id.registrasinpwp0fotoidentitas_lblwarn_foto);
        inpFile.setFotoView(R.id.registrasinpwp0fotoidentitas_img_preview);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        ActDataRegistrasiNpwp actData = getActData();

        PickedDTO negara = null;
        if(actData.tmpValidasi1kdNegaraWp != null){
            HolderCommon2Val holder = new HolderCommon2Val();
            holder.bold = actData.tmpValidasi1kdNegaraWp.bold;
            holder.mini = actData.tmpValidasi1kdNegaraWp.mini;

            negara = new PickedDTO();
            negara.name = holder.mini + " " + holder.bold;
            negara.object = holder;
        }

        inpNegara.setValueUnchange(negara);
        inpFile.setValueUnchange(actData.savingKtpPath);

        checkInput(null);
    }

    @Override
    public void onPause(){
        super.onPause();

        saveObj();
    }

    private void saveObj(){
        ActDataRegistrasiNpwp actData = getActData();

        PickedDTO negara = inpNegara.getValue();

        actData.savingValidasi1.kdNegaraWp = negara != null ? ((HolderCommon2Val)negara.object).mini : "";
        actData.savingKtpPath = inpFile.getValuePath(false);
    }

    @Override
    public void save(){
        final BaseActivity context = (BaseActivity) activity;
        saveObj();

        final ActDataRegistrasiNpwp actData = getActData();

        ApiReqUpload.eregUploadKtp(activity, new RequestParamConfig(), actData.savingKtpPath,
                new CommonCallback<UploadedKtp>() {
                    @Override
                    public void onSuccess(UploadedKtp data) {
                        actData.savingValidasi1.photoKtpFilename = data.name;
                        context.nextFrag();
                    }
                });

    }

    @Override
    public void onChangeInputComponent(BaseActivity act, BaseInput bi, boolean isValid){
        ActDataRegistrasiNpwp actData = getActData();

        PickedDTO negara = inpNegara.getValue();
        String kodeNegara = negara != null ? ((HolderCommon2Val)negara.object).mini : "";

        inpFile.setHint( kodeNegara.equalsIgnoreCase(AppConstant.EREG_KODE_NEGARA_INDO) ? "File Foto KTP" : "File Foto Paspor" );
    }

}
