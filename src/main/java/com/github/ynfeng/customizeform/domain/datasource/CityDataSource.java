package com.github.ynfeng.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.domain.business.City;
import java.util.Map;

public class CityDataSource implements DataSource {
    @Override
    public Datas getData() {
        return new Datas();
    }

    @Override
    public Datas getData(Map<String, Object> params) {
        Datas datas = new Datas();
        String provinceCode = (String) params.get("provinceCode");

        if ("BJ".equals(provinceCode)) {
            datas.appendData(Data.of("BJ", new City(provinceCode, "市辖区", "SXQ")));
            return datas;
        }

        if ("HB".equals(provinceCode)) {
            datas.appendData(Data.of("LF", new City(provinceCode, "廊坊", "LF")));
            return datas;
        }

        return null;
    }
}
