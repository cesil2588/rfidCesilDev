package com.systemk.spyder.Controller.Api.Web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.BatchJob;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BatchJobRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BatchJobService;
import com.systemk.spyder.Service.SchedulerService;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/batchJob")
public class BatchJobController {
	
	@Autowired
	private BatchJobRepository batchJobRepository;
	
	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private BatchJobService batchJobService;

	@RequestMapping(method = RequestMethod.GET)
	public List<BatchJob> findAll() throws Exception {
		return batchJobRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BatchJob> save(@RequestBody BatchJob batchJob) throws Exception {
		
		LoginUser user = SecurityUtil.getCustomUser();
		if(user != null){
			UserInfo userInfo = new UserInfo();
			userInfo.setUserSeq(user.getUserSeq());
			batchJob.setRegUserInfo(userInfo);
		}
		batchJob.setRegDate(new Date());
		BatchJob returnBatchJob = batchJobRepository.save(batchJob);
		
		if(returnBatchJob.getUseYn().equals("Y")){
			schedulerService.addScheduler(returnBatchJob);
		}
        return new ResponseEntity<BatchJob>(returnBatchJob, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<BatchJob> update(@RequestBody BatchJob batchJob) throws Exception {
		
		LoginUser user = SecurityUtil.getCustomUser();
		if(user != null){
			UserInfo userInfo = new UserInfo();
			userInfo.setUserSeq(user.getUserSeq());
			batchJob.setUpdUserInfo(userInfo);
		}
		batchJob.setUpdDate(new Date());
		BatchJob returnBatchJob = batchJobRepository.save(batchJob);
		
		if(returnBatchJob.getUseYn().equals("Y")){
			schedulerService.updateScheduler(returnBatchJob);
		} else {
			schedulerService.delScheduler(returnBatchJob);
		}
        return new ResponseEntity<BatchJob>(returnBatchJob, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<BatchJob> delete(@RequestBody BatchJob batchJob) throws Exception {
		
		batchJobRepository.delete(batchJob);
		schedulerService.delScheduler(batchJob);
		
        return new ResponseEntity<BatchJob>(batchJob, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST, value="/action")
    public ResponseEntity<BatchJob> action(@RequestBody BatchJob batchJob) throws Exception {
		
		batchJobService.jobAction(batchJob.getCommand());
		
        return new ResponseEntity<BatchJob>(batchJob, HttpStatus.OK);
    }
}
