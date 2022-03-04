package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{
   private static List<Person> DB = new ArrayList<>();
	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add( new Person(id, person.getName()));
		return 0;
	}

	@Override
	public List<Person> selectAllPeople() {
		return DB;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return DB.stream()
				.filter(person -> person.getId().equals(id))
				.findFirst();
	}

	@Override
	public int deletePersonById(UUID id) {
		Optional<Person> maybePerson = selectPersonById(id);
		if(maybePerson.isPresent()){
			DB.remove(maybePerson.get());
			return 1;
		} else {
          return 0;
		}

	}

	@Override
	public int updatePersonById(UUID id, Person updatePerson) {
		return selectPersonById(id)
				.map(person -> {
					int selectPersonToUpdate = DB.indexOf(person);
					if(selectPersonToUpdate >= 0){
						DB.set(selectPersonToUpdate, new Person(id, updatePerson.getName()));
						return 1;
					} else {
						return 0;
					}
				})
				.orElse(0);
	}
}
