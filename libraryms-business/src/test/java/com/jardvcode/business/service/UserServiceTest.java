package com.jardvcode.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.jardvcode.business.exception.GeneralServiceException;
import com.jardvcode.business.exception.NoDataFoundException;
import com.jardvcode.business.service.impl.UserServiceImpl;
import com.jardvcode.business.uitl.OrderChecker;
import com.jardvcode.business.uitl.UserDataTest;
import com.jardvcode.model.dao.UserDao;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.util.UserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Mock
	private UserDao userDao;
	
	private UserService userService;
	
	private OrderChecker<UserEntity, Long> orderChecker;
	
	@Before
	public void beforeEachTest() {
		orderChecker = new OrderChecker<UserEntity, Long>(userDao);
		userService = new UserServiceImpl(orderChecker.getEntityManagerFactory(), userDao);
	}
	
	@Test
	public void create_should_register_user() {
		UserEntity userEntityToCreate = UserDataTest.USER_SAVED;
		userEntityToCreate.setId(null);
		
		when(userDao.create(userEntityToCreate)).thenAnswer(new Answer<UserEntity>() {

			@Override
			public UserEntity answer(InvocationOnMock invocation) throws Throwable {
				UserEntity userEntity = invocation.getArgument(0);
				userEntity.setId(1L);
				return userEntity;
			}
			
		});
		
		UserEntity userEntityCreated = userService.create(userEntityToCreate);
		
		assertNotNull(userEntityCreated);
		assertEquals(new Long(1), userEntityCreated.getId());
		assertEquals("elizabethmz", userEntityCreated.getNickname());
		assertEquals("12345678", userEntityCreated.getPassword());
		assertEquals("mail7@example.com", userEntityCreated.getMail());
		assertEquals("elizabeth", userEntityCreated.getName());
		assertEquals("evangelista", userEntityCreated.getFirstName());
		assertEquals("ramirez", userEntityCreated.getLastName());
		
		verify(userDao, times(1)).create(userEntityCreated);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(userDao).create(any(UserEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void create_should_throw_exception_when_something_is_bad() {
		UserEntity userEntityToCreate = UserDataTest.USER_SAVED;
		userEntityToCreate.setId(null);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(userDao.create(userEntityToCreate)).thenThrow(exception);
		
		try {
			userService.create(userEntityToCreate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_find_user_by_id() {
		UserEntity userEntity = UserDataTest.USER_SAVED;
		
		Long id = 1L;
		
		when(userDao.findById(id)).thenReturn(userEntity);
		
		UserEntity userEntityFound = userService.findById(id);
		
		assertNotNull(userEntityFound);
		assertEquals(id, userEntityFound.getId());
		
		verify(userDao, times(1)).findById(id);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(userDao).findById(anyLong());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findById_should_throw_exception_when_user_is_not_found() {
		NoDataFoundException exception = new NoDataFoundException("error");
		
		when(userDao.findById(1L)).thenThrow(exception);
		
		try {
			userService.findById(1L);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(userDao.findById(1L)).thenThrow(exception);
		
		try {
			userService.findById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_update_user() {
		UserEntity userEntity = UserDataTest.USER_SAVED;
		
		UserEntity userEntityToUpdate = UserBuilder.createStudentUser(1L);
		userEntityToUpdate.setPassword("9876543210");
		
		when(userDao.findById(1L)).thenReturn(userEntity);
		when(userDao.update(userEntity)).thenAnswer(new Answer<UserEntity>() {
			
			@Override
			public UserEntity answer(InvocationOnMock invocation) throws Throwable {
				UserEntity userEntity = invocation.getArgument(0);
				return userEntity;
			}
			
		});
		
		UserEntity userEntityUpdated = userService.updateById(userEntityToUpdate);
		
		assertNotNull(userEntityUpdated);
		assertEquals(new Long(1), userEntityUpdated.getId());
		assertEquals("9876543210", userEntityUpdated.getPassword());
		
		verify(userDao, times(1)).findById(anyLong());
		verify(userDao, times(1)).update(any(UserEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(userDao).findById(anyLong());
		orderChecker.getInOrder().verify(userDao).update(any(UserEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void updateById_should_throw_exception_when_user_is_not_found() {
		UserEntity userEntityToUpdate = UserBuilder.createStudentUser(1L);
		userEntityToUpdate.setPassword("9876543210");
		
		NoDataFoundException exception = new NoDataFoundException("error");
		
		when(userDao.findById(1L)).thenThrow(exception);
		
		try {
			userService.updateById(userEntityToUpdate);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_throw_exception_when_something_is_bad() {
		UserEntity userEntityToUpdate = UserBuilder.createStudentUser(1L);
		userEntityToUpdate.setPassword("9876543210");
		
		RuntimeException exception = new RuntimeException("error");
		
		when(userDao.findById(1L)).thenThrow(exception);
		
		try {
			userService.updateById(userEntityToUpdate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_delete_user_by_id() {
		UserEntity userEntity = UserDataTest.USER_SAVED;
		
		ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
		
		when(userDao.findById(1L)).thenReturn(userEntity);
		doNothing().when(userDao).delete(argumentCaptor.capture());
		
		userService.deleteById(1L);
		
		UserEntity userEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(userEntityCaptured);
		assertEquals(new Long(1), userEntityCaptured.getId());
		
		verify(userDao, times(1)).findById(anyLong());
		verify(userDao, times(1)).delete(any(UserEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(userDao).findById(anyLong());
		orderChecker.getInOrder().verify(userDao).delete(any(UserEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_user_is_not_found() {
		NoDataFoundException exception = new NoDataFoundException("error");
		
		when(userDao.findById(1L)).thenThrow(exception);
		
		try {
			userService.deleteById(1L);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(userDao.findById(1L)).thenThrow(exception);
		
		try {
			userService.deleteById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}

}
