package com.sweetzpot.stravazpot.upload.api;

import com.sweetzpot.stravazpot.common.api.Config;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.upload.request.CheckUploadStatusRequest;
import com.sweetzpot.stravazpot.upload.request.UploadFileRequest;
import com.sweetzpot.stravazpot.upload.rest.UploadRest;
import java.io.File;

public class UploadAPI extends StravaAPI {
    public UploadAPI(Config config) {
        super(config);
    }

    public UploadFileRequest uploadFile(File file) {
        return new UploadFileRequest(file, (UploadRest) getAPI(UploadRest.class), this);
    }

    public CheckUploadStatusRequest checkUploadStatus(int id) {
        return new CheckUploadStatusRequest(id, (UploadRest) getAPI(UploadRest.class), this);
    }
}
