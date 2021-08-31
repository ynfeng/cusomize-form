package com.github.ynfeng.customizeform.domain.business;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.datasource.Data;
import com.github.ynfeng.customizeform.domain.datasource.DataSourceStub;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactoryStub;
import java.util.List;
import org.junit.jupiter.api.Test;

class AddressSelectTest {

    @Test
    void should_get_province() {
        DatasourceFactoryStub datasourceFactory = new DatasourceFactoryStub();
        DataSourceStub dataSource = new DataSourceStub();
        datasourceFactory.setDataSource(dataSource);
        dataSource.addData(Data.of("BJ", new Province("北京", "BJ")));
        dataSource.addData(Data.of("TJ", new Province("天津", "TJ")));

        AddressSelect addressSelect = AddressSelect
            .with("address", "select an address", datasourceFactory)
            .withProvince("province", "select an province")
            .build();

        List<Province> provinceList = addressSelect.getProvinceList();

        assertThat(provinceList).containsExactly(
            new Province("北京", "BJ"),
            new Province("天津", "TJ")
        );
    }

    @Test
    void should_get_city() {
        DatasourceFactoryStub datasourceFactory = new DatasourceFactoryStub();
        DataSourceStub dataSourceStub = new DataSourceStub();

        dataSourceStub.addData(Data.of("BJC", new City("BJ", "市辖区", "BJC")));
        datasourceFactory.setDataSource(dataSourceStub);

        AddressSelect addressSelect = AddressSelect
            .with("address", "select an address", datasourceFactory)
            .withProvince("province", "select an province")
            .withCity("city", "select an city")
            .build();

        List<City> cityList = addressSelect.getCityList("BJ");

        assertThat(cityList)
            .containsExactly(new City("BJ", "市辖区", "BJC"));
    }

    @Test
    void should_get_area() {
        DatasourceFactoryStub datasourceFactory = new DatasourceFactoryStub();
        DataSourceStub dataSourceStub = new DataSourceStub();

        dataSourceStub.addData(Data.of("HD", new Area("BJC", "海淀区", "HD")));
        dataSourceStub.addData(Data.of("CY", new Area("BJC", "朝阳区", "CY")));
        datasourceFactory.setDataSource(dataSourceStub);

        AddressSelect addressSelect = AddressSelect
            .with("address", "select an address", datasourceFactory)
            .withProvince("province", "select a province")
            .withCity("city", "select an city")
            .withArea("area", "select an area")
            .build();

        List<Area> areaList = addressSelect.getAreaList("BJ");

        assertThat(areaList)
            .containsExactly(
                new Area("BJC", "海淀区", "HD"),
                new Area("BJC", "朝阳区", "CY"));
    }
}
