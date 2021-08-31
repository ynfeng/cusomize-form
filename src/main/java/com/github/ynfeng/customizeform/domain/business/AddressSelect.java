package com.github.ynfeng.customizeform.domain.business;

import com.github.ynfeng.customizeform.domain.AbstractComponent;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.select.Option;
import com.github.ynfeng.customizeform.domain.select.SingleSelect;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressSelect extends AbstractComponent {
    private final DatasourceFactory datasourceFactory;
    private SingleSelect<Province> provinceSingleSelect;
    private SingleSelect<City> citySingleSelect;
    private SingleSelect<Area> areaSingleSelect;

    private AddressSelect(String name, String screenName, DatasourceFactory datasourceFactory) {
        super(name, screenName);
        this.datasourceFactory = datasourceFactory;
    }

    private void province(String name, String screenName) {
        provinceSingleSelect = new SingleSelect<>(name, screenName, datasourceFactory.get("ds_province"));
    }

    private void city(String name, String screenName) {
        citySingleSelect = new SingleSelect<>(name, screenName, datasourceFactory.get("ds_city"));
    }

    private void area(String name, String screenName) {
        areaSingleSelect = new SingleSelect<>(name, screenName, datasourceFactory.get("ds_area"));
    }

    public List<Province> getProvinceList() {
        return provinceSingleSelect.getOptions()
            .stream()
            .map(Option::getData)
            .collect(Collectors.toList());
    }

    public List<City> getCityList(String provinceCode) {
        Map<String, Object> params = ImmutableMap.of("provinceCode", provinceCode);

        return citySingleSelect.getOptions(params)
            .stream()
            .map(Option::getData)
            .collect(Collectors.toList());
    }

    public static AddressSelectBuilder with(String name, String screenName, DatasourceFactory datasourceFactory) {
        return new AddressSelectBuilder(name, screenName, datasourceFactory);
    }

    public List<Area> getAreaList(String cityCode) {
        Map<String, Object> params = ImmutableMap.of("cityCode", cityCode);

        return areaSingleSelect.getOptions(params)
            .stream()
            .map(Option::getData)
            .collect(Collectors.toList());
    }

    public static class AddressSelectBuilder {
        private final AddressSelect addressSelect;

        public AddressSelectBuilder(String name, String screenName, DatasourceFactory datasourceFactory) {
            addressSelect = new AddressSelect(name, screenName, datasourceFactory);
        }

        public AddressSelectBuilder withProvince(String name, String screeName) {
            addressSelect.province(name, screeName);
            return this;
        }

        public AddressSelect build() {
            return addressSelect;
        }

        public AddressSelectBuilder withCity(String name, String screenName) {
            addressSelect.city(name, screenName);
            return this;
        }

        public AddressSelectBuilder withArea(String name, String screenName) {
            addressSelect.area(name, screenName);
            return this;
        }
    }
}
