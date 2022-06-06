package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

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
            res = res.replaceFirst("#\\{tag}", tags.get(i));

        return res;
    }

    public List<String> getTags() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your tags: \n");
        String line = scanner.nextLine();

        return Arrays.stream(line.split(" ")).collect(Collectors.toList());
    }
}
