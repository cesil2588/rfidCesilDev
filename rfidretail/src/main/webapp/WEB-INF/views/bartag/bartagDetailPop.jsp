<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">RFID 생산 스타일 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<h2><a href="" ng-click="reload()">바택정보상세</a></h2>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr>
						<th>발행일자</th>
						<th>일련번호</th>
						<th>라인번호</th>
						<th>연도</th>
						<th>시즌</th>
						<th>ERP키</th>
						<th>스타일</th>
						<th>컬러</th>
						<th>사이즈</th>
						<th>오더차수</th>
						<th>발행수량</th>
						<th>생산업체</th>
						<th>생산공장</th>
						<th>상태</th>
						<th>발행시작</th>
						<th>발행종료</th>
						<th>등록일</th>
						<th style="width: 100px;">재발행<br>요청여부</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>{{data.createDate}}</td>
						<td>{{data.seq}}</td>
						<td>{{data.lineSeq}}</td>
						<td>{{data.productYy}}</td>
						<td>{{data.productSeason}}</td>
						<td>{{data.erpKey}}</td>
						<td>{{data.style}}</td>
						<td>{{data.color}}</td>
						<td>{{data.size}}</td>
						<td>{{data.orderDegree}}</td>
						<td>{{data.amount | number:0}}</td>
						<td><a href="" data-toggle="modal" data-target="#companyModal" ng-click="clickListener(data.productionCompanyInfo.customerCode)"><span>{{data.productionCompanyInfo.name}}</span></a></td>
						<td>{{data.detailProductionCompanyName ? data.detailProductionCompanyName : '-'}}</td>
						<td ng-switch="data.stat">
							<span ng-switch-when="1">초기 생성</span>
							<span ng-switch-when="2">발행 준비</span>
							<span ng-switch-when="3">발행 시작</span>
							<span ng-switch-when="4">발행 중</span>
							<span ng-switch-when="5">발행 종료</span>
							<span ng-switch-when="6">종결 처리</span>
							<span ng-switch-when="7">폐기</span>
						</td>
						<td>{{data.bartagStartDate | date:"yyyy-MM-dd"}}<br />
						    {{data.bartagStartDate | date:"HH:mm:ss"}}</td>
						<td>{{data.bartagEndDate | date:"yyyy-MM-dd"}}<br />
							{{data.bartagEndDate | date:"HH:mm:ss"}}</td>
						<td>
							{{data.regDate | date:"yyyy-MM-dd"}}<br/>
							{{data.regDate | date:"HH:mm:ss"}}
						</td>	
						<td>{{data.reissueRequestYn}}</td>
					</tr>
				</tbody>
			</table>
			<div class="form-group" ng-if="userRole != 'production' || (userRole == 'production' && data.stat > 4)">
				<h5>태그 상태별 수량</h5>
				<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr>
						<th>전체</th>
						<th>미발행</th>
						<th>발행대기</th>
						<th>발행완료</th>
						<th>재발행대기</th>
						<th>재발행완료</th>
						<th>재발행요청</th>
						<th>폐기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><a href="" ng-click="tagStat('all')">{{data.totalAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('1')">{{data.stat1Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('2')">{{data.stat2Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('3')">{{data.stat3Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('4')">{{data.stat4Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('5')">{{data.stat5Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('6')">{{data.stat6Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('7')">{{data.stat7Amount | number:0}}</a></td>
					</tr>
				</tbody>
				</table>
			</div>
			<div class="form-group">
				<h5 ng-show="userRole != 'production'">태그 발행 목록<span ng-show="data.reissueRequestYn == 'Y'" class="btn btn-primary pull-right"><i class="xi-upload" aria-hidden="true"></i><input style="width: 140px;" type="file" class="input" id="myfile" file-model="uploadedFile" accept=".txt" flag="reissue">재발행결과 업로드</input></span><span ng-show="data.bartagStartDate && !data.bartagEndDate" class="btn btn-primary pull-right"><i class="xi-upload" aria-hidden="true"></i><input style="width: 150px;" type="file" class="input" id="myfile" file-model="uploadedFile" accept=".txt" flag="publish">태그발행결과 업로드</input></span><button ng-click="goExcelDownload()" class="btn btn-primary pull-right" type="button" style="margin-right:4px;"><i class="xi-download" aria-hidden="true"></i>엑셀 다운로드</button><button ng-click="sample()" class="btn btn-primary pull-right" style="margin-right: 4px;"><i class="xi-download" aria-hidden="true"></i>업로드양식 다운로드</button></h5>
				<h5 ng-show="userRole == 'production' && data.stat > 4">태그 발행 목록</h5>
			</div>
			<div class="form-group" ng-if="userRole != 'production' || (userRole == 'production' && data.stat > 4)">
				<!-- <div class="card card-body" style="overflow:scroll; height:400px;"> -->
				<div class="card card-body">
					<table class="table table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr class="pointer">
							<th ng-click="sort('rfidTag')">RFID태그</th>
							<th ng-click="sort('erpKey')">ERP키</th>
							<th ng-click="sort('season')">시즌</th>
							<th ng-click="sort('orderDegree')">오더차수</th>
							<th ng-click="sort('customerCd')">생산업체코드</th>
							<th ng-click="sort('publishLocation')">발행장소</th>
							<th ng-click="sort('publishRegDate')">발행날짜</th>
							<th ng-click="sort('publishDegree')">발행차수</th>
							<th ng-click="sort('rfidSeq')">일련번호</th>
							<th ng-click="sort('stat')">상태</th>
							<th ng-click="sort('regDate')">등록일</th>
							<th ng-click="sort('updDate')">수정일</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="rfid in list" ng-if="list.length > 0" ng-click="openRfidTagDetail(rfid)" class="pointer">
							<td>{{rfid.rfidTag ? rfid.rfidTag : '-'}}</td>
							<td>{{rfid.erpKey}}</td>
							<td>{{rfid.season}}</td>
							<td>{{rfid.orderDegree}}</td>
							<td>{{rfid.customerCd}}</td>
							<td>{{rfid.publishLocation}}</td>
							<td>{{rfid.publishRegDate ? rfid.publishRegDate : '-'}}</td>
							<td>{{rfid.publishDegree ? rfid.publishDegree : '-'}}</td>
							<td>{{rfid.rfidSeq}}</td>
							<td ng-switch="rfid.stat">
								<span ng-switch-when="1">미발행</span>
								<span ng-switch-when="2">발행대기</span>
								<span ng-switch-when="3">발행완료</span>
								<span ng-switch-when="4">재발행대기</span>
								<span ng-switch-when="5">재발행</span>
								<span ng-switch-when="6">재발행요청</span>
								<span ng-switch-when="7">폐기</span>
							</td>
							<td>
								{{rfid.regDate | date:"yyyy-MM-dd"}}<br />
						    	{{rfid.regDate | date:"HH:mm:ss"}}
						    </td>
							<td>{{rfid.updDate | date:"yyyy-MM-dd"}}<br />
						    	{{rfid.updDate | date:"HH:mm:ss"}}
						    </td>
						</tr>
						<tr ng-if="list.length == 0">
							<td colspan="12" class="text-center">
								No Data
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div ng-if="userRole != 'production' || (userRole == 'production' && data.stat > 4)">
				<nav class="text-center">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(begin, statValue, statNum)" class="page-link"><span aria-hidden="true">&laquo;</span></a>
				    	</li>
				    	<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(current - 1, statValue, statNum)" class="page-link"><span aria-hidden="true">&lt;</span></a>
				    	</li>
				    	<li ng-repeat="pageNum in [begin, end] | makeRange" ng-class="{'active' : current == pageNum}" class="page-item"><a href="" ng-click="goPage(pageNum, statValue, statNum)" class="page-link">{{pageNum}}</a></li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(current + 1, statValue, statNum)" class="page-link"><span aria-hidden="true">&gt;</span></a>
				    	</li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(end, statValue, statNum)" class="page-link"><span aria-hidden="true">&raquo;</span></a>
				    	</li>
				  	</ul>
				</nav>
			</div>
			<div class="search-margin" ng-if="userRole != 'production' || (userRole == 'production' && data.stat > 4)">
				<div class="d-flex justify-content-center">
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:100px;">
								<option value="publishLocation" ng-selected="true" value="">시즌발행장소</option>
								<option value="publishRegDate">발행날짜</option>
								<option value="publishDegree">발행차수</option>
								<option value="rfidSeq">일련번호</option>
							</select>&nbsp;
							<input type="text" class="form-control" placeholder="검색" ng-model="search.text" style="width:300px;" id="inputSearch">&nbsp;
							<button type="submit" class="btn btn-default">검색</button>
						</div>
					</form>
				</div>
			</div>
			<!-- 
			<div class="form-group margin-top-4" ng-show="rfidTagReissueRequestList.length > 0 " ng-if="userRole != 'production' && testMode">
				<h5>태그 재발행 요청 목록</h5>
				<div class="card card-body">
				<table class="table table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr>
							<th>재발행 요청 태그</th>
							<th>요청 업체정보</th>
							<th>재발행 완료여부</th>
							<th>등록자</th>
							<th>등록일</th>
							<th>수정자</th>
							<th>수정일</th>
						</tr>
						</thead>
						<tbody>
						<tr ng-repeat="reissue in rfidTagReissueRequestList">
							<td>{{reissue.rfidTag}}</td>
							<td>{{reissue.productionStorage.companyInfo.name}}</td>
							<td>{{reissue.stat == '1' ? 'N' : 'Y'}}</td>
							<td>{{reissue.regUserInfo.userId}}</td>
							<td>{{reissue.regDate | date:"yyyy-MM-dd"}}<br />
								{{reissue.regDate | date:"HH:mm:ss"}}</td>
							<td>{{reissue.updUserInfo.userId}}</td>
							<td>{{reissue.updDate | date:"yyyy-MM-dd"}}<br />
							    {{reissue.updDate | date:"HH:mm:ss"}}<br /></td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			-->
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button ng-show="data.bartagStartDate && !data.bartagEndDate" ng-click="finalConfirm()" class="btn btn-primary">최종확인</button>
		<button ng-show="data.reissueRequestYn == 'Y' && data.stat4Amount || data.stat6Amount" ng-click="refinalConfirm()" class="btn btn-primary">재발행 최종확인</button>
    </div>


<!-- Modal -->
<div class="modal fade bd-example-modal-lg" id="companyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
      	<h4 class="modal-title" id="myModalLabel">업체정보</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
      </div>
      <div class="modal-body">
			<div class="card" style="overflow:scroll; height:400px;">
				<table class="table table-bordered table-hover">
					<tr><th style="width: 150px;">업체명</th><td>{{company.name}}</td></tr>
					<tr><th style="width: 150px;">업체코드</th><td>{{company.code}}</td></tr>
					<tr><th style="width: 150px;">ERP 코드,코너</th><td>{{company.customerCode}}, {{company.cornerName}}</td></tr>
					<tr><th style="width: 150px;">폐점여부</th><td>{{company.closeYn}}</td></tr>
					<tr><th style="width: 150px;">업체구분</th><td>{{company.type}}</td></tr>
					<tr><th style="width: 150px;">주소1</th><td>{{company.address1}}</td></tr>
					<tr><th style="width: 150px;">주소2</th><td>{{company.address2}}</td></tr>
					<tr><th style="width: 150px;">연락처</th><td>{{company.telNo}}</td></tr>
			</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

</html>