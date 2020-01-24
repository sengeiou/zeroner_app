package com.iwown.data_link.weight.archive;

import com.github.mikephil.charting.utils.Utils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AutoWeight {
    public Long getMinDis(Map<Long, List<DataSet>> hisData, Map<Long, DataSet> lastData) {
        Long uid = null;
        double dis = 1.0d;
        for (Entry<Long, DataSet> entryLastIsZero : lastData.entrySet()) {
            DataSet dsIsZero = (DataSet) entryLastIsZero.getValue();
            if (Utils.DOUBLE_EPSILON >= dsIsZero.getBmi() || Utils.DOUBLE_EPSILON >= dsIsZero.getBodyFat() || Utils.DOUBLE_EPSILON >= dsIsZero.getBoneWeight() || Utils.DOUBLE_EPSILON >= dsIsZero.getCalorie() || Utils.DOUBLE_EPSILON >= dsIsZero.getMuscule() || Utils.DOUBLE_EPSILON >= dsIsZero.getImpedance() || Utils.DOUBLE_EPSILON >= dsIsZero.getVisceralFat() || Utils.DOUBLE_EPSILON >= dsIsZero.getWater()) {
                return onlyByWeiht(hisData, lastData);
            }
            if (Utils.DOUBLE_EPSILON >= dsIsZero.getWeight()) {
                return onlyByWeiht(hisData, lastData);
            }
        }
        DataSet dsMax = dataMax(hisData);
        if (Utils.DOUBLE_EPSILON >= dsMax.getBmi() || Utils.DOUBLE_EPSILON >= dsMax.getBodyFat() || Utils.DOUBLE_EPSILON >= dsMax.getBoneWeight() || Utils.DOUBLE_EPSILON >= dsMax.getCalorie() || Utils.DOUBLE_EPSILON >= dsMax.getMuscule() || Utils.DOUBLE_EPSILON >= dsMax.getImpedance() || Utils.DOUBLE_EPSILON >= dsMax.getVisceralFat() || Utils.DOUBLE_EPSILON >= dsMax.getWater() || Utils.DOUBLE_EPSILON >= dsMax.getWeight()) {
            return onlyByWeiht(hisData, lastData);
        }
        for (Entry<Long, List<DataSet>> entry : hisData.entrySet()) {
            DataSet dsAvg = dataAvg((List) entry.getValue());
            if (dsAvg == null) {
                return onlyByWeiht(hisData, lastData);
            }
            for (Entry<Long, DataSet> entryLast : lastData.entrySet()) {
                double d = distance(dsAvg, (DataSet) entryLast.getValue(), dsMax);
                if (d < dis) {
                    dis = d;
                    uid = (Long) entry.getKey();
                }
            }
        }
        if (uid == null) {
            uid = onlyByWeiht(hisData, lastData);
        }
        return uid;
    }

    public Long onlyByWeiht(Map<Long, List<DataSet>> hisData, Map<Long, DataSet> lastData) {
        double minWeightDiff = 1.0d;
        double lastWeight = -9999.0d;
        Long uid = null;
        Iterator it = lastData.entrySet().iterator();
        if (it.hasNext()) {
            lastWeight = ((DataSet) ((Entry) it.next()).getValue()).getWeight();
        }
        for (Entry<Long, List<DataSet>> entry : hisData.entrySet()) {
            List<DataSet> dsList = (List) entry.getValue();
            if (!(dsList == null || dsList.size() == 0)) {
                DataSet dt = (DataSet) dsList.get(0);
                if (Math.abs(dt.getWeight() - lastWeight) <= minWeightDiff) {
                    uid = (Long) entry.getKey();
                    minWeightDiff = Math.abs(dt.getWeight() - lastWeight);
                }
            }
        }
        return uid;
    }

    public double distance(DataSet avg30, DataSet lastData, DataSet maxData) {
        double diffBmi = Math.pow((lastData.getBmi() / maxData.getBmi()) - (avg30.getBmi() / maxData.getBmi()), 2.0d);
        double diffBodyFat = Math.pow((lastData.getBodyFat() / maxData.getBodyFat()) - (avg30.getBodyFat() / maxData.getBodyFat()), 2.0d);
        double diffBoneWeight = Math.pow((lastData.getBoneWeight() / maxData.getBoneWeight()) - (avg30.getBoneWeight() / maxData.getBoneWeight()), 2.0d);
        double diffCalorie = Math.pow((lastData.getCalorie() / maxData.getCalorie()) - (avg30.getCalorie() / maxData.getCalorie()), 2.0d);
        double diffImpedance = Math.pow((lastData.getImpedance() / maxData.getImpedance()) - (avg30.getImpedance() / maxData.getImpedance()), 2.0d);
        double diffMuscule = Math.pow((lastData.getMuscule() / maxData.getMuscule()) - (avg30.getMuscule() / maxData.getMuscule()), 2.0d);
        double diffVisceralFat = Math.pow((lastData.getVisceralFat() / maxData.getVisceralFat()) - (avg30.getVisceralFat() / maxData.getVisceralFat()), 2.0d);
        double diffWater = Math.pow((lastData.getWater() / maxData.getWater()) - (avg30.getWater() / maxData.getWater()), 2.0d);
        return Math.sqrt(diffBmi + diffBodyFat + diffBoneWeight + diffCalorie + diffImpedance + diffMuscule + diffVisceralFat + diffWater + Math.pow((lastData.getWeight() / maxData.getWeight()) - (avg30.getWeight() / maxData.getWeight()), 2.0d));
    }

    public DataSet dataAvg(List<DataSet> dsList) {
        int dataNum = dsList.size();
        DataSet dsAvg = new DataSet();
        double diffBmi = Utils.DOUBLE_EPSILON;
        double diffBodyFat = Utils.DOUBLE_EPSILON;
        double diffBoneWeight = Utils.DOUBLE_EPSILON;
        double diffCalorie = Utils.DOUBLE_EPSILON;
        double diffImpedance = Utils.DOUBLE_EPSILON;
        double diffMuscule = Utils.DOUBLE_EPSILON;
        double diffVisceralFat = Utils.DOUBLE_EPSILON;
        double diffWater = Utils.DOUBLE_EPSILON;
        double diffWeight = Utils.DOUBLE_EPSILON;
        int invalid = 0;
        for (DataSet ds : dsList) {
            if (Utils.DOUBLE_EPSILON >= ds.getBmi() || Utils.DOUBLE_EPSILON >= ds.getBodyFat() || Utils.DOUBLE_EPSILON >= ds.getBoneWeight() || Utils.DOUBLE_EPSILON >= ds.getCalorie() || Utils.DOUBLE_EPSILON >= ds.getMuscule() || Utils.DOUBLE_EPSILON >= ds.getImpedance() || Utils.DOUBLE_EPSILON >= ds.getVisceralFat() || Utils.DOUBLE_EPSILON >= ds.getWater() || Utils.DOUBLE_EPSILON >= ds.getWeight()) {
                invalid++;
            } else {
                diffBmi += ds.getBmi();
                diffBodyFat += ds.getBodyFat();
                diffBoneWeight += ds.getBoneWeight();
                diffCalorie += ds.getCalorie();
                diffImpedance += ds.getImpedance();
                diffMuscule += ds.getMuscule();
                diffVisceralFat += ds.getVisceralFat();
                diffWater += ds.getWater();
                diffWeight += ds.getWeight();
            }
        }
        int validDays = dataNum - invalid;
        if (validDays <= 0) {
            return null;
        }
        dsAvg.setBmi(diffBmi / ((double) validDays));
        dsAvg.setBodyFat(diffBodyFat / ((double) validDays));
        dsAvg.setBoneWeight(diffBoneWeight / ((double) validDays));
        dsAvg.setCalorie(diffCalorie / ((double) validDays));
        dsAvg.setImpedance(diffImpedance / ((double) validDays));
        dsAvg.setMuscule(diffMuscule / ((double) validDays));
        dsAvg.setVisceralFat(diffVisceralFat / ((double) validDays));
        dsAvg.setWater(diffWater / ((double) validDays));
        dsAvg.setWeight(diffWeight / ((double) validDays));
        return dsAvg;
    }

    public DataSet dataMax(Map<Long, List<DataSet>> hisData) {
        DataSet dsMax = new DataSet();
        double maxBmi = Utils.DOUBLE_EPSILON;
        double maxBodyFat = Utils.DOUBLE_EPSILON;
        double maxBoneWeight = Utils.DOUBLE_EPSILON;
        double maxCalorie = Utils.DOUBLE_EPSILON;
        double maxImpedance = Utils.DOUBLE_EPSILON;
        double maxMuscule = Utils.DOUBLE_EPSILON;
        double maxVisceralFat = Utils.DOUBLE_EPSILON;
        double maxWater = Utils.DOUBLE_EPSILON;
        double maxWeight = Utils.DOUBLE_EPSILON;
        for (Entry<Long, List<DataSet>> entry : hisData.entrySet()) {
            for (DataSet ds : (List) entry.getValue()) {
                if (ds.getBmi() > maxBmi) {
                    maxBmi = ds.getBmi();
                }
                if (ds.getBodyFat() > maxBodyFat) {
                    maxBodyFat = ds.getBodyFat();
                }
                if (ds.getBoneWeight() > maxBoneWeight) {
                    maxBoneWeight = ds.getBoneWeight();
                }
                if (ds.getCalorie() > maxCalorie) {
                    maxCalorie = ds.getCalorie();
                }
                if (ds.getImpedance() > maxImpedance) {
                    maxImpedance = ds.getImpedance();
                }
                if (ds.getMuscule() > maxMuscule) {
                    maxMuscule = ds.getMuscule();
                }
                if (ds.getVisceralFat() > maxVisceralFat) {
                    maxVisceralFat = ds.getVisceralFat();
                }
                if (ds.getWater() > maxWater) {
                    maxWater = ds.getBmi();
                }
                if (ds.getWeight() > maxWeight) {
                    maxWeight = ds.getWeight();
                }
            }
        }
        dsMax.setBmi(maxBmi);
        dsMax.setBodyFat(maxBodyFat);
        dsMax.setBoneWeight(maxBoneWeight);
        dsMax.setCalorie(maxCalorie);
        dsMax.setImpedance(maxImpedance);
        dsMax.setMuscule(maxMuscule);
        dsMax.setVisceralFat(maxVisceralFat);
        dsMax.setWater(maxWater);
        dsMax.setWeight(maxWeight);
        return dsMax;
    }
}
