package com.jardvcode.configuration;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

import com.jardvcode.model.util.PersistenceManager;
import com.mysql.jdbc.Connection;

public class DBUnitConfiguration {

	private EntityManagerFactory emf;
	private IDatabaseConnection connection;
	private IDataSet dataset;

	public EntityManagerFactory createEntityManagerFactory() throws DatabaseUnitException, SQLException, ClassNotFoundException {
		emf = Persistence.createEntityManagerFactory("library");
		Class.forName("com.mysql.jdbc.Driver");
		Connection jdbcConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/library",
				"jalexisrdv", "12345678");
		connection = new DatabaseConnection(jdbcConnection);
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);
		dataset = flatXmlDataSetBuilder
				.build(Thread.currentThread().getContextClassLoader().getResourceAsStream("test-daos-dataset.xml"));
		
		return emf;
	}

	public void closeEntityManagerFactory() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(connection, dataset);
		if (emf != null) emf.close();
	}

	public void cleanAndInsertDB() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
	}

}
