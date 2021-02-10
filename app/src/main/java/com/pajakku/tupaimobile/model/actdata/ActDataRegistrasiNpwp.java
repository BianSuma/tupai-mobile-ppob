package com.pajakku.tupaimobile.model.actdata;

import com.pajakku.tupaimobile.model.actparam.ActParamRefundForm;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqRefund;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.model.spinitem.SpinItem;
import com.pajakku.tupaimobile.model.spinitem.SpinItem2StrCustom;

import java.io.Serializable;

public class ActDataRegistrasiNpwp implements Serializable {
    public String savingKtpPath;
    public ReqValidasi1 savingValidasi1;
//    public String savingKitas;
    public String savingTanggungan;
    public String savingJmlPenghasilan;
    public String savingVideoPath;
    public EregDataByEmail savingEregDataByEmail;  // biarkan null krn didapat dri back-end

    public WilayahDTO tmpValidasi1AlmKTPKdWilayah;
    public HolderCommon2Val tmpValidasi1kdNegaraWp;
    public SpinItem tmpEregDataByEmailKluklu1JnsPegawai;
    public PickedDTO tmpEregDataByEmailKluklu1KdKlu;
    public PickedDTO tmpEregDataByEmailklu2klu2KdKlu;
    public SpinItem tmpEregDataByEmailklu2klu2ThBukuAwal;
    public SpinItem tmpEregDataByEmailklu2klu2ThBukuAkhir;
    public WilayahDTO tmpEregDataByEmailUsahaAlmUsahaKdWilayah;

    public ActDataRegistrasiNpwp(){
        savingValidasi1 = new ReqValidasi1();
    }

    public void setNulled(){
        SpinItem2StrCustom spin = null;
        PickedDTO pickedDTO;
        HolderCommon2Val holderCommon2Val;

        if( ! savingEregDataByEmail.klu.klu1JnsPegawaiNotNull().isEmpty() ) {
            spin = new SpinItem2StrCustom();
            spin.idStr = spin.str0 = savingEregDataByEmail.klu.klu1JnsPegawai;
        }
        tmpEregDataByEmailKluklu1JnsPegawai = spin;

        pickedDTO = null;
        if( ! savingEregDataByEmail.klu.klu1KdKluNotNull().isEmpty() ) {
            holderCommon2Val = new HolderCommon2Val();
            holderCommon2Val.bold = savingEregDataByEmail.klu.klu1KdKlu;
            pickedDTO = holderCommon2Val.toPickedDTO(0);
        }
        tmpEregDataByEmailKluklu1KdKlu = pickedDTO;

        pickedDTO = null;
        if( ! savingEregDataByEmail.klu2.klu2KdKluNotNull().isEmpty() ) {
            holderCommon2Val = new HolderCommon2Val();
            holderCommon2Val.bold = savingEregDataByEmail.klu2.klu2KdKlu;
            pickedDTO = holderCommon2Val.toPickedDTO(0);
        }
        tmpEregDataByEmailklu2klu2KdKlu = pickedDTO;

        spin = null;
        if( ! savingEregDataByEmail.klu2.klu2ThBukuAwalNotNull().isEmpty() ) {
            spin = new SpinItem2StrCustom();
            spin.idStr = spin.str0 = savingEregDataByEmail.klu2.klu2ThBukuAwal;
        }
        tmpEregDataByEmailklu2klu2ThBukuAwal = spin;

        spin = null;
        if( ! savingEregDataByEmail.klu2.klu2ThBukuAkhirNotNull().isEmpty() ) {
            spin = new SpinItem2StrCustom();
            spin.idStr = spin.str0 = savingEregDataByEmail.klu2.klu2ThBukuAkhir;
        }
        tmpEregDataByEmailklu2klu2ThBukuAkhir = spin;

        WilayahDTO wilayah = null;
        if( ! savingEregDataByEmail.usaha.almUsahaKdWilayahNotNull().isEmpty() ) {
            wilayah = new WilayahDTO();
            wilayah.id = savingEregDataByEmail.usaha.almUsahaKdWilayah;
        }
        tmpEregDataByEmailUsahaAlmUsahaKdWilayah = wilayah;
    }
}
