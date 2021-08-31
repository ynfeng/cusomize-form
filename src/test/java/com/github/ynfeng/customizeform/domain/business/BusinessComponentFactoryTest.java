package com.github.ynfeng.customizeform.domain.business;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.datasource.DefaultDataSourceFactory;
import com.github.ynfeng.customizeform.domain.text.SingleLineText;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessComponentFactoryTest {
    private BusinessComponentFactory factory;

    @BeforeEach
    void setup() {
        factory = new BusinessComponentFactory(new DefaultDataSourceFactory());
    }

    @Test
    void should_create_department_select() {
        Component component = factory.create("a name", "a screen name", "DepartmentSelect", Maps.newHashMap());

        assertThat(component).isInstanceOf(DepartmentSelect.class);
    }

    @Test
    void should_create_single_line_text() {
        Component component = factory.create("a name", "a screen name", "SingleLineText", Maps.newHashMap());

        assertThat(component).isInstanceOf(SingleLineText.class);
    }

    @Test
    void should_create_address_select() {
        Component component = factory.create("a name", "a screen name", "AddressSelect");

        assertThat(component).isInstanceOf(AddressSelect.class);
    }
}
