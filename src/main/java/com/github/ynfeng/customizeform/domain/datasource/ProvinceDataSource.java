package com.github.ynfeng.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.domain.business.Province;
import java.util.Map;

public class ProvinceDataSource implements DataSource {
    @Override
    public Datas getData() {
        Datas datas = new Datas();
        datas.appendData(Data.of("BJ", new Province("北京", "BJ")));
        datas.appendData(Data.of("HB", new Province("河北", "HB")));
        return datas;
    }

    @Override
    public Datas getData(Map<String, Object> params) {
        return getData();
    }
}
