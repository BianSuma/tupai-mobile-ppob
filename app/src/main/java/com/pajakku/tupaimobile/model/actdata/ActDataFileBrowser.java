package com.pajakku.tupaimobile.model.actdata;


import com.pajakku.tupaimobile.model.dto.FileBrowserItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActDataFileBrowser implements Serializable {
    public List<FileBrowserItem> siList;
    public int dirDeep;

    public ActDataFileBrowser(){
        siList = new ArrayList<>();
    }
}
