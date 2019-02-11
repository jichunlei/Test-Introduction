package com.jmock.demos;
/**
 * PersonService测试类
 * @author xianzilei
 *
 */

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.mockito.demos.Person;
import com.mockito.demos.PersonDao;
import com.mockito.demos.PersonService;

public class PersonServiceTest {
	private PersonDao personDao;
	private PersonService personService;
	
	@Before
    public void setUp() throws Exception {
         personDao = mock(PersonDao.class);
         when(personDao.getPerson(1)).thenReturn(new Person(1, "person1"));
         when(personDao.update(isA(Person.class))).thenReturn(true);
         personService=new PersonService(personDao);
    }
	
	@Test
    public void testUpdate() throws Exception {
		boolean result = personService.update(1, "person1");
		assertTrue("must be true!", result);
		//验证是否执行过一次getPerson(1)
        verify(personDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(personDao, times(1)).update(isA(Person.class));
	}
	
	@Test
    public void testUpdateNotFind() throws Exception {
		boolean result = personService.update(2, "person2");
		assertTrue("must be false!", result);
		//验证是否执行过一次getPerson(1)
        verify(personDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(personDao, times(1)).update(isA(Person.class));
	}
}
