package com.systemk.spyder.Service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.Response.CompanyResult;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.CompanyInfoSpecification;
import com.systemk.spyder.Service.CompanyService;
import com.systemk.spyder.Service.CustomBean.Select.SelectCompanyModel;
import com.systemk.spyder.Service.Mapper.Select.SelectCompanyModelMapper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional(readOnly = true)
	@Override
	public Page<CompanyInfo> findAll(Long companySeq, String type, String search, String option, Pageable pageable) throws Exception{

		Page<CompanyInfo> page = null;

		Specifications<CompanyInfo> specifications = null;

		if(companySeq != 0){
			specifications = Specifications.where(CompanyInfoSpecification.companySeqEqual(companySeq));
		}

		if(!type.equals("all") && companySeq == 0){
			specifications = Specifications.where(CompanyInfoSpecification.typeEqual(type));
		} else if(!type.equals("all") && companySeq != 0){
			specifications = specifications.and(CompanyInfoSpecification.typeEqual(type));
		}

		if(!search.equals("") && option.equals("telNo")){
			specifications = specifications.and(CompanyInfoSpecification.telNoContain(search));
		}

		page = companyInfoRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public String findAll() {
		String value = (String) redisTemplate.opsForValue().get("companyList");

		if(value == null || value.isEmpty()){

			ObjectMapper mapper = new ObjectMapper();

			String employeeAsPrettyString;
			try {
				List<CompanyResult> companyList = companyInfoRepository.findAllByOrderByNameAsc()
																	   .stream().map(obj -> new CompanyResult(obj))
																	   .collect(Collectors.toList());
				employeeAsPrettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(companyList);
				redisTemplate.opsForValue().set("companyList", employeeAsPrettyString);

				value = (String) redisTemplate.opsForValue().get("companyList");
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return value;
	}

	@Transactional
	@Override
	public void saveAll() {

		ObjectMapper mapper = new ObjectMapper();

		String employeeAsPrettyString;
		try {
			List<CompanyResult> companyList = companyInfoRepository.findAllByOrderByNameAsc()
																   .stream().map(obj -> new CompanyResult(obj))
																   .collect(Collectors.toList());
			employeeAsPrettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(companyList);
			redisTemplate.opsForValue().set("companyList", employeeAsPrettyString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectCompanyModel> selectApiCompanyHeader(String roleType) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();

		query.append("SELECT ci.type, ");
		query.append("CASE ci.role ");
		query.append("WHEN 'admin' THEN '관리자' ");
		query.append("WHEN 'publish' THEN '발행업체' ");
		query.append("WHEN 'production' THEN '생산업체' ");
		query.append("WHEN 'distribution' THEN '물류센터' ");
		query.append("WHEN 'sales' THEN '매장' ");
		query.append("WHEN 'special' THEN '백화점' ");
		query.append("WHEN 'publishAdmin' THEN '발행관리자' ");
		query.append("WHEN 'superAdmin' THEN '슈퍼관리자' ");
		query.append("END AS name, ");
		query.append("'0' AS company_seq ");
		query.append("FROM company_info ci ");

		if(roleType.equals("store")){
			query.append("WHERE ci.type IN ('5', '6') ");
		}

		query.append("GROUP BY ci.type, ci.role ");
		query.append("ORDER BY ci.role ASC ");

		return nameTemplate.query(query.toString(), params, new SelectCompanyModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectCompanyModel> selectApiCompanyDetail(String type) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();

		query.append("SELECT ci.type, ");
		query.append("ci.name, ");
		query.append("ci.company_seq ");
		query.append("FROM company_info ci ");

		query.append("WHERE ci.type = :type ");
		params.put("type", type);

		query.append("GROUP BY ci.type, ci.name, ci.company_seq ");
		query.append("ORDER BY ci.name ASC ");

		return nameTemplate.query(query.toString(), params, new SelectCompanyModelMapper());

	}

}
