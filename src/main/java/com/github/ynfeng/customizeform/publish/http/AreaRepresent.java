package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.domain.business.Area;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "areaList", itemRelation = "area")
public class AreaRepresent extends DataRepresent {
    private final String name;
    private final String code;
    private final String cityCode;

    private AreaRepresent(String name, String code, String cityCode) {
        this.name = name;
        this.code = code;
        this.cityCode = cityCode;
    }

    public static AreaRepresent fromDomain(Area area) {
        return new AreaRepresent(area.getName(), area.getCode(), area.getCityCode());
    }
}
