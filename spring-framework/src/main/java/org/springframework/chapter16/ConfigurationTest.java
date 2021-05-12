package org.springframework.chapter16;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 */
public class ConfigurationTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter16");
        SchoolConfiguration.Person person = context.getBean(SchoolConfiguration.Person.class);
        SchoolConfiguration.Student student = context.getBean(SchoolConfiguration.Student.class);
        NormalConfiguration.Worker worker = context.getBean(NormalConfiguration.Worker.class);
    }
}
