package com.iwown.sport_module.net.response;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class Weather24h0verseasRsp extends RetCode {
    private List<DataBean> data;
    private String info;

    public static class DataBean {
        private String DateTime;
        private int EpochDateTime;
        private String IconPhrase;
        private boolean IsDaylight;
        private String Link;
        private String MobileLink;
        private int PrecipitationProbability;
        private TemperatureBean Temperature;
        private int WeatherIcon;

        public static class TemperatureBean {
            private String Unit;
            private int UnitType;
            private double Value;

            public int getUnitType() {
                return this.UnitType;
            }

            public void setUnitType(int UnitType2) {
                this.UnitType = UnitType2;
            }

            public double getValue() {
                return this.Value;
            }

            public void setValue(double Value2) {
                this.Value = Value2;
            }

            public String getUnit() {
                return this.Unit;
            }

            public void setUnit(String Unit2) {
                this.Unit = Unit2;
            }
        }

        public TemperatureBean getTemperature() {
            return this.Temperature;
        }

        public void setTemperature(TemperatureBean Temperature2) {
            this.Temperature = Temperature2;
        }

        public int getPrecipitationProbability() {
            return this.PrecipitationProbability;
        }

        public void setPrecipitationProbability(int PrecipitationProbability2) {
            this.PrecipitationProbability = PrecipitationProbability2;
        }

        public int getEpochDateTime() {
            return this.EpochDateTime;
        }

        public void setEpochDateTime(int EpochDateTime2) {
            this.EpochDateTime = EpochDateTime2;
        }

        public String getIconPhrase() {
            return this.IconPhrase;
        }

        public void setIconPhrase(String IconPhrase2) {
            this.IconPhrase = IconPhrase2;
        }

        public boolean isIsDaylight() {
            return this.IsDaylight;
        }

        public void setIsDaylight(boolean IsDaylight2) {
            this.IsDaylight = IsDaylight2;
        }

        public String getDateTime() {
            return this.DateTime;
        }

        public void setDateTime(String DateTime2) {
            this.DateTime = DateTime2;
        }

        public int getWeatherIcon() {
            return this.WeatherIcon;
        }

        public void setWeatherIcon(int WeatherIcon2) {
            this.WeatherIcon = WeatherIcon2;
        }

        public String getLink() {
            return this.Link;
        }

        public void setLink(String Link2) {
            this.Link = Link2;
        }

        public String getMobileLink() {
            return this.MobileLink;
        }

        public void setMobileLink(String MobileLink2) {
            this.MobileLink = MobileLink2;
        }
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info2) {
        this.info = info2;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> data2) {
        this.data = data2;
    }
}
