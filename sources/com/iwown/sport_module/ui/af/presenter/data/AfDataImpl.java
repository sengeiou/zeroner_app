package com.iwown.sport_module.ui.af.presenter.data;

import android.util.SparseArray;
import com.iwown.data_link.af.ModuleRouterRRIService;
import com.iwown.device_module.common.sql.TB_rri_data;
import com.iwown.device_module.common.sql.TB_rri_index_table;
import com.iwown.lib_common.json.JsonTool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.litepal.crud.DataSupport;

public class AfDataImpl implements IAfData {
    private String dataFrom;
    private long uid;

    public AfDataImpl(long uid2, String dataFrom2) {
        this.uid = uid2;
        this.dataFrom = dataFrom2;
    }

    public List<Integer> getRealData(String date) {
        return dispData(date);
    }

    public void onDestroy() {
        this.dataFrom = null;
    }

    private List<Integer> dispData(String date) {
        String rriHasDataFrom = ModuleRouterRRIService.getInstance().getRRIHasDataFrom(this.uid, date, this.dataFrom);
        List<Integer> list = new ArrayList<>();
        List<TB_rri_index_table> tb_rri_index_tables = DataSupport.where("uid=? and dataFrom=? and data_ymd=?", this.uid + "", rriHasDataFrom, date).find(TB_rri_index_table.class);
        List<TB_rri_data> tb_rri_data = DataSupport.where("uid=? and data_from=? and date=?", this.uid + "", rriHasDataFrom, date).find(TB_rri_data.class);
        Collections.sort(tb_rri_data);
        Collections.sort(tb_rri_index_tables);
        for (TB_rri_index_table tables : tb_rri_index_tables) {
            int start_seq = tables.getStart_seq();
            int end_seq = tables.getEnd_seq();
            int startIndex = -1;
            int endIndex = -1;
            int i = 0;
            while (true) {
                if (i >= tb_rri_data.size()) {
                    break;
                }
                if (((TB_rri_data) tb_rri_data.get(i)).getSeq() == start_seq) {
                    startIndex = i;
                }
                if (((TB_rri_data) tb_rri_data.get(i)).getSeq() == end_seq - 1) {
                    endIndex = i;
                    break;
                }
                i++;
            }
            if (!(startIndex == -1 || endIndex == -1)) {
                for (int i2 = startIndex; i2 < endIndex + 1; i2++) {
                    list.addAll(JsonTool.getListJson(((TB_rri_data) tb_rri_data.get(i2)).getRawData(), Integer.class));
                }
            }
        }
        return list;
    }

    public SparseArray<List<Integer>> getDataByHour(String date) {
        String rriHasDataFrom = ModuleRouterRRIService.getInstance().getRRIHasDataFrom(this.uid, date, this.dataFrom);
        SparseArray<List<Integer>> map = new SparseArray<>();
        for (int i = 0; i < 24; i++) {
            List<Integer> list = new ArrayList<>();
            List<TB_rri_data> tb_rri_data = DataSupport.where("uid=? and data_from=? and date=? and hour=?", this.uid + "", rriHasDataFrom, date, i + "").find(TB_rri_data.class);
            if (tb_rri_data != null) {
                for (int j = 0; j < tb_rri_data.size(); j++) {
                    list.addAll(JsonTool.getListJson(((TB_rri_data) tb_rri_data.get(j)).getRawData(), Integer.class));
                }
            }
            map.put(i, list);
        }
        return map;
    }
}
