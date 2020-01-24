package com.sweetzpot.stravazpot.upload.request;

import com.sweetzpot.stravazpot.upload.api.UploadAPI;
import com.sweetzpot.stravazpot.upload.model.UploadStatus;
import com.sweetzpot.stravazpot.upload.rest.UploadRest;

public class CheckUploadStatusRequest {
    private final int id;
    private final UploadAPI uploadAPI;
    private final UploadRest uploadRest;

    public CheckUploadStatusRequest(int id2, UploadRest uploadRest2, UploadAPI uploadAPI2) {
        this.id = id2;
        this.uploadRest = uploadRest2;
        this.uploadAPI = uploadAPI2;
    }

    public UploadStatus execute() {
        return (UploadStatus) this.uploadAPI.execute(this.uploadRest.checkUploadStatus(this.id));
    }
}
