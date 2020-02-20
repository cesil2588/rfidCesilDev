package com.systemk.spyder.Service.Impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Entity.Main.ParentCodeInfo;
import com.systemk.spyder.Service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOperations;

	@Resource(name = "redisTemplate")
	private ListOperations<String, ParentCodeInfo> listOperations;

	@Resource(name = "redisTemplate")
	private HashOperations<String, Long, String> hashOperations;
	
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashStringOperations;

	@Resource(name = "redisTemplate")
	private SetOperations<String, String> setOperations;

	@Resource(name = "redisTemplate")
	private ZSetOperations<String, String> zSetOperations;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public String getRedisTemplateValue(String name) throws Exception {
		return (String)redisTemplate.opsForValue().get(name);
	}


	/*
	@Autowired
	private ParentCodeInfoRepository parentCodeInfoRepository;
	*/
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}	

	@Override
	public Map<Long, String> findAll(String name) throws Exception{
		return hashOperations.entries(name);
	}
	
	@Override
	public String find(String key, Long seq) throws Exception{
		return hashOperations.get(key, seq);
	}
	
	@Override
	public void delete(String key, Long seq) throws Exception{
		hashOperations.delete(key, seq);
	}
	
	@Override
	public void save(String key, Long seq, String value) throws Exception{
		hashOperations.put(key, seq, value);
	}

	@Override
	public String find(String key, String seq) throws Exception {
		return hashStringOperations.get(key, seq);
	}
	
	@Override
	public void save(String key, String seq, String value) throws Exception {
		hashStringOperations.put(key, seq, value);
	}
	
	/*
	@Override
	public String getTestRedis(String name) throws Exception {
		return valueOperations.get(name);
	}

	@Override
	public void setTestRedis(String name, String value) throws Exception {
		valueOperations.set(name, value);
	}

	@Override
	public String getRedisTemplateValue(String name) throws Exception {
		return (String)redisTemplate.opsForValue().get(name);
	}

	@Override
	public boolean setCodeList() {

		boolean success = true;

		ObjectMapper mapper = new ObjectMapper();

		String employeeAsPrettyString;
		try {
			List<ParentCodeInfo> codeList = parentCodeInfoRepository.findAllByOrderBySortAsc();
			employeeAsPrettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(codeList);
			redisTemplate.opsForValue().set("codeList", employeeAsPrettyString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String value = (String) redisTemplate.opsForValue().get("codeList");

		System.out.println("-------------------------------------------------------------------------");
		System.out.println(value);
		System.out.println("-------------------------------------------------------------------------");

		return success;
	}

	@Override
	public void setHashCodeList() {

		for (ParentCodeInfo parendCode : parentCodeInfoRepository.findAllByOrderBySortAsc()) {
			hashOperations.put("codeList.parentCode", parendCode.getParentCodeSeq(), parendCode);
		}

		Map<Long, ParentCodeInfo> codeList = hashOperations.entries("codeList.parentCode");

		System.out.println("-------------------------------------------------------------------------");
		for (ParentCodeInfo parentCode : codeList.values()) {
			System.out.println(parentCode.getCodeValue());
		}
		System.out.println("-------------------------------------------------------------------------");

	}
	
	@Override
	public boolean setCompanyList() {

		boolean success = true;

		ObjectMapper mapper = new ObjectMapper();

		String employeeAsPrettyString;
		try {
			List<CompanyInfo> companyList = companyInfoRepository.findAllByOrderByNameAsc();
			employeeAsPrettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(companyList);
			redisTemplate.opsForValue().set("companyList", employeeAsPrettyString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String value = (String) redisTemplate.opsForValue().get("companyList");

		System.out.println("-------------------------------------------------------------------------");
		System.out.println(value);
		System.out.println("-------------------------------------------------------------------------");

		return success;
	}
	 */
	
}
