package com.open.test.arch.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.open.test.arch.mvp.model.MvpModel;
import com.open.test.arch.mvp.presenter.MvpPresenter;

import com.open.test.R;

public class MvpActivity extends Activity implements IMVPView{

    MvpPresenter presenter = new MvpPresenter(this,new MvpModel());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arch_mvp);

        presenter.onUpdate();
    }

    @Override
    public void onUpdateView(MvpModel model) {
        ((TextView)(findViewById(R.id.arch_name))).setText(model.getName());
        ((TextView)(findViewById(R.id.arch_psd))).setText(model.getPsd());
    }
}
