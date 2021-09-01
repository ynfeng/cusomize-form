package com.github.ynfeng.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.domain.business.Area;
import java.util.Map;

public class AreaDataSource implements DataSource {
    @Override
    public Datas getData() {
        return new Datas();
    }

    @Override
    public Datas getData(Map<String, Object> params) {
        Datas datas = new Datas();
        String cityCode = (String) params.get("cityCode");

        if ("SXQ".equals(cityCode)) {
            datas.appendData(Data.of("CY", new Area("SXQ", "朝阳区", "CY")));
            datas.appendData(Data.of("HD", new Area("SXQ", "海淀区", "HD")));
        }

        if ("LF".equals(cityCode)) {
            datas.appendData(Data.of("SH", new Area("LF", "三河", "SH")));
        }

        return datas;
    }
}
