package com.mockito.demos;

public class PersonService {
	private PersonDao personDao;

	public PersonService(PersonDao personDao) {
		super();
		this.personDao = personDao;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	/**
	 * 更新
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean update(int id, String name) {
		Person person = personDao.getPerson(id);
		if (person == null) {
			return false;
		}

		Person personUpdate = new Person(person.getId(), name);
		return personDao.update(personUpdate);
	}
	
}
