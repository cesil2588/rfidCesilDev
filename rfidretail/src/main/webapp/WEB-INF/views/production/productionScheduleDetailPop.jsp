<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">RFID 태그입고 검수 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr>
						<th>연도</th>
						<th>시즌</th>
						<th>스타일</th>
						<th>컬러</th>
						<th>사이즈</th>
						<th>오더차수</th>
						<th>추가발주</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>{{data.bartagMaster.productYy}}</td>
						<td>{{data.bartagMaster.productSeason}}</td>
						<td>{{data.bartagMaster.style}}</td>
						<td>{{data.bartagMaster.color}}</td>
						<td>{{data.bartagMaster.size}}</td>
						<td>{{data.bartagMaster.orderDegree}}</td>
						<td>{{data.bartagMaster.additionOrderDegree}}</td>
					</tr>
				</tbody>
			</table>
			<h5>생산업체 태그 상태별 수량 정보</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr>
						<th>총계</th>
						<th>발행미검수</th>
						<th>입고</th>
						<th>출고</th>
						<th>재발행요청</th>
						<th>폐기</th>
						<th>반품미검수</th>
						<th>반품</th>
					</tr>
				</thead>
				<tbody>	
					<tr>
						<td><a href="" ng-click="tagStat('all')">{{data.totalAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('1')">{{data.nonCheckAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('2')">{{data.stockAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('3')">{{data.releaseAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('4')">{{data.reissueAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('5')">{{data.disuseAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('6')">{{data.returnNonCheckAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('7')">{{data.returnAmount | number:0}}</a></td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" ng-model="statValue"/>
			<input type="hidden" ng-model="statNum"/>
			<h5 ng-if="testMode">태그 목록</h5>
			<div class="form-group" ng-if="testMode">
				<!-- <div class="card card-body" style="overflow:scroll; height:400px;"> -->
				<div class="card card-body">
					<table class="table table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr class="pointer">
							<th style="width: 90px;">
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" ng-show="checkboxFlag" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" ng-show="checkboxFlag" for="allCheck">체크</label>
								<span ng-show="!checkboxFlag">체크</span>
							</div>
							</th>
							<th style="width: 180px;" ng-click="sort('barcode')">바코드</th>
							<th style="width: 300px;" ng-click="sort('rfidTag')">RFID태그</th>
							<th ng-click="sort('regUserInfo.userId')">박스바코드</th>
							<th style="width: 110px;" ng-click="sort('stat')">상태</th>
							<th ng-click="sort('regUserInfo.userId')">등록자</th>
							<th style="width: 180px;" ng-click="sort('regDate')">등록일</th>
							<th ng-click="sort('updUserInfo.userId')">수정자</th>
							<th ng-click="sort('updDate')">수정일</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="rfid in list" ng-if="list.length > 0">
							<td>
								<div ng-show="rfid.stat == '1' || rfid.stat == '2'" class="custom-control custom-checkbox">
									<!--  
							  		<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-click="checkBoxSelected(rfid)">
							  		<label class="custom-control-label" for="customCheck{{$index}}"></label>
							  		-->
							  		
							  		<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="rfid.check" ng-change="checkBoxSelected(rfid)">
							  		<label class="custom-control-label" for="customCheck{{$index}}"></label>
								</div>
							</td>
							<td ng-click="openRfidTagDetail(rfid)" class="pointer">{{rfid.barcode}}</td>
							<td ng-click="openRfidTagDetail(rfid)" class="pointer">{{rfid.rfidTag}}</td>
							<td>{{rfid.boxBarcode ? rfid.boxBarcode : '-'}}</td>
							<td ng-switch="rfid.stat">
								<span ng-switch-when="1">발행미검수</span>
								<span ng-switch-when="2">입고</span>
								<span ng-switch-when="3">출고</span>
								<span ng-switch-when="4">재발행요청</span>
								<span ng-switch-when="5">폐기</span>
								<span ng-switch-when="6">반품미검수</span>
								<span ng-switch-when="7">반품</span>
							</td>
							<td>{{rfid.regUserInfo.userId}}</td>
							<td>
								<span ng-if="rfid.regDate">
									{{rfid.regDate | date:"yyyy-MM-dd"}}<br />
									{{rfid.regDate | date:"HH:mm:ss"}}
								</span>
								<span ng-if="!rfid.regDate">
									-
								</span>
							</td>
							<td>{{rfid.updUserInfo.userId ? rfid.updUserInfo.userId : '-'}}</td>
							<td>
								<span ng-if="rfid.updDate">
									{{rfid.updDate | date:"yyyy-MM-dd"}}<br />
									{{rfid.updDate | date:"HH:mm:ss"}}
								</span>
								<span ng-if="!rfid.updDate">
									- 
								</span>
							</td>
						</tr>
						<tr ng-if="list.length == 0">
							<td colspan="9" class="text-center">
								No Data
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div ng-if="testMode">
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
			<!-- 
			<div class="search-margin" ng-if="testMode">
				<div class="d-flex justify-content-center">
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:120px;">
								<option ng-selected="true" value="barcode">바코드</option>
								<option value="rfidTag">RFID태그</option>
							</select>&nbsp;
							<input type="text" class="form-control" placeholder="검색" ng-model="search.text" style="width:300px;" id="inputSearch">&nbsp;
							<button type="submit" class="btn btn-default">검색</button>
						</div>
					</form>
				</div>
			</div>
			-->
			<!-- 
			<div class="pull-right">
				<back-btn back="뒤로 가기"></back-btn>
			</div>
			-->
			<div class="form-group margin-top-4" ng-show="rfidTagReissueRequestList.length > 0 ">
				<h5>태그 재발행 요청 목록</h5>
				<div class="card card-body">
				<table class="table table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr>
							<th>요청 업체정보</th>
							<th>재발급 요청 태그</th>
							<th>사유</th>
							<th>재발급 완료여부</th>
							<th>등록자</th>
							<th>등록일</th>
							<th>수정자</th>
							<th>수정일</th>
						</tr>
						</thead>
						<tbody>
						<tr ng-repeat="reissue in rfidTagReissueRequestList">
							<td>{{reissue.companyInfo.name}}</td>
							<td><p ng-repeat="reissueDetail in reissue.rfidTagReissueRequestDetail" style="margin-bottom:0rem;">{{reissueDetail.rfidTag}}</p></td>
							<td>{{reissue.explanatory}}</td>
							<td>{{reissue.reissueYn}}</td>
							<td>{{reissue.regUserInfo.userId}}</td>
							<td>{{reissue.regDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
							<td>{{reissue.updUserInfo.userId}}</td>
							<td>{{reissue.updDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<!-- <button ng-click="openBoxMapping()" ng-if="userRole == 'admin'" class="btn btn-lg btn-primary">박스맵핑</button> -->
		<button ng-click="inspection()" class="btn btn-primary">전체입고검수</button>
		<!-- <button ng-click="reBartag()" class="btn btn-lg btn-primary">재발행요청</button> -->
		<button class="btn btn-primary" ng-if="testMode" ng-click="openReissueModal()">재발행요청</button>
		<!-- <button ng-click="reBartagAll()" class="btn btn-lg btn-primary">전체재발행요청</button> -->
    </div>
    <div class="modal fade" id="reissueModal" tabindex="-1" role="dialog" aria-labelledby="reissueModalLabel" aria-hidden="true">
  	<div class="modal-dialog" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title" id="exampleModalLabel">태그 재발행 요청</h5>
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          			<span aria-hidden="true">&times;</span>
        		</button>
      		</div>
	      	<div class="modal-body">
	        	<form>
	          		<div class="form-group">
	            		<label for="message-text" class="col-form-label">태그 재발행 사유를 입력하세요.</label>
	            		<textarea class="form-control" id="message-text" ng-model="explanatory"></textarea>
	          		</div>
	        	</form>
	      	</div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
	        	<button type="button" class="btn btn-primary" ng-click="reBartag()">확인</button>
	      	</div>
    	</div>
  	</div>
</div>
</html>
