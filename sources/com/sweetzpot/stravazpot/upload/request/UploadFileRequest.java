package com.sweetzpot.stravazpot.upload.request;

import com.sweetzpot.stravazpot.upload.api.UploadAPI;
import com.sweetzpot.stravazpot.upload.model.DataType;
import com.sweetzpot.stravazpot.upload.model.UploadActivityType;
import com.sweetzpot.stravazpot.upload.model.UploadStatus;
import com.sweetzpot.stravazpot.upload.rest.UploadRest;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;

public class UploadFileRequest {
    private UploadActivityType activityType;
    private DataType dataType;
    private String description;
    private String externalID;
    private final File file;
    private boolean hasTrainer;
    private boolean isCommute;
    private boolean isPrivate;
    private String name;
    private final UploadRest restService;
    private final UploadAPI uploadAPI;

    public UploadFileRequest(File file2, UploadRest restService2, UploadAPI uploadAPI2) {
        this.file = file2;
        this.restService = restService2;
        this.uploadAPI = uploadAPI2;
    }

    public UploadFileRequest withDataType(DataType dataType2) {
        this.dataType = dataType2;
        return this;
    }

    public UploadFileRequest withActivityType(UploadActivityType activityType2) {
        this.activityType = activityType2;
        return this;
    }

    public UploadFileRequest withName(String name2) {
        this.name = name2;
        return this;
    }

    public UploadFileRequest withDescription(String description2) {
        this.description = description2;
        return this;
    }

    public UploadFileRequest isPrivate(boolean isPrivate2) {
        this.isPrivate = isPrivate2;
        return this;
    }

    public UploadFileRequest hasTrainer(boolean hasTrainer2) {
        this.hasTrainer = hasTrainer2;
        return this;
    }

    public UploadFileRequest isCommute(boolean isCommute2) {
        this.isCommute = isCommute2;
        return this;
    }

    public UploadFileRequest withExternalID(String externalID2) {
        this.externalID = externalID2;
        return this;
    }

    public UploadStatus execute() {
        return (UploadStatus) this.uploadAPI.execute(this.restService.upload(requestBodyFromString(this.activityType.toString()), requestBodyFromString(this.name), requestBodyFromString(this.description), booleanToInt(this.isPrivate), booleanToInt(this.hasTrainer), booleanToInt(this.isCommute), requestBodyFromString(this.dataType.toString()), requestBodyFromString(this.externalID), Part.createFormData("file", this.file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), this.file))));
    }

    private RequestBody requestBodyFromString(String str) {
        return RequestBody.create(MultipartBody.FORM, str);
    }

    private Integer booleanToInt(boolean b) {
        return Integer.valueOf(b ? 1 : 0);
    }
}
