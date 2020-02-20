<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">검색</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<div class="card card-body" style="margin-bottom:10px;"> 
			<div class="form-row">
				<div class="form-group form-inline">
					검색 기준 : 
		          	&nbsp;&nbsp;
					<div class="custom-control custom-radio custom-control-inline">
						<input name="inputCheckYn" id="inputCheckYn1" type="radio" ng-model="search.searchType" value="boxBarcode" class="custom-control-input">
						<label class="custom-control-label" for="inputCheckYn1">박스바코드</label>
					</div>
					<div class="custom-control custom-radio custom-control-inline">
						<input name="inputCheckYn" id="inputCheckYn2" type="radio" ng-model="search.searchType" value="rfidTag" class="custom-control-input">
						<label class="custom-control-label" for="inputCheckYn2">RFID 태그</label>
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group form-inline" ng-if="search.searchType == 'boxBarcode'">
					박스 바코드 :
					&nbsp;&nbsp;
					<input type="text" class="form-control" placeholder="검색" ng-model="search.text" style="width:300px;" id="inputSearch">
					&nbsp;&nbsp;
					<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
				</div>
				<div class="form-group form-inline" ng-if="search.searchType == 'rfidTag'">
					스타일 :
					&nbsp;&nbsp;
					<input type="text" ng-model="search.style" placeholder="스타일 입력" uib-typeahead="style as style.data for style in styleList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'style')">
					&nbsp;&nbsp;
					컬러 :
					&nbsp;&nbsp;
					<input type="text" ng-model="search.color" placeholder="컬러 입력" uib-typeahead="color as color.data for color in colorList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'color')">
					&nbsp;&nbsp;
					사이즈 :
					&nbsp;&nbsp;
					<input type="text" ng-model="search.styleSize" placeholder="사이즈 입력" uib-typeahead="size as size.data for size in sizeList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'styleSize')">
					&nbsp;&nbsp;
					태그 시리얼 :
					&nbsp;&nbsp; 
					<input type="text" class="form-control" placeholder="검색" ng-model="search.text" id="inputSearch">
					&nbsp;&nbsp;
					<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
				</div>
		    </div>
		</div>
		<div ng-if="search.searchType == 'boxBarcode'">
			<div class="form-group">
				<h5>물류 입고 박스 정보 <small>({{data.storageScheduleList == undefined ? '0' : data.storageScheduleList.length}}건)</small></h5>
				<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr class="pointer">
							<th>도착 예정일</th>
							<th>바코드</th>
							<th>스타일</th>
							<th>컬러</th>
							<th>사이즈</th>
							<th>오더차수</th>
							<th>수량</th>
							<th>총 수량</th>
							<th>출발지</th>
							<th>도착지</th>
							<th>상태</th>
							<th>등록일</th>
							<th>수정일</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="obj in data.storageScheduleList" class="pointer" ng-if="data.storageScheduleList.length == 1">
							<td ng-click="detail(obj)">
								<span ng-if="obj.boxInfo.arrivalDate">
									{{obj.boxInfo.arrivalDate | date:'yyyyMMdd'}}
								</span>
								<span ng-if="!obj.boxInfo.arrivalDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)">{{obj.boxInfo.barcode}}</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.style}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.color}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.size}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.orderDegree}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.amount}}
							</td>
							<td ng-click="detail(obj)">
								{{getTotalAmount(obj)}}
							</td>
							<td ng-click="detail(obj)">{{obj.boxInfo.startCompanyInfo.name}}</td>
							<td ng-click="detail(obj)">{{obj.boxInfo.endCompanyInfo.name}}</td>
							<td ng-click="detail(obj)">
								<span ng-if="obj.disuseYn == 'N' && obj.confirmYn == 'N' && obj.completeYn == 'N'">미확정</span>
								<span ng-if="obj.disuseYn == 'N' && obj.confirmYn == 'Y' && obj.completeYn == 'N'">입고 예정</span>
								<span ng-if="obj.disuseYn == 'N' && obj.confirmYn == 'Y' && obj.completeYn == 'Y'">입고 완료</span>
								<span ng-if="obj.disuseYn == 'Y' && obj.returnYn == 'Y'">입고 반송(폐기)</span>
								<span ng-if="obj.disuseYn == 'Y' && obj.returnYn == 'N'">폐기</span>
							</td>
							<td ng-click="detail(obj)">
								<span ng-if="obj.regDate">
									{{obj.regDate | date:'yyyy-MM-dd'}}<br />
									{{obj.regDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.regDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)">
								<span ng-if="obj.updDate">
									{{obj.updDate | date:'yyyy-MM-dd'}}<br />
									{{obj.updDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.updDate">
									-
								</span>
							</td>
						</tr>
						<tr ng-repeat-start="obj in data.storageScheduleList" class="pointer" ng-if="data.storageScheduleList.length > 1">
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>
								<span ng-if="obj.boxInfo.arrivalDate">
									{{obj.boxInfo.arrivalDate | date:'yyyyMMdd'}}
								</span>
								<span ng-if="!obj.boxInfo.arrivalDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>{{obj.boxInfo.barcode}}</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.style}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.color}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.size}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.orderDegree}}
							</td>
							<td ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="$first">
								{{detail.amount}}
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>
								{{getTotalAmount(obj)}}
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>{{obj.boxInfo.startCompanyInfo.name}}</td>
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>{{obj.boxInfo.endCompanyInfo.name}}</td>
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>
								<span ng-if="obj.disuseYn == 'N' && obj.confirmYn == 'N' && obj.completeYn == 'N'">미확정</span>
								<span ng-if="obj.disuseYn == 'N' && obj.confirmYn == 'Y' && obj.completeYn == 'N'">입고 예정</span>
								<span ng-if="obj.disuseYn == 'N' && obj.confirmYn == 'Y' && obj.completeYn == 'Y'">입고 완료</span>
								<span ng-if="obj.disuseYn == 'Y' && obj.returnYn == 'Y'">입고 반송(폐기)</span>
								<span ng-if="obj.disuseYn == 'Y' && obj.returnYn == 'N'">폐기</span>
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>
								<span ng-if="obj.regDate">
									{{obj.regDate | date:'yyyy-MM-dd'}}<br />
									{{obj.regDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.regDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.storageScheduleDetailLog.length}}>
								<span ng-if="obj.updDate">
									{{obj.updDate | date:'yyyy-MM-dd'}}<br />
									{{obj.updDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.updDate">
									-
								</span>
							</td>
						</tr>
						<tr ng-repeat-end ng-repeat="detail in obj.storageScheduleDetailLog" ng-if="!$first" class="pointer">
							<td>
								{{detail.style}}
							</td>
							<td>
								{{detail.color}}
							</td>
							<td>
								{{detail.size}}
							</td>
							<td>
								{{detail.orderDegree}}
							</td>
							<td>
								{{detail.amount}}
							</td>
						</tr>
						<tr>
							<td colspan="15" class="text-center" ng-if="data.storageScheduleList == undefined || data.storageScheduleList.length == 0">
								No Data
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div ng-if="search.searchType == 'boxBarcode' && userRole != 'production'">
			<div class="form-group">
				<h5>물류 출고 박스 정보 <small>({{data.releaseScheduleList == undefined ? '0' : data.releaseScheduleList.length}}건)</small></h5>
				<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr class="pointer">
							<th>도착 예정일</th>
							<th>바코드</th>
							<th>스타일</th>
							<th>컬러</th>
							<th>사이즈</th>
							<th>오더차수</th>
							<th>수량</th>
							<th>총 수량</th>
							<th>출발지</th>
							<th>도착지</th>
							<th>상태</th>
							<th>등록일</th>
							<th>수정일</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="obj in data.releaseScheduleList" class="pointer" ng-if="data.releaseScheduleList.length == 1">
							<td ng-click="detail(obj)">
								<span ng-if="obj.boxInfo.arrivalDate">
									{{obj.boxInfo.arrivalDate | date:'yyyyMMdd'}}
								</span>
								<span ng-if="!obj.boxInfo.arrivalDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)">{{obj.boxInfo.barcode}}</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.style}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.color}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.size}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.orderDegree}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.amount}}
							</td>
							<td ng-click="detail(obj)">
								{{getTotalAmount(obj)}}
							</td>
							<td ng-click="detail(obj)">{{obj.boxInfo.startCompanyInfo.name}}</td>
							<td ng-click="detail(obj)">{{obj.boxInfo.endCompanyInfo.name}}</td>
							<td ng-click="detail(obj)">
								<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'N'">물류 출고</span>
								<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'Y'">매장 입고 완료</span>
							</td>
							<td ng-click="detail(obj)">
								<span ng-if="obj.regDate">
									{{obj.regDate | date:'yyyy-MM-dd'}}<br />
									{{obj.regDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.regDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)">
								<span ng-if="obj.updDate">
									{{obj.updDate | date:'yyyy-MM-dd'}}<br />
									{{obj.updDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.updDate">
									-
								</span>
							</td>
						</tr>
						<tr ng-repeat-start="obj in data.releaseScheduleList" class="pointer" ng-if="data.releaseScheduleList.length > 1">
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>
								<span ng-if="obj.boxInfo.arrivalDate">
									{{obj.boxInfo.arrivalDate | date:'yyyyMMdd'}}
								</span>
								<span ng-if="!obj.boxInfo.arrivalDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>{{obj.boxInfo.barcode}}</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.style}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.color}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.size}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.orderDegree}}
							</td>
							<td ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="$first">
								{{detail.amount}}
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>
								{{getTotalAmount(obj)}}
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>{{obj.boxInfo.startCompanyInfo.name}}</td>
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>{{obj.boxInfo.endCompanyInfo.name}}</td>
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>
								<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'N'">물류 출고</span>
								<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'Y'">매장 입고 완료</span>
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>
								<span ng-if="obj.regDate">
									{{obj.regDate | date:'yyyy-MM-dd'}}<br />
									{{obj.regDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.regDate">
									-
								</span>
							</td>
							<td ng-click="detail(obj)" rowspan={{obj.releaseScheduleDetailLog.length}}>
								<span ng-if="obj.updDate">
									{{obj.updDate | date:'yyyy-MM-dd'}}<br />
									{{obj.updDate | date:'HH:mm:ss'}}
								</span>
								<span ng-if="!obj.updDate">
									-
								</span>
							</td>
						</tr>
						<tr ng-repeat-end ng-repeat="detail in obj.releaseScheduleDetailLog" ng-if="!$first" class="pointer">
							<td>
								{{detail.style}}
							</td>
							<td>
								{{detail.color}}
							</td>
							<td>
								{{detail.size}}
							</td>
							<td>
								{{detail.orderDegree}}
							</td>
							<td>
								{{detail.amount}}
							</td>
						</tr>
						<tr>
							<td colspan="15" class="text-center" ng-if="data.releaseScheduleList == undefined || data.releaseScheduleList.length == 0">
								No Data
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div ng-if="search.searchType == 'rfidTag'">
				<h5>태그 정보 <small>({{data.rfidTagList == undefined ? '0' : data.rfidTagList.length}}건)</small></h5>
				<div class="form-group">
					<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
						<thead>
							<tr class="pointer">
								<th>스타일</th>
								<th>컬러</th>
								<th>사이즈</th>
								<th>일련번호</th>
								<th>상세정보</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="obj in data.rfidTagList" ng-if="data.rfidTagList.length > 0">
								<td>{{search.tempStyle}}</td>
								<td>{{search.tempColor}}</td>
								<td>{{search.tempSize}}</td>
								<td>{{obj.rfidSeq}}</td>
								<td><button ng-click="searchDetail(obj)" class="btn btn-primary">&nbsp;&nbsp;상세보기&nbsp;&nbsp;</button></td>
							</tr>
							<tr ng-if="data.rfidTagList == undefined || data.rfidTagList.length == 0">
								<td colspan="5">
									No Data
								</td>
							</tr>
						</tbody>
					</table>
				</div>
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="ok()">닫기</button>
    </div>
</html>