package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Objects;

/**
 * The type Template engine.
 */
@NoArgsConstructor
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) throws InvalidPropertiesFormatException {
        String res = template.getPattern();

        long amountOfTags = Arrays.stream(res.split(" "))
                .filter(s -> s.equals("#{tag}"))
                .count();

        List<String> tags = getTags();

        if(Objects.isNull(tags))
            throw new InvalidPropertiesFormatException("Null isn't valid for a tag");

        if(tags.size() != amountOfTags)
            throw new InvalidPropertiesFormatException("The number of tags isn't appropriate for this message");

        for (int i = 0; i < amountOfTags; i++)
            res = res.replace("#{tag}", tags.get(i));

        return res;
    }

    public List<String> getTags() {
        return List.of("tag", "tag", "tag");
    }
}
