package com.systemk.spyder.Service.Impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Response.ProductMasterResult;
import com.systemk.spyder.Entity.Main.AppInfo;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.Specification.ProductMasterSpecification;
import com.systemk.spyder.Service.AppService;
import com.systemk.spyder.Service.ProductMasterService;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;
import com.systemk.spyder.Service.Mapper.Select.SelectGroupByMapper;
import com.systemk.spyder.Util.ResultUtil;

@Service
public class ProductMasterServiceImpl implements ProductMasterService{

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@Autowired
	private AppService appService;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
    private Environment env;

	@Transactional(readOnly = true)
	@Override
	public Page<ProductMaster> findAll(String productYy, String productSeason, String style, String search, String option, Pageable pageable) throws Exception {

		Page<ProductMaster> page = null;

		Specifications<ProductMaster> specifications = Specifications.where(ProductMasterSpecification.productYyEqual(productYy));

		if(!productSeason.equals("all")){
			specifications = specifications.and(ProductMasterSpecification.productSeasonEqual(productSeason));
		}

		if(!style.equals("all")){
			specifications = specifications.and(ProductMasterSpecification.styleEqual(style));
		}

		if(!search.equals("") && option.equals("erpKey")){
			specifications = specifications.and(ProductMasterSpecification.erpKeyContain(search));
		} else if(!search.equals("") && option.equals("color")){
			specifications = specifications.and(ProductMasterSpecification.colorContain(search));
		} else if(!search.equals("") && option.equals("size")){
			specifications = specifications.and(ProductMasterSpecification.sizeContain(search));
		}

		page = productMasterRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectGroupBy> findSelectGroupBy() throws Exception{

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "SELECT style AS data, 'style' AS flag, '-' AS rank  FROM product_master GROUP BY style " +
					   "UNION " +
					   "SELECT product_yy AS data, 'productYy' AS flag, '-' AS rank FROM product_master GROUP BY product_yy " +
					   "UNION " +
					   "SELECT size AS data, 'size' AS flag, '-' AS rank FROM product_master GROUP BY size " +
					   "UNION " +
					   "SELECT product_season AS data, 'season' AS flag, " +
					   "CASE product_season " +
					   "WHEN 'A' THEN '1' " +
					   "WHEN 'P' THEN '2' " +
					   "WHEN 'M' THEN '3' " +
					   "WHEN 'F' THEN '4' " +
					   "WHEN 'W' THEN '5' " +
					   "END AS rank " +
					   "FROM product_master GROUP BY product_season ORDER BY rank";

		return template.query(query, new SelectGroupByMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findByMasterList(String version, String appType) throws Exception {

		// version, appType 파리미터가 있을시 아래 로직 수행
		if(!version.equals("") && !appType.equals("")) {

			AppInfo appInfo = appService.currentRepresentApp(appType);

			// App 정보 확인
			if(appInfo == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_APP_MESSAGE);
			}

			// 버전이 맞지 않을 경우 ResultMessage에 URL을 넣어서 전달
			if(!version.equals(appInfo.getVersion())){
				return ResultUtil.setCommonResult("U", env.getProperty("version.download.url") + "storage/" + appInfo.getFileName());
			}
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, productMasterRepository.findAll()
																										 .stream()
																										 .map(ProductMasterResult::new)
																										 .collect(Collectors.toList()));
	}
}
