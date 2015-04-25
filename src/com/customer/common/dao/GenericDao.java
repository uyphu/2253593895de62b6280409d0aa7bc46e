package com.customer.common.dao;

import java.util.List;

import com.customer.exception.DatabaseException;

public interface GenericDao {

	public <T> void create(T t);
	public <T> String createWithKey(T t);
	public <T> Long createWithID(T t);
	public <T> void update(Class<T> clazz, Long id, T t) throws DatabaseException;
	public <T> void update(Class<T> clazz, String key, T t) throws DatabaseException;
	public <T> T getById(Class<T> clazz, Long id) throws DatabaseException;
	public <T> T getByKey(Class<T> clazz, String key) throws DatabaseException;
	public <T> List<T> list(Class<T> clazz);
	public <T> void delete(Class<T> clazz, Long id) throws DatabaseException;
	public <T> void deleteByKey(Class<T> clazz, String key) throws DatabaseException;  

}
