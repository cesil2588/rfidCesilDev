package com.systemk.spyder.Service.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Service.StatsCompanyGroupService;
import com.systemk.spyder.Service.CustomBean.BartagSort;

@Service
public class StatsCompanyGroupServiceImpl implements StatsCompanyGroupService{
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Override
	public List<BartagSort> step1() {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(b.amount) AS amount, " +  
							  "c.company_seq " +   
						 "FROM bartag_master b " +  
				   "INNER JOIN company_info c " +  
				   		   "ON b.production_company_seq = c.company_seq " +  
				     "GROUP BY c.company_seq";
		return template.query(query, new step1Mapper());
	}
	
	public class step1Mapper implements RowMapper<BartagSort> {
	       
		public BartagSort mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			BartagSort bartagSort = new BartagSort();
			bartagSort.setAmount(rs.getLong("amount"));
			bartagSort.setCompanySeq(rs.getLong("company_seq"));
        	
        	return bartagSort;
        }
    }
	
	@Override
	public List<BartagSort> step2() {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(b.amount) AS amount, " +  
							  "c.company_seq, " +
							  "ISNULL( b.detail_production_company_name, '-' ) AS detail_production_company_name  " +
						 "FROM bartag_master b " +  
				   "INNER JOIN company_info c " +  
				   		   "ON b.production_company_seq = c.company_seq " +  
				     "GROUP BY c.company_seq, b.detail_production_company_name ";
		return template.query(query, new step2Mapper());
	}
	
	public class step2Mapper implements RowMapper<BartagSort> {
	       
		public BartagSort mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			BartagSort bartagSort = new BartagSort();
			bartagSort.setAmount(rs.getLong("amount"));
			bartagSort.setCompanySeq(rs.getLong("company_seq"));
			bartagSort.setDetailProductionCompanyName(rs.getString("detail_production_company_name"));
        	
        	return bartagSort;
        }
    }
	
	@Override
	public List<BartagSort> step3() {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(b.amount) AS amount, " +  
							  "c.company_seq, " +
							  "ISNULL( b.detail_production_company_name, '-' ) AS detail_production_company_name, " +
							  "b.style " + 
						 "FROM bartag_master b " +  
				   "INNER JOIN company_info c " +  
				   		   "ON b.production_company_seq = c.company_seq " +  
				     "GROUP BY c.company_seq, b.detail_production_company_name, b.style ";
		return template.query(query, new step3Mapper());
	}
	
	public class step3Mapper implements RowMapper<BartagSort> {
	       
		public BartagSort mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			BartagSort bartagSort = new BartagSort();
			bartagSort.setAmount(rs.getLong("amount"));
			bartagSort.setCompanySeq(rs.getLong("company_seq"));
			bartagSort.setDetailProductionCompanyName(rs.getString("detail_production_company_name"));
			bartagSort.setStyle(rs.getString("style"));
        	
        	return bartagSort;
        }
    }
	
	@Override
	public List<BartagSort> step4() {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(b.amount) AS amount, " +  
							  "c.company_seq, " +
							  "ISNULL( b.detail_production_company_name, '-' ) AS detail_production_company_name, " +
							  "b.style, " + 
							  "b.color " + 
						 "FROM bartag_master b " +  
				   "INNER JOIN company_info c " +  
				   		   "ON b.production_company_seq = c.company_seq " +  
				     "GROUP BY c.company_seq, b.detail_production_company_name, b.style, b.color ";
		return template.query(query, new step4Mapper());
	}
	
	public class step4Mapper implements RowMapper<BartagSort> {
	       
		public BartagSort mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			BartagSort bartagSort = new BartagSort();
			bartagSort.setAmount(rs.getLong("amount"));
			bartagSort.setCompanySeq(rs.getLong("company_seq"));
			bartagSort.setDetailProductionCompanyName(rs.getString("detail_production_company_name"));
			bartagSort.setStyle(rs.getString("style"));
			bartagSort.setColor(rs.getString("color"));
        	
        	return bartagSort;
        }
    }
	
	@Override
	public List<BartagSort> step5() {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(b.amount) AS amount, " +  
							  "c.company_seq, " +
							  "ISNULL( b.detail_production_company_name, '-' ) AS detail_production_company_name, " +
							  "b.style, " + 
							  "b.color, " +
							  "b.size " +
						 "FROM bartag_master b " +  
				   "INNER JOIN company_info c " +  
				   		   "ON b.production_company_seq = c.company_seq " +  
				     "GROUP BY c.company_seq, b.detail_production_company_name, b.style, b.color, b.size ";
		return template.query(query, new step5Mapper());
	}
	
	public class step5Mapper implements RowMapper<BartagSort> {
	       
		public BartagSort mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			BartagSort bartagSort = new BartagSort();
			bartagSort.setAmount(rs.getLong("amount"));
			bartagSort.setCompanySeq(rs.getLong("company_seq"));
			bartagSort.setDetailProductionCompanyName(rs.getString("detail_production_company_name"));
			bartagSort.setStyle(rs.getString("style"));
			bartagSort.setColor(rs.getString("color"));
			bartagSort.setSize(rs.getString("size"));
        	
        	return bartagSort;
        }
    }
	
	@Override
	public List<BartagSort> step6() {
	
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(b.amount) AS amount, " +  
							  "c.company_seq, " +
							  "ISNULL( b.detail_production_company_name, '-' ) AS detail_production_company_name, " +
							  "b.style, " + 
							  "b.color, " +
							  "b.size, " +
							  "b.order_degree, " + 
							  "b.bartag_seq " +
						 "FROM bartag_master b " +  
				   "INNER JOIN company_info c " +  
				   		   "ON b.production_company_seq = c.company_seq " +  
				     "GROUP BY c.company_seq, b.detail_production_company_name, b.style, b.color, b.size, b.order_degree, b.bartag_seq ";
		return template.query(query, new step6Mapper());
	}
	
	public class step6Mapper implements RowMapper<BartagSort> {
	       
		public BartagSort mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			BartagSort bartagSort = new BartagSort();
			bartagSort.setAmount(rs.getLong("amount"));
			bartagSort.setCompanySeq(rs.getLong("company_seq"));
			bartagSort.setDetailProductionCompanyName(rs.getString("detail_production_company_name"));
			bartagSort.setStyle(rs.getString("style"));
			bartagSort.setColor(rs.getString("color"));
			bartagSort.setSize(rs.getString("size"));
			bartagSort.setOrderDegree(rs.getString("order_degree"));
			bartagSort.setBartagSeq(rs.getLong("bartag_seq"));
        	
        	return bartagSort;
        }
    }
}
