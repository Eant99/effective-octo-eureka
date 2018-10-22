package com.googosoft.dao.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.pojo.Field;
import com.googosoft.pojo.Form;
import com.googosoft.util.CoderHelper;
import com.googosoft.util.CommonUtils;


@Repository
public class FormDao  {
	private static final long serialVersionUID = -8945469242429605208L;
	private static final Logger log = LoggerFactory.getLogger(Form.class);
	private static final String TABLE_PREFIX = "TBL_";
	@Autowired
	JdbcTemplate jdbcTemplate;
	public List<Form> findForm() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.gid,t.name,t.displayname,t.createtime,t.creator,t.originalhtml,t.parsehtml  from "+SystemSet.oaBz+"FORM t");
		List<Form> form =jdbcTemplate.query(sql.toString(), new Object[]{},new RowMapper<Form>() {
			@Override
			public Form mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Form form = new Form();
				form.setGid(rs.getString("gid"));
				form.setName(rs.getString("name"));
				form.setDisplayname(rs.getString("displayname"));
				form.setCreator(rs.getString("creator"));
				form.setCreatetime(rs.getString("createtime"));
				form.setOriginalhtml(rs.getString("originalhtml"));
				form.setParsehtml(rs.getString("parsehtml"));
				return form;
			}
			
		});
		return form;
	}
	
	
	public boolean saveFormInfo(Form form) {
		String sql ="insert into "+SystemSet.oaBz+"FORM(gid,name,displayname,createtime,type,creator,formdesc,saas) values(sys_guid(),?,?,to_char(sysdate,'yyyy-mm-dd HH24:mi:ss'),?,?,?,?)";
		Object[] obj = new Object[]{
				form.getName().trim(),
				form.getDisplayname().trim(),
				form.getType(),
				CommonUtils.getRybh(),
				form.getFormdesc(),
				CommonUtils.getSaas()
		};
		int i = jdbcTemplate.update(sql, obj);
		if(i>0)
			return true;
		return false;
	}
	public boolean updateFormByid( final Form form) {
		String sql ="update "+SystemSet.oaBz+"FORM set name=?,displayname=?,type=? where gid=?";
		int rows=jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, form.getName());
				ps.setString(2, form.getDisplayname());
				ps.setString(3, form.getType());
				ps.setString(4, form.getGid());
			
			}
		});
		if(rows>0)
			return true;
		return false;
		
	}
	public boolean updateFormInfo(final Form form) throws SerialException, SQLException {
		String sql ="update "+SystemSet.oaBz+"FORM set originalhtml=?,parsehtml=? where gid=? ";
		final LobHandler lobHandler=new DefaultLobHandler(); 
		jdbcTemplate.execute(sql,  
                new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					@Override
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException,
							DataAccessException {
						// TODO Auto-generated method stub
						lobCreator.setClobAsString(ps, 1, form.getOriginalhtml());
						lobCreator.setClobAsString(ps, 2, form.getParsehtml());
						ps.setString(3, form.getGid());
						
					}  
                });  
			
			return true;
		
		
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> process(Form model, Map<String, Object> datas) {
		Map<String, String> nameMap = new HashMap<String, String>();
		if(datas == null) {
			throw new NullPointerException();
		}
		String tableName = getTableName(model);
		final List<Field> fields = new ArrayList<Field>();
		for(Map.Entry<String, Object> entry : datas.entrySet()) {
			Map<String, String> fieldInfo = (Map<String, String>)entry.getValue();
			Field field = new Field();
			field.setName(fieldInfo.get("fieldname"));
			field.setTitle(fieldInfo.get("title"));
			field.setPlugins(fieldInfo.get("plugins"));
			field.setFlow(fieldInfo.get("fieldflow"));
			field.setTablename(tableName);
			field.setFormid(model.getGid());
			field.setType(fieldInfo.get("orgtype"));
			fields.add(field);
			nameMap.put(entry.getKey(), fieldInfo.get("fieldname"));
		}
		model.setFieldNum( model.getFieldNum() + fields.size());
		String check = "select count(*) from " + tableName + " where gid = 1";
		boolean isExists = true;
		try {
			jdbcTemplate.execute(check);
//			Db.queryLong(check);
		} catch(Exception e) {
			isExists = false;
		}
		StringBuilder sql = new StringBuilder();
		try {
			List<String> fieldNames =jdbcTemplate.queryForList("select name from "+SystemSet.oaBz+"field where tableName=?", new Object[]{tableName},String.class);
			/*if(!isExists) {
				sql.append("CREATE TABLE ").append(tableName).append(" (");
				sql.append("gid INT NOT NULL,");
				for(Field field : fields) {
					sql.append(field.getName());
					sql.append(" ").append(fieldSQL(field)).append(",");
				}
				sql.append("FORMID INT NOT NULL,");
				sql.append("UPDATETIME VARCHAR(20),");
				sql.append("ORDERID VARCHAR(50),");
				sql.append("TASKID  VARCHAR(50),");
				sql.append("PRIMARY KEY (gid)");
				sql.append(")");
				jdbcTemplate.update(sql.toString());
			} else {
				if(fields.size() > 0) {
					for(Field field : fields) {
						if(StringUtils.isNotEmpty(field.getName()) && 
								!fieldNames.contains(field.getName())) {
							jdbcTemplate.update("ALTER TABLE " + tableName + " ADD " + field.getName() + fieldSQL(field));
						}
					}
				}
			}*/
			for(final Field field : fields) {
				if(!fieldNames.contains(field.getName())) {
					  int[] updateCounts = jdbcTemplate.batchUpdate(  
				                "insert into "+SystemSet.oaBz+"field(gid,name,plugins,title,flow,tablename,formid) values(?,?,?,?,?,?,?)",  
				                new BatchPreparedStatementSetter() {  
				                      
				                        @Override  
				                        public void setValues(PreparedStatement ps, int i) throws SQLException {  
				                            ps.setString(1,CoderHelper.generateId());  
				                            ps.setString(2,field.getName() );  
				                            ps.setString(3, field.getPlugins()); 
				                            ps.setString(4, field.getTitle()); 
				                            ps.setString(5, field.getFlow()); 
				                            ps.setString(6, field.getTablename()); 
				                            ps.setString(7, field.getFormid()); 
				                        }  
				                          
				                        public int getBatchSize() {  
				                            return 1;  
				                        }  
				                }   
				        );  
		
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return nameMap;
	}
	private String getTableName(Form model) {
		return TABLE_PREFIX +CommonUtils.getSaas()+ model.getName();
	}
	private String fieldSQL(Field field) {
		String plugins = field.getPlugins();
        if(plugins.equalsIgnoreCase("textarea") 
        		|| plugins.equalsIgnoreCase("listctrl")) {
            return " VARCHAR(4000)  default ''";
        } else if(plugins.equalsIgnoreCase("text")) {
        	String type = field.getType();
        	if("text".equals(type)) {
        		return " VARCHAR(255)  default '' NOT NULL ";
        	} else if("int".equals(type)) {
        		return " INT NOT NULL DEFAULT 0";
        	} else if("float".equals(type)) {
        		return " FLOAT ";
        	} else {
        		return " VARCHAR(255)  DEFAULT '' NOT NULL ";
        	}
        } else if(plugins.equalsIgnoreCase("radios")) {
            return " VARCHAR(255)  DEFAULT '' NOT NULL";
        } else {
            return " VARCHAR(255)  DEFAULT '' NOT NULL ";
        }
	}
	
	
	public Form findFormInfoById(String gid) {
		String sql ="select gid,name,displayname,type,createtime,creator,originalhtml,parsehtml,fieldnum,formdesc from "+SystemSet.oaBz+"FORM where gid=?";
		Form form =jdbcTemplate.query(sql, new ResultSetExtractor<Form>(){
			@Override
			public Form extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Form form = new Form();
				if(rs.next()){
					form.setGid(rs.getString("gid"));
					form.setName(rs.getString("name"));
					form.setDisplayname(rs.getString("displayname"));
					form.setCreator(rs.getString("creator"));
					form.setCreatetime(rs.getString("createtime"));
					form.setOriginalhtml(rs.getString("originalhtml"));
					form.setParsehtml(rs.getString("parsehtml"));
					form.setFieldNum(rs.getInt("fieldnum"));
					form.setType(rs.getString("type"));
					form.setType(rs.getString("formdesc"));
				}
				return form;
			}
		}, new Object[]{gid});
		return form;
	}
	public boolean deleteFormInfo(final String gid) {
		String sql =" delete from "+SystemSet.oaBz+"FORM where gid='"+gid+"' ";
		String sqlselect =" select * from "+SystemSet.oaBz+"FORM where gid='"+gid+"' ";
		Map map=jdbcTemplate.queryForMap(sqlselect);
		System.out.println("map"+map.get("name"));
		String deleteFile=" delete from "+SystemSet.oaBz+"field where formid='"+gid+"' ";
		//String tablename="tbl_"+map.get("name");
		//String droptable =" drop table "+tablename+" ";
		String [] sqls =new String [2];
		sqls[0]=deleteFile;
		//sqls[1]=droptable;
		sqls[1]=sql;
		int[] a=jdbcTemplate.batchUpdate(sqls);
	    int h=	a.length;
	    boolean b=false;
	    if(h>0){
	    	
	    	b=true;
	    }else{
	    	b=false;
	    }
		
		return b;
	}
	
	public String  findFormContent(String name) {
		String sql="select t.parsehtml  from "+SystemSet.oaBz+"form t where t.name='"+name+"' and t.saas='"+CommonUtils.getSaas()+"'";
		Map map=jdbcTemplate.queryForMap(sql);
		Integer count=jdbcTemplate.queryForObject("select count(1) from "+SystemSet.oaBz+"form where name=? and saas=?",new Object[]{name,CommonUtils.getSaas()}, Integer.class);
		String nr="";
		if(count==0){
			nr="";
		}else{
			nr=map.get("parsehtml")+"";
		}
		return nr;
	}
	
	
	

}
