package com.iwown.device_module.common.Bluetooth.sync.proto;

import com.iwown.ble_module.proto.model.ProtoBufHisIndexTable;
import com.iwown.ble_module.proto.model.ProtoBufHisIndexTable.Index;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.utils.Util;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.ProtoBuf_index_80;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class ProtoBufIndex {
    public static List<ProtoBuf_index_80> parseIndex(ProtoBufHisIndexTable i7BHisIndexTable) {
        if (i7BHisIndexTable == null || i7BHisIndexTable.getIndexList() == null) {
            return null;
        }
        String data_from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        List<ProtoBuf_index_80> index_80s = new ArrayList<>();
        for (Index index : i7BHisIndexTable.getIndexList()) {
            int[] ints = parseTime((long) index.getSecond());
            KLog.e("time--" + index.getSecond());
            KLog.e("startSeq--" + index.getStartSeq() + "endSeq--" + index.getEndSeq());
            if (index.getStartSeq() < index.getEndSeq()) {
                KLog.e("需要同步的SEQ");
                KLog.e("protobuf--- startSeq--" + index.getStartSeq() + "endSeq--" + index.getEndSeq());
                DateUtil dateUtil = new DateUtil();
                int endSeq = 0;
                if (dateUtil.getYear() == ints[0] && dateUtil.getMonth() == ints[1] && dateUtil.getDay() == ints[2]) {
                    ProtoBuf_index_80 end_idx = (ProtoBuf_index_80) DataSupport.select("end_idx").where("uid=? and year=? and month=? and day=? and data_from=?  and indexType=? and isFinish=1", uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", data_from, i7BHisIndexTable.getHisDataType() + "").findLast(ProtoBuf_index_80.class);
                    if (end_idx != null) {
                        endSeq = end_idx.getEnd_idx();
                    }
                    KLog.e("endSEQ" + endSeq);
                }
                List<ProtoBuf_index_80> index_table = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=? and start_idx=? and end_idx=? and indexType=? and isFinish=1", uid + "", ints[0] + "", ints[1] + "", ints[2] + "", data_from, index.getStartSeq() + "", index.getEndSeq() + "", i7BHisIndexTable.getHisDataType() + "").find(ProtoBuf_index_80.class);
                if (index_table == null || index_table.size() <= 0) {
                    KLog.e(ints[0] + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + ints[1] + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + ints[2] + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + ints[3] + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + ints[4] + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + ints[5]);
                    ProtoBuf_index_80 index_80 = new ProtoBuf_index_80();
                    index_80.setUid(uid);
                    index_80.setYear(ints[0]);
                    index_80.setMonth(ints[1]);
                    index_80.setDay(ints[2]);
                    index_80.setHour(ints[3]);
                    index_80.setMin(ints[4]);
                    index_80.setSecond(ints[5]);
                    index_80.setTime(index.getSecond() - (Util.getTimeZone() * 3600));
                    index_80.setData_from(data_from);
                    if (endSeq <= 0 || endSeq >= index.getEndSeq()) {
                        index_80.setStart_idx(index.getStartSeq());
                    } else {
                        index_80.setStart_idx(endSeq);
                    }
                    index_80.setEnd_idx(index.getEndSeq());
                    index_80.setIndexType(i7BHisIndexTable.getHisDataType());
                    index_80s.add(index_80);
                    index_80.saveOrUpdate("uid=? and year=? and month=? and day=? and hour=? and min=? and second=? and data_from=? and start_idx=? and end_idx=? and indexType=?", uid + "", ints[0] + "", ints[1] + "", ints[2] + "", ints[3] + "", ints[4] + "", ints[5] + "", data_from, index_80.getStart_idx() + "", index_80.getEnd_idx() + "", i7BHisIndexTable.getHisDataType() + "");
                    KLog.e("PROTOBUF-SYNC" + index_80.toString());
                }
            }
        }
        Collections.sort(index_80s, new Comparator<ProtoBuf_index_80>() {
            public int compare(ProtoBuf_index_80 index1, ProtoBuf_index_80 index2) {
                int i = (index1.getYear() * 380) + (index1.getMonth() * 31) + index1.getDay();
                int i2 = (index2.getYear() * 380) + (index2.getMonth() * 31) + index2.getDay();
                if (i > i2) {
                    return -1;
                }
                if (i == i2) {
                    return 0;
                }
                return 1;
            }
        });
        KLog.d("protobuf---", JsonTool.toJson(index_80s));
        return index_80s;
    }

    private static int[] parseTime(long second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((second * 1000) - (((long) (Util.getTimeZone() * 3600)) * 1000));
        return new int[]{calendar.get(1), calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13)};
    }
}
