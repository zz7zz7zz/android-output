package com.open.test.arch.mvvm.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.open.test.BR;

public class MVVMViewModel extends BaseObservable {

    private String name;
    private String psd;

    public MVVMViewModel(String name, String psd) {
        this.name = name;
        this.psd = psd;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
        notifyPropertyChanged(BR.psd);
    }
}
