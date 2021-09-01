package com.github.ynfeng.customizeform.publish.http.datalinker;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.AddressSelect;

public class DataLinkerFactory {
    public DataLinker get(Component formItemType) {
        if (formItemType instanceof AddressSelect) {
            return new AddressSelectDataLinker();
        }

        return new DefaultDataLinker();
    }
}
