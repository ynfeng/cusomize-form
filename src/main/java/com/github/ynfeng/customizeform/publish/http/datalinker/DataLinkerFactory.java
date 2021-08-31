package com.github.ynfeng.customizeform.publish.http.datalinker;

import com.github.ynfeng.customizeform.domain.Component;

public class DataLinkerFactory {
    public DataLinker get(Class<? extends Component> formItemType) {
        return new DefaultDataLinker();
    }
}
