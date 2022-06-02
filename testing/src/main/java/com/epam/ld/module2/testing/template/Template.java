package com.epam.ld.module2.testing.template;

import lombok.Getter;

/**
 * The type Template.
 */
@Getter
public class Template {

    private final String pattern;

    public Template(String pattern) {
        pattern = pattern.replaceAll("#\\{value}", "#{tag}");
        this.pattern = pattern;
    }
}
