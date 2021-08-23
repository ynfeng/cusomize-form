package com.github.ynfeng.customizeform.domain.text;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SingleLineTextTest {

    @Test
    void should_create() {
        SingleLineText singleLineText = new SingleLineText("input1", "一个单行文本");

        assertThat(singleLineText.name()).isEqualTo("input1");
        assertThat(singleLineText.screenName()).isEqualTo("一个单行文本");
    }
}
