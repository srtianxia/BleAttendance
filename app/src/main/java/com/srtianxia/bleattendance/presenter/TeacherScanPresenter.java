package com.srtianxia.bleattendance.presenter;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleScanResult;
import com.srtianxia.bleattendance.base.presenter.BasePresenter;
import com.srtianxia.bleattendance.base.view.BaseView;
import com.srtianxia.bleattendance.model.TeacherScanModel;
import com.srtianxia.bleattendance.ui.fragment.TeacherScanScanFragment;
import com.srtianxia.bleattendance.utils.RxSchedulersHelper;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.trello.rxlifecycle.android.FragmentEvent.DESTROY;

/**
 * Created by srtianxia on 2016/7/31.
 */
@Singleton
public class TeacherScanPresenter extends BasePresenter<TeacherScanPresenter.ITeacherScanView> {
    private TeacherScanModel mTeacherScanModel;
    private Subscription mScanSubscription;


    @Inject
    public TeacherScanPresenter(ITeacherScanView baseView) {
        super(baseView);
        mTeacherScanModel = new TeacherScanModel(getViewType().getActivity());
    }


    @Override public TeacherScanScanFragment getViewType() {
        return (TeacherScanScanFragment) getView();
    }


    public void startScan() {
        if (isScanning()) {
            mScanSubscription.unsubscribe();
        } else {
            mScanSubscription = mTeacherScanModel
                .startScan()
                .compose(getViewType().bindUntilEvent(DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxBleScanResult>() {
                    @Override public void call(RxBleScanResult rxBleScanResult) {
                        getView().addScanResult(rxBleScanResult);
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Logger.d(throwable);
                    }
                });
        }
    }


    private void scanResult(RxBleScanResult rxBleScanResult) {
        Logger.d("scanResult --->");
        getView().addScanResult(rxBleScanResult);
    }


    private void handleScanError(Throwable throwable) {
        getView().handleScanError(throwable);
    }


    private boolean isScanning() {
        return mScanSubscription != null;
    }


    public interface ITeacherScanView extends BaseView {
        void addScanResult(RxBleScanResult rxBleScanResult);

        void handleScanError(Throwable throwable);
    }
}
