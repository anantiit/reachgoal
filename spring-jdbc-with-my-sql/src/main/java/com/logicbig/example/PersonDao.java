package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonDao implements Dao<Person> {
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("PERSON").usingGeneratedKeyColumns("ID");
    }

    @Override
    public void save(Person person) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(person);
        jdbcInsert.execute(parameters);
    }

    @Override
    public Person load(long id) {
        List<Person> persons = jdbcTemplate.query("select * from Person where id =?",
                new Object[]{id}, (resultSet, i) -> {
                    return toPerson(resultSet);
                });

        if (persons.size() == 1) {
            return persons.get(0);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from PERSON where id = ?", id);
    }

    @Override
    public void update(Person person) {
        throw new UnsupportedOperationException();
    }


    @Override
    public List<Person> loadAll() {
        return jdbcTemplate.query("select * from Person", (resultSet, i) -> {
            return toPerson(resultSet);
        });
    }

    private Person toPerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("ID"));
        person.setFirstName(resultSet.getString("FIRST_NAME"));
        person.setLastName(resultSet.getString("LAST_NAME"));
        person.setAddress(resultSet.getString("ADDRESS"));
        return person;
    }
}