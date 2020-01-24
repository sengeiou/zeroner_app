package com.iwown.my_module.feedback.network.response;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class IvServiceCode extends RetCode {
    private List<IvService> data;

    public static class IvService {
        private long chatReordId;
        private String content;
        private int contentType;
        private String rawContent;
        private long uid;

        public long getChatReordId() {
            return this.chatReordId;
        }

        public void setChatReordId(long chatReordId2) {
            this.chatReordId = chatReordId2;
        }

        public long getUid() {
            return this.uid;
        }

        public void setUid(long uid2) {
            this.uid = uid2;
        }

        public String getRawContent() {
            return this.rawContent;
        }

        public void setRawContent(String rawContent2) {
            this.rawContent = rawContent2;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content2) {
            this.content = content2;
        }

        public int getContentType() {
            return this.contentType;
        }

        public void setContentType(int contentType2) {
            this.contentType = contentType2;
        }
    }

    public List<IvService> getData() {
        return this.data;
    }

    public void setData(List<IvService> data2) {
        this.data = data2;
    }
}
