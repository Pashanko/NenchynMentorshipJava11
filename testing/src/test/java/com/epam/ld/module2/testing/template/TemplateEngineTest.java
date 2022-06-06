package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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


    @ParameterizedTest
    @CsvFileSource(resources = "/template.csv")
    void generateMessageValidParams(Template template) throws InvalidPropertiesFormatException {
        List<String> tags = List.of("tag", "tag", "tag");

        doReturn(tags).when(templateEngine).getTags();
        String result = templateEngine.generateMessage(template, new Client());

        assertNotNull(result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/template.csv")
    void generateMessageInvalidParamsTooManyTags(Template template) {
        List<String> tags = List.of("tag", "tag", "tag", "tag");

        doReturn(tags).when(templateEngine).getTags();
        assertThrows(InvalidPropertiesFormatException.class,
                () -> templateEngine.generateMessage(template, new Client()));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/template.csv")
    void generateMessageInvalidParamsNotEnoughTags(Template template) {
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
