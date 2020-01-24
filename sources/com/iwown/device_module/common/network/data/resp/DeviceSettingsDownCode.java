package com.iwown.device_module.common.network.data.resp;

import java.util.List;

public class DeviceSettingsDownCode extends RetCode {
    private DataBean data;

    public static class DataBean {
        private int app;
        private int app_platform;
        private int command;
        private int device_platform;
        private String model;
        private int model_dfu;
        private int model_wechat;
        private List<SettingBean> setting;
        private String suffix;
        private String version;

        public class SettingBean {
            private int type;
            private int valueInt;
            private String valueStr;

            public SettingBean() {
            }

            public String getValueStr() {
                return this.valueStr;
            }

            public void setValueStr(String valueStr2) {
                this.valueStr = valueStr2;
            }

            public int getType() {
                return this.type;
            }

            public void setType(int type2) {
                this.type = type2;
            }

            public int getValueInt() {
                return this.valueInt;
            }

            public void setValueInt(int valueInt2) {
                this.valueInt = valueInt2;
            }
        }

        public int getModel_wechat() {
            return this.model_wechat;
        }

        public void setModel_wechat(int model_wechat2) {
            this.model_wechat = model_wechat2;
        }

        public int getApp() {
            return this.app;
        }

        public void setApp(int app2) {
            this.app = app2;
        }

        public int getApp_platform() {
            return this.app_platform;
        }

        public void setApp_platform(int app_platform2) {
            this.app_platform = app_platform2;
        }

        public String getModel() {
            return this.model;
        }

        public void setModel(String model2) {
            this.model = model2;
        }

        public int getModel_dfu() {
            return this.model_dfu;
        }

        public void setModel_dfu(int model_dfu2) {
            this.model_dfu = model_dfu2;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version2) {
            this.version = version2;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public void setSuffix(String suffix2) {
            this.suffix = suffix2;
        }

        public int getDevice_platform() {
            return this.device_platform;
        }

        public void setDevice_platform(int device_platform2) {
            this.device_platform = device_platform2;
        }

        public int getCommand() {
            return this.command;
        }

        public void setCommand(int command2) {
            this.command = command2;
        }

        public List<SettingBean> getSetting() {
            return this.setting;
        }

        public void setSetting(List<SettingBean> setting2) {
            this.setting = setting2;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean data2) {
        this.data = data2;
    }
}
