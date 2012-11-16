package business.productsubsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.dataaccess.DataAccessUtil;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import middleware.externalinterfaces.DbConfigKey;

public class DbClassCatalog implements IDbClass {
	
	private String query;
    private String queryType;
    private final String SAVE = "Save";
    private final String READ = "Read";
    private String catName;
    private final String READ_ID = "ReadId";
	private Integer catId;
	private List<String[]>catalogs = new ArrayList<String[]>();
    
    public void saveNewCatalog(String name) throws DatabaseException {
    	//IMPLEMENT
    }
    public Integer getCatalogIdForName(String catName) throws DatabaseException {
    	this.catName = catName;
    	queryType = READ_ID;
    	IDataAccessSubsystem daccess = new DataAccessSubsystemFacade();
    	daccess.atomicRead(this);
    	return catId;
    	
    }
    
	public void buildQuery() throws DatabaseException {
		if(queryType.equals(SAVE)) {
			buildSaveQuery();
		}
		else if(queryType.equals(READ_ID)){
			buildReadIdQuery();	
		}else buildReadQuery();
		
	}
	void buildSaveQuery() throws DatabaseException {
		//IMPLEMENT
		query = ""; 
	}
	void buildReadQuery() throws DatabaseException {
	query = "SELECT * FROM CatalogType"; 
	}
	public String getDbUrl() {
		DbConfigProperties props = new DbConfigProperties();
		return props.getProperty(DbConfigKey.PRODUCT_DB_URL.getVal());
	}
	public List<String[]> getCatalogNames() throws DatabaseException { 
		queryType=READ;
		IDataAccessSubsystem dataAccess=new DataAccessSubsystemFacade();
		dataAccess.atomicRead(this);
		return catalogs;
		
	}

	public String getQuery() {
		//IMPLEMENT
		return query;
	}

	public void populateEntity(ResultSet resultSet) throws DatabaseException {
		if (queryType.equals(READ)) {
			populateCatalogEntity(resultSet);
		} else if (queryType.equals(READ_ID)) {
			populateReadIdEntity(resultSet);
		}// do nothing
		
	}
	public void populateCatalogEntity(ResultSet resultSet) throws DatabaseException {
		// do nothing
		//stub
		try{
			while(resultSet.next()){
				String next = resultSet.getString("catalogname");
				String []arr = {next};
				catalogs.add(arr);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	void buildReadIdQuery() throws DatabaseException {
		query = "SELECT * FROM CatalogType where catalogname='" + catName + "'";
	}
	
	public void populateReadIdEntity(ResultSet resultSet) throws DatabaseException {
		try {
			if(resultSet.next()) {
				Integer next = resultSet.getInt("catalogid");
				 catId = next;
				
			}
			
		} catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}
	
}
