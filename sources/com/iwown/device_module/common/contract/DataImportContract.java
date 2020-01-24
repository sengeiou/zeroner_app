package com.iwown.device_module.common.contract;

public class DataImportContract {

    public interface DataImportPresenter {
        void downloadSport(long j, int i, String str);

        void downloadStatisticsSport(long j);

        void sportToGpsSegment(long j, long j2, long j3);
    }

    public interface DataImportView {
        void loadFail();

        void loadSuccess();

        void onProgress(int i);

        void sportToGpsOk();

        void uploadGpsFail(int i);

        void uploadGpsSuccess(int i);
    }
}
