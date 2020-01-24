package com.iwown.ble_module.zg_ble.data.model;

public class DataDateStorage {
    private DataStoreDate[] storeDatesList;
    private int storeDays;
    private int[] storeLocation;

    public class DataStoreDate {
        private int day;
        private int month;
        private int year;

        public DataStoreDate() {
        }
    }
}
