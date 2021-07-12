package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonClient {

    @Autowired
    Dao<Person> personDao;

    public void process() {
        Person person = Person.create("Dana", "Whitley", "464 Gorsuch Drive");
        System.out.println("saving: "+person);
        personDao.save(person);

        person = Person.create("Robin", "Cash", "64 Zella Park");
        System.out.println("saving: "+person);
        personDao.save(person);

        List<Person> list = personDao.loadAll();
        System.out.println("Loaded all: " + list);

    }
}