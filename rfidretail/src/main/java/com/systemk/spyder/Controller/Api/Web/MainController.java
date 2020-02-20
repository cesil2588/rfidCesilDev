package com.systemk.spyder.Controller.Api.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.systemk.spyder.Util.CalendarUtil;

@Controller
public class MainController {
 
	@RequestMapping("/")
    public String index(Model model) {
		
		model.addAttribute("version", CalendarUtil.convertFormat("yyyyMMdd"));
        return "index"; 
    }
	
	@RequestMapping("/main/home")
    public String home(Model model) {
		
        return "main/home"; 
    }
	
	@RequestMapping("/main/searchPop")
    public String searchPop(Model model) {
		
        return "main/searchPop"; 
    }
	
	@RequestMapping("/main/searchDetailPop")
    public String searchDetailPop(Model model) {
		
        return "main/searchDetailPop"; 
    }
	
	@RequestMapping("/member/login")
    public String login(Model model) {
		
        return "member/login"; 
    }
	
	@RequestMapping("/member/join")
    public String join(Model model) {
		
        return "member/join"; 
    }
	
	@RequestMapping("/member/userIdFind")
    public String userIdFind(Model model) {
		
        return "member/userIdFind"; 
    }
	
	@RequestMapping("/member/passwordFind")
    public String passwordFind(Model model) {
		
        return "member/passwordFind"; 
    }
	
	@RequestMapping("/member/userAddPop")
    public String userAddPop(Model model) {
		
        return "member/userAddPop"; 
    }
	
	@RequestMapping("/member/userList")
    public String userList(Model model) {
		
        return "member/userList"; 
    }
	
	@RequestMapping("/member/userDetailPop")
    public String userDetailPop(Model model) {
		
        return "member/userDetailPop"; 
    }
	
	@RequestMapping("/member/userNotiList")
    public String userNotiList(Model model) {
		
        return "member/userNotiList"; 
    }
	
	@RequestMapping("/company/companyAddPop")
    public String companyAddPop(Model model) {
        return "company/companyAddPop"; 
    }
	
	@RequestMapping("/company/companyDetailPop")
    public String companyDetailPop(Model model) {
        return "company/companyDetailPop"; 
    }
	
	@RequestMapping("/company/companyList")
    public String companyList(Model model) {
		
        return "company/companyList"; 
    }
	
	@RequestMapping("/code/codeDetail")
    public String codeDetail(Model model) {
		
        return "code/codeDetail"; 
    }
	
	@RequestMapping("/mail/mailSend")
	public String mailSend() {
		return "mail/mailSend";
	}
	
	@RequestMapping("/soap/soapSend")
	public String soapSend() {
		return "soap/soapSend";
	}
	
	@RequestMapping("/excel/excelDownload")
	public String download() {
		return "download/excelDownload";
	}
	
	@RequestMapping("/bartag/bartagGroupList")
	public String bartagGroupList() {
		return "bartag/bartagGroupList";
	}
	
	@RequestMapping("/bartag/bartagList")
	public String bartagList() {
		return "bartag/bartagList";
	}
	
	@RequestMapping("/bartag/bartagDetailPop")
	public String bartagDetailPop() {
		return "bartag/bartagDetailPop";
	}
	
	@RequestMapping("/bartag/bartagOrderGroupList")
	public String bartagOrderGroupList() {
		return "bartag/bartagOrderGroupList";
	}
	
	@RequestMapping("/bartag/bartagOrderList")
	public String bartagOrderList() {
		return "bartag/bartagOrderList";
	}
	
	@RequestMapping("/bartag/bartagOrderDetailPop")
	public String bartagOrderDetailPop() {
		return "bartag/bartagOrderDetailPop";
	}
	
	//태그 이력 조회
	@RequestMapping("/bartag/bartagHistory")
	public String bartagHistory() {
		return "bartag/bartagHistory";
	}
	
	@RequestMapping("/production/productionList")
	public String productionList() {
		return "production/productionList";
	}
	
	@RequestMapping("/production/productionDetailPop")
	public String productionDetailPop() {
		return "production/productionDetailPop";
	}
	
	@RequestMapping("/production/productionReleaseList")
	public String productionReleaseList() {
		return "production/productionReleaseList";
	}
	
	@RequestMapping("/production/productionReleaseGroupList")
	public String productionReleaseGroupList() {
		return "production/productionReleaseGroupList";
	}
	
	@RequestMapping("/production/productionReleaseDetailPop")
	public String productionReleaseDetailPop() {
		return "production/productionReleaseDetailPop";
	}
	
	@RequestMapping("/production/productionScheduleList")
	public String productionScheduleList() {
		return "production/productionScheduleList";
	}
	
	@RequestMapping("/production/productionScheduleDetailPop")
	public String productionScheduleDetailPop() {
		return "production/productionScheduleDetailPop";
	}
	
	@RequestMapping("/batchJob/batchJobDetail")
	public String batchJobDetail() {
		return "batchJob/batchJobDetail";
	}
	
	@RequestMapping("/batchLog/batchLogList")
	public String batchLogList() {
		return "batchLog/batchLogList";
	}
	
	@RequestMapping("/batchLog/batchLogDetailPop")
	public String batchLogDetailPop() {
		return "batchLog/batchLogDetailPop";
	}
	
	@RequestMapping("/batchLog/batchLogDetailPopTooltip")
	public String batchLogDetailPopTooltip() {
		return "batchLog/batchLogDetailPopTooltip";
	}
	
	@RequestMapping("/mailLog/mailLogList")
	public String mailLogList() {
		return "mailLog/mailLogList";
	}
	
	@RequestMapping("/mailLog/mailLogDetailPop")
	public String mailLogDetailPop() {
		return "mailLog/mailLogDetailPop";
	}
	
	@RequestMapping("/errorLog/errorLogList")
	public String errorLogList() {
		return "errorLog/errorLogList";
	}
	
	@RequestMapping("/errorLog/errorLogDetailPop")
	public String errorLogDetailPop() {
		return "errorLog/errorLogDetailPop";
	}
	
	@RequestMapping("/requestLog/requestLogList")
	public String requestLogList() {
		return "requestLog/requestLogList";
	}
	
	@RequestMapping("/requestLog/requestLogDetailPop")
	public String requestLogDetailPop() {
		return "requestLog/requestLogDetailPop";
	}
	
	@RequestMapping("/box/boxWorkGroupList")
	public String boxWorkGroupList() {
		return "box/boxWorkGroupList";
	}
	
	@RequestMapping("/box/boxList")
	public String boxList() {
		return "box/boxList";
	}
	
	@RequestMapping("/box/boxDetailPop")
	public String boxDetailPop() {
		return "box/boxDetailPop";
	}
	
	@RequestMapping("/box/boxAddPop")
	public String boxAddPop() {
		return "box/boxAddPop";
	} 
	
	@RequestMapping("/box/boxPdfDownload")
	public String boxPdfDownload() {
		return "box/boxPdfDownload";
	}
	
	@RequestMapping("/customTemplate/customCompany")
	public String customCompany() {
		return "customTemplate/customCompany";
	}
	
	@RequestMapping("/productMaster/productList")
	public String productList() {
		return "productMaster/productList";
	}
	
	@RequestMapping("/productMaster/productDetailPop")
	public String productDetailPop() {
		return "productMaster/productDetailPop";
	}
	
	@RequestMapping("/customTemplate/customRfidTag")
	public String customRfidTag() {
		return "customTemplate/customRfidTag";
	}
	
	@RequestMapping("/customTemplate/customBoxMapping")
	public String customBoxMapping() {
		return "customTemplate/customBoxMapping";
	}
	
	@RequestMapping("/customTemplate/customFileUpload")
	public String customFileUpload() {
		return "customTemplate/customFileUpload";
	}
	
	@RequestMapping("/customTemplate/customFileUploadReissue")
	public String customFileUploadReissue() {
		return "customTemplate/customFileUploadReissue";
	}
	
	@RequestMapping("/customTemplate/customBoxMappingTag")
	public String customBoxMappingTag() {
		return "customTemplate/customBoxMappingTag";
	}
	
	@RequestMapping("/customTemplate/customReleaseGroupConfirm")
	public String customReleaseGroupConfirm() {
		return "customTemplate/customReleaseGroupConfirm";
	}
	
	@RequestMapping("/customTemplate/customReleaseConfirm")
	public String customReleaseConfirm() {
		return "customTemplate/customReleaseConfirm";
	}
	
	@RequestMapping("/customTemplate/customReturnConfirm")
	public String customReturnConfirm() {
		return "customTemplate/customReturnConfirm";
	}
	
	@RequestMapping("/customTemplate/customReissueTag")
	public String customReissueTag() {
		return "customTemplate/customReissueTag";
	}
	
	@RequestMapping("/customTemplate/customBartagData")
	public String customBartagData() {
		return "customTemplate/customBartagData";
	}
	
	@RequestMapping("/customTemplate/customBartagSeasonData")
	public String customBartagSeasonData() {
		return "customTemplate/customBartagSeasonData";
	}
	
	@RequestMapping("/customTemplate/customBartagOrderComplete")
	public String customBartagOrderComplete() {
		return "customTemplate/customBartagOrderComplete";
	}
	
	@RequestMapping("/customTemplate/customBartagOrderAddition")
	public String customBartagOrderAddition() {
		return "customTemplate/customBartagOrderAddition";
	}
	
	@RequestMapping("/customTemplate/customBartagUpdate")
	public String customBartagUpdate() {
		return "customTemplate/customBartagUpdate";
	}
	
	@RequestMapping("/customTemplate/customStoreReleaseInit")
	public String customStoreReleaseInit() {
		return "customTemplate/customStoreReleaseInit";
	}
	
	@RequestMapping("/customTemplate/customWmsReleaseMapping")
	public String customWmsReleaseMapping() {
		return "customTemplate/customWmsReleaseMapping";
	}
	
	@RequestMapping("/customTemplate/customWmsTboxPick")
	public String customWmsTboxPick() {
		return "customTemplate/customWmsTboxPick";
	}
	
	@RequestMapping("/customTemplate/customReissueTagPrint")
	public String customReissueTagPrint() {
		return "customTemplate/customReissueTagPrint";
	}
	
	@RequestMapping("/distribution/distributionList")
	public String distributionList() {
		return "distribution/distributionList";
	}
	
	@RequestMapping("/distribution/distributionDetailPop")
	public String distributionDetailPop() {
		return "distribution/distributionDetailPop";
	}
	
	@RequestMapping("/distribution/storageScheduleGroupList")
	public String storageScheduleGroupList() {
		return "distribution/storageScheduleGroupList";
	}
	
	@RequestMapping("/distribution/storageScheduleList")
	public String storageScheduleList() {
		return "distribution/storageScheduleList";
	}
	
	@RequestMapping("/distribution/storageScheduleDetailPop")
	public String storageScheduleDetailPop() {
		return "distribution/storageScheduleDetailPop";
	}
	
	@RequestMapping("/distribution/storageReturnScheduleDetailPop")
	public String storageReturnScheduleDetailPop() {
		return "distribution/storageReturnScheduleDetailPop";
	}
	
	@RequestMapping("/distribution/storeReleaseScheduleGroupList")
	public String storeReleaseScheduleGroupList() {
		return "distribution/storeReleaseScheduleGroupList";
	}
	
	@RequestMapping("/distribution/storeReleaseScheduleList")
	public String storeReleaseScheduleList() {
		return "distribution/storeReleaseScheduleList";
	}
	
	@RequestMapping("/distribution/storeReleaseScheduleDetailPop")
	public String storeReleaseScheduleDetailPop() {
		return "distribution/storeReleaseScheduleDetailPop";
	}
	
	@RequestMapping("/distribution/storeReleaseGroupList")
	public String storeReleaseGroupList() {
		return "distribution/storeReleaseGroupList";
	}
	
	@RequestMapping("/distribution/storeReleaseList")
	public String storeReleaseList() {
		return "distribution/storeReleaseList";
	}
	
	@RequestMapping("/distribution/storeReleaseDetailPop")
	public String storeReleaseDetailPop() {
		return "distribution/storeReleaseDetailPop";
	}
	
	//반품입고예정정보
	@RequestMapping("/distribution/storageReturnScheduleGroupList")
	public String storageReturnScheduleGroupList() {
		return "distribution/storageReturnScheduleGroupList";
	}
	
	//반품입고예정정보 상세
	@RequestMapping("/distribution/storageReturnScheduleList")
	public String storageReturnScheduleList() {
		return "distribution/storageReturnScheduleList";
	}
	
	@RequestMapping("/store/storageScheduleGroupList")
	public String storeStorageScheduleGroupList() {
		return "store/storageScheduleGroupList";
	}
	
	@RequestMapping("/store/storageScheduleList")
	public String storeStorageScheduleList() {
		return "store/storageScheduleList";
	}
	
	@RequestMapping("/store/storageScheduleDetailPop")
	public String storeStorageScheduleDetailPop() {
		return "store/storageScheduleDetailPop";
	}
	
	@RequestMapping("/store/storeList")
	public String storeList() {
		return "store/storeList";
	}
	
	@RequestMapping("/store/storeDetailPop")
	public String storeDetailPop() {
		return "store/storeDetailPop";
	}
	
	@RequestMapping("/store/storeReturnGroupList")
	public String storeReturnGroupList() {
		return "store/storeReturnGroupList";
	}
	
	@RequestMapping("/store/storeReturnList")
	public String storeReturnList() {
		return "store/storeReturnList";
	}
	
	@RequestMapping("/store/storeReturnDetailPop")
	public String storeReturnDetailPop() {
		return "store/storeReturnDetailPop";
	}
	
	@RequestMapping("/store/storeMoveGroupList")
	public String storeMoveGroupList() {
		return "store/storeMoveGroupList";
	}
	
	@RequestMapping("/store/storeMoveList")
	public String storeMoveList() {
		return "store/storeMoveList";
	}
	
	@RequestMapping("/store/storeMoveDetailPop")
	public String storeMoveDetailPop() {
		return "store/storeMoveDetailPop";
	}
	
	@RequestMapping("/reissueTag/reissueTagGroupList")
	public String reissueTagGroupList() {
		return "reissueTag/reissueTagGroupList";
	}
	
	@RequestMapping("/reissueTag/reissueTagList")
	public String reissueTagList() {
		return "reissueTag/reissueTagList";
	}
	
	@RequestMapping("/reissueTag/reissueTagDetailPop")
	public String reissueTagDetailPop() {
		return "reissueTag/reissueTagDetailPop";
	}
	
	@RequestMapping("/app/appList")
	public String appList() {
		return "app/appList";
	}
	
	@RequestMapping("/app/appAddPop")
	public String appAddPop() {
		return "app/appAddPop";
	}
	
	@RequestMapping("/app/appDetailPop")
	public String appDetailPop() {
		return "app/appDetailPop";
	}
	
	@RequestMapping("/app/mobileDownload")
	public String mobileDownload() {
		return "app/mobileDownload";
	}
	
	@RequestMapping("/batchTrigger/batchTriggerList")
	public String batchTriggerList() {
		return "batchTrigger/batchTriggerList";
	}
	
	@RequestMapping("/batchTrigger/batchTriggerDetailPop")
	public String batchTriggerDetailPop() {
		return "batchTrigger/batchTriggerDetailPop";
	}
	
	@RequestMapping("/batchTrigger/batchTriggerDetailPopTooltip")
	public String batchTriggerDetailPopTooltip() {
		return "batchTrigger/batchTriggerDetailPopTooltip";
	}
	
	@RequestMapping("/inventorySchedule/inventoryScheduleGroupList")
	public String inventoryScheduleGroupList() {
		return "inventorySchedule/inventoryScheduleGroupList";
	}
	
	@RequestMapping("/inventorySchedule/inventoryScheduleList")
	public String inventoryScheduleList() {
		return "inventorySchedule/inventoryScheduleList";
	}
	
	@RequestMapping("/inventorySchedule/inventoryScheduleDetailPop")
	public String inventoryScheduleDetailPop() {
		return "inventorySchedule/inventoryScheduleDetailPop";
	}
	
	@RequestMapping("/error/error")
	public String error() {
		return "error/error";
	}
	
	@RequestMapping("/customTemplate/customModal")
	public String customModal() {
		return "customTemplate/customModal";
	}
	
	@RequestMapping("/customTemplate/customConfirm")
	public String customConfirm() {
		return "customTemplate/customConfirm";
	}
	
	@RequestMapping("/role/roleList")
	public String roleList() {
		return "role/roleList";
	}
	
	@RequestMapping("/role/roleDetailPop")
	public String roleDetailPop() {
		return "role/roleDetailPop";
	}
	
	@RequestMapping("/role/roleAddPop")
	public String roleAddPop() {
		return "role/roleAddPop";
	}
	
	@RequestMapping("/menu/menuDetail")
	public String menuDetail() {
		return "menu/menuDetail";
	}
	

}
