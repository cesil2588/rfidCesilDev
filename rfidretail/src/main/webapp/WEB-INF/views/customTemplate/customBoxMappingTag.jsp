<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">태그 맵핑</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<div class="form-row">
			<div class="form-group col-2">
				연도 :
				&nbsp;&nbsp;
				<input type="text" ng-model="search.productYy" placeholder="연도 입력" uib-typeahead="productYy as productYy.data for productYy in productYyList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productYy')">
			</div>
			<div class="form-group col-2">
				시즌 :
				&nbsp;&nbsp;
				<input type="text" ng-model="search.productSeason" placeholder="시즌 입력" uib-typeahead="productSeason as productSeason.data for productSeason in productSeasonList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productSeason')">
			</div>
			<div class="form-group col-2">
				스타일 :
				&nbsp;&nbsp;
				<input type="text" ng-model="search.style" placeholder="스타일 입력" uib-typeahead="style as style.data for style in styleList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'style')">
			</div>
			<div class="form-group col-2">
				컬러 :
				&nbsp;&nbsp;
				<input type="text" ng-model="search.color" placeholder="컬러 입력" uib-typeahead="color as color.data for color in colorList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'color')">	
			</div>
			<div class="form-group col-2">
				사이즈 :
				&nbsp;&nbsp;
				<input type="text" ng-model="search.styleSize" placeholder="사이즈 입력" uib-typeahead="size as size.data for size in sizeList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'styleSize')">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-2">
				오더차수 : 
				&nbsp;&nbsp;
				<input type="text" ng-model="search.orderDegree" placeholder="오더차수 입력" uib-typeahead="orderDegree as orderDegree.data for orderDegree in orderDegreeList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'orderDegree')">
			</div>
			<div class="form-group col-2">
				추가발주 : 
				&nbsp;&nbsp;
				<input type="text" ng-model="search.additionOrderDegree" placeholder="추가발주 입력" uib-typeahead="additionOrderDegree as additionOrderDegree.data for additionOrderDegree in additionOrderDegreeList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'additionOrderDegree')">
			</div>
			<div class="form-group col-2">
				일련번호 시작 :
				&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="일련번호 시작 입력" ng-model="search.startRfidSeq">
			</div>
			<div class="form-group col-2">
				일련번호 끝 :
				&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="일련번호 끝 입력" ng-model="search.endRfidSeq">
			</div>
			<div class="form-group col-2" ng-if="search.type == '03'">
				반품타입 :
				&nbsp;&nbsp;
				<select class="custom-select" ng-model="search.returnType">
					<!-- 
					<option value="10">일반반품</option>
					<option value="20">비용반품</option>
					<option value="40">불량반품</option>
					<option value="90">계절반품</option>
					-->
					<option value="15">이동반품</option>
					<option value="80">일반반품대기</option>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group">
				<button class="btn btn-primary" type="button" ng-click="headSearch()">조회</button>
				<button class="btn btn-primary" type="button" ng-click="init('all')">검색조건 초기화</button>
			</div>
		</div>
		<h5>검색된 태그 목록</h5>
		<div class="form-group">
			<div class="card card-body">
				<table class="table table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th style="width: 90px;">
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
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
					<tr ng-repeat="rfid in list track by $index" ng-if="list.length > 0">
						<td>
							<div ng-show="rfid.stat == '1' || rfid.stat == '2'" class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="rfid.check" ng-change="checkBoxSelected(rfid)">
							  	<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="openRfidTagDetail(rfid)" class="pointer">{{rfid.rfidTag}}</td>
						<td>{{rfid.boxBarcode ? rfid.boxBarcode : '-'}}</td>
						<td ng-switch="rfid.stat" ng-if="search.type == '01'">
							<span ng-switch-when="1">미검수</span>
							<span ng-switch-when="2">입고</span>
							<span ng-switch-when="3">출고</span>
							<span ng-switch-when="4">재발행요청</span>
							<span ng-switch-when="5">폐기</span>
							<span ng-switch-when="6">반품미검수</span>
							<span ng-switch-when="7">반품</span>
						</td>
						<td ng-switch="rfid.stat" ng-if="search.type == '02'">
							<span ng-switch-when="1">입고예정</span>
							<span ng-switch-when="2">입고</span>
							<span ng-switch-when="3">출고</span>
							<span ng-switch-when="4">재발행대기</span>
							<span ng-switch-when="5">재발행요청</span>
							<span ng-switch-when="6">반품</span>
							<span ng-switch-when="7">폐기</span>
						</td>
						<td ng-switch="rfid.stat" ng-if="search.type == '03'">
							<span ng-switch-when="1">입고예정</span>
							<span ng-switch-when="2">입고</span>
							<span ng-switch-when="3">판매</span>
							<span ng-switch-when="4">반품</span>
							<span ng-switch-when="5">재발행요청</span>
							<span ng-switch-when="6">매장간이동</span>
							<span ng-switch-when="7">폐기</span>
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
						<td colspan="8" class="text-center">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div>
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
		<div class="text-center">
			<div class="form-group">
				<button class="btn btn-primary justify-content-center" type="button" ng-click="add()"><i class="xi-arrow-bottom" aria-hidden="true"></i> 추가</button>
			</div>
		</div>
		<h5>선택된 태그 목록</h5>
		<div class="form-group">
			<div class="card card-body">
				<table class="table table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th style="width: 300px;" ng-click="sort('rfidTag')">RFID태그</th>
						<th ng-click="sort('boxBarcode')">박스바코드</th>
						<th style="width: 110px;" ng-click="sort('stat')">상태</th>
						<th ng-click="sort('regUserInfo.userId')">등록자</th>
						<th style="width: 180px;" ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updUserInfo.userId')">수정자</th>
						<th ng-click="sort('updDate')">수정일</th>
						<th>작업</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="rfid in result.list track by $index" ng-if="result.list.length > 0">
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
						<td>
							<button class="btn btn-danger justify-content-center" type="button" ng-click="del($index)">삭제</button>
						</td>
					</tr>
					<tr ng-if="result.list.length == 0">
						<td colspan="8" class="text-center">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group form-inline">
				<button class="btn btn-primary" type="button" ng-click="selectInit()">선택된 태그 초기화</button>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="flag" ng-change="orderSelect()">
			<option value="OP-R">생산처입고</option>
			<option value="OP-R2">해외배송입고</option>
		</select>
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button class="btn btn-primary" type="button" ng-click="pdaComplete()">PDA 출고 완료</button>
		<button class="btn btn-primary" type="button" ng-click="complete()">출고 완료</button>
    </div>
</html>