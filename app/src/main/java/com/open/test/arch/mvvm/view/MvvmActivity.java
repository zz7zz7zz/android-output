package com.open.test.arch.mvvm.view;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.open.test.arch.mvvm.viewmodel.MVVMViewModel;
import com.open.test.databinding.ArchMvvmBinding;

import com.open.test.R;

public class MvvmActivity extends Activity {

    MVVMViewModel viewModel;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.arch_mvvm);

        ArchMvvmBinding binding = DataBindingUtil.setContentView(this,R.layout.arch_mvvm);
        viewModel = new MVVMViewModel("Yang","Psd");
        binding.setData(viewModel);

        new Thread(new Runnable() {

            int count = 0;
            @Override
            public void run() {

                while (true){

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    count++;
                    viewModel.setName("Yang " + count);
                    viewModel.setPsd("Psd " + count);
                }

            }
        }).start();
    }


}
