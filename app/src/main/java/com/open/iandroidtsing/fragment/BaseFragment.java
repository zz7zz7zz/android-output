package com.open.iandroidtsing.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/2.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onAttach() 1:");
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onCreate() 2:");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onCreateView() 3:");
        restoreStateFromArguments();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onActivityCreated() 4:");
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onStart() 5:");
        super.onStart();
    }

    @Override
    public void onResume() {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onResume() 6:");
        super.onResume();
    }


    @Override
    public void onPause() {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onPause() 7:");
        super.onPause();

    }

    @Override
    public void onStop() {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onStop() 8:");
        super.onStop();
    }


    @Override
    public void onDestroyView() {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onDestroyView() 9:");
        super.onDestroyView();
        saveStateToArguments();
    }

    @Override
    public void onDestroy() {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onDestroy() 10:");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onDetach() 11:");
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            onVisible();
        }
        else {
            onInVisible();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onSaveInstanceState() 100:");
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }

    //------------------------监听Fragment可见性------------------------------------
    protected void onVisible(){
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onVisible() 101:");
        onLazyLoad();
    }

    protected void onInVisible(){
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onInVisible() 102:");
    }

    protected void onLazyLoad(){
		Log.v(this.getClass().getSimpleName(),"Obj:"+this+" onLazyLoad() 103:");
    }

    protected void setFragmentViewCreated(boolean isViewCreated){
        this.isViewCreated = isViewCreated;
        if(isViewCreated){
            onLazyLoad();
        }
    }
    protected boolean isFragmentViewCreated(){
        return this.isViewCreated;
    }

    private boolean isViewCreated = false;
    //------------------------监听Fragment保存和恢复Fragment 的状态--------------------
    Bundle savedState;

    public BaseFragment() {
        super();
        if (getArguments() == null){
            setArguments(new Bundle());
        }
    }

    private void saveStateToArguments() {
        if (getView() != null)
            savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            if(null !=b){
                b.putBundle("key_save_state", savedState);
            }
        }
    }

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        if(null !=b){
            savedState = b.getBundle("key_save_state");
            if (savedState != null) {
                restoreState();
                return true;
            }
        }
        return false;
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }

    private void restoreState() {
        if (savedState != null) {
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    protected void onSaveState(Bundle outState) {

    }

}
