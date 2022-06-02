package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class TemplateEngineTest {

    @Spy
    TemplateEngine templateEngine;

    @BeforeEach
    public void preTestInit() {
        templateEngine = spy(new TemplateEngine());
    }

    @Test
    void generateMessageValidParams() throws InvalidPropertiesFormatException {
        Template template = new Template("#{value} #{value} #{value}");
        List<String> tags = List.of("tag", "tag", "tag");

        doReturn(tags).when(templateEngine).getTags();
        String result = templateEngine.generateMessage(template, new Client());

        assertNotNull(result);
    }

    @Test
    void generateMessageInvalidParamsTooManyTags() {
        Template template = new Template("#{value} #{value} #{value}");
        List<String> tags = List.of("tag", "tag", "tag", "tag");

        doReturn(tags).when(templateEngine).getTags();
        assertThrows(InvalidPropertiesFormatException.class,
                () -> templateEngine.generateMessage(template, new Client()));
    }

    @Test
    void generateMessageInvalidParamsNotEnoughTags() {
        Template template = new Template("#{value} #{value} #{value}");
        List<String> tags = List.of("tag", "tag");

        doReturn(tags).when(templateEngine).getTags();
        assertThrows(InvalidPropertiesFormatException.class,
                () -> templateEngine.generateMessage(template, new Client()));
    }

    @Test
    void generateMessageInvalidParamsNullTag() {
        Template template = new Template("");
        doReturn(null).when(templateEngine).getTags();
        assertThrows(InvalidPropertiesFormatException.class,
                () -> templateEngine.generateMessage(template, new Client()));
    }
}
