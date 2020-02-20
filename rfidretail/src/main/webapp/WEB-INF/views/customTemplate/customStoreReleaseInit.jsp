<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">매장 출고예정 관리</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<div class="form-row">
			<div class="form-group form-inline">
				데이터 기준 선택 
				&nbsp;&nbsp;
				<div class="custom-control custom-radio custom-control-inline">
					<input name="inputCheckYn" id="inputCheckYn1" type="radio" ng-model="search.dataCriteria" value="distribution" class="custom-control-input" ng-change="changeDataCriteria()">
					<label class="custom-control-label" for="inputCheckYn1">물류</label>
				</div>
				<div class="custom-control custom-radio custom-control-inline">
					<input name="inputCheckYn" id="inputCheckYn2" type="radio" ng-model="search.dataCriteria" value="production" class="custom-control-input" ng-change="changeDataCriteria()">
					<label class="custom-control-label" for="inputCheckYn2">매장</label>
				</div>
			</div>
		</div>
		<div class="form-row">
			<h5>ERP 출고예정 정보 추가</h5>
			<table class="table table-striped table-bordered custom-align-middle">
				<thead>
					<tr class="pointer text-center">
						<th>마감일자</th>
						<th>마감시리얼</th>
						<th>RFID여부</th>
						<th>연도</th>
						<th>시즌</th>
						<th>스타일</th>
						<th>컬러</th>
						<th>사이즈</th>
						<th>출고수량</th>
						<th>도착지</th>
						<th>액션</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<input type="text" ng-model="search.completeDate"  placeholder="마감일자 입력" class="form-control">
						</td>
						<td>
							<input type="text" ng-model="search.completeSeq"  placeholder="마감시리얼 입력" class="form-control">
						</td>
						<td>
							<select class="custom-select" ng-model="search.rfidYn" style="width:70px;">
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</td>
						<td>
							<input type="text" ng-model="search.productYy" placeholder="연도 입력" uib-typeahead="productYy as productYy.data for productYy in productYyList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productYy')">
						</td>
						<td>
							<input type="text" ng-model="search.productSeason" placeholder="시즌 입력" uib-typeahead="productSeason as productSeason.data for productSeason in productSeasonList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagSeasonData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productSeason')">
						</td>
						<td>
							<input type="text" ng-model="search.style" placeholder="스타일 입력" uib-typeahead="style as style.data for style in styleList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'style')">
						</td>
						<td>
							<input type="text" ng-model="search.color" placeholder="컬러 입력" uib-typeahead="color as color.data for color in colorList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'color')">
						</td>
						<td>
							<input type="text" ng-model="search.styleSize" placeholder="사이즈 입력" uib-typeahead="size as size.data for size in sizeList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'styleSize')">
						</td>
						<td>
							<input type="text" ng-model="search.orderAmount"  placeholder="수량 입력" class="form-control" id="inputBarcode">
						</td>
						<td>
							<input type="text" ng-model="search.endCompanyInfo" placeholder="도착 업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						</td>
						<td>
							<button class="btn btn-primary btn-block" type="button" ng-click="erpStoreScheduleAdd()">저장</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h5>ERP 출고예정 목록</h5>
		<div class="form-row">
			<div class="form-group col-3">
				ReferenceNo :
				&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="ReferenceNo 입력" ng-model="itemSearch.referenceNo" id="inputBarcode">			
			</div>
			<div class="form-group col-3">
				스타일 :
				&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="스타일 입력" ng-model="itemSearch.style" id="inputBarcode">			
			</div>
			<div class="form-group col-3">
				컬러 :
				&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="컬러 입력" ng-model="itemSearch.color" id="inputBarcode">			
			</div>
			<div class="form-group col-3">
				사이즈 :
				&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="사이즈 입력" ng-model="itemSearch.size" id="inputBarcode">			
			</div>
			<div class="form-group col-12">
				<ul class="list-unstyled row" dnd-list dnd-drop="onDrop(storeSchedule, item, index)">
				    <li class="list-item col-4 border py-2 list-group-item" ng-repeat="item in storeSchedule.items | filter:itemSearch"
				        dnd-draggable="getSelectedItemsIncluding(storeSchedule, item)"
				        dnd-dragstart="onDragstart(storeSchedule, event)"
				        dnd-moved="onMoved(storeSchedule)"
				        dnd-dragend="storeSchedule.dragging = false"
				        dnd-selected="item.selected = !item.selected"
				        ng-class="{'active': item.selected}"
				        ng-hide="storeSchedule.dragging && item.selected">
				        ReferenceNo : {{item.completeDate}}{{item.endCompanyInfo.customerCode}}{{item.endCompanyInfo.cornerCode}}{{item.completeSeq}}&nbsp;{{item.endCompanyInfo.name}}&nbsp;RFID 여부 : {{item.rfidYn}}<br/>스타일 : {{item.style}} 컬러 : {{item.color}} 사이즈 : {{item.size}} 출고수량 : {{item.orderAmount}}&nbsp; 
				        <span ng-if="item.releaseAmount > 0 && item.orderAmount == item.releaseAmount"class="badge badge-success">출고완료</span>
				        <span ng-if="item.releaseAmount > 0 && item.orderAmount != item.releaseAmount"class="badge badge-warning">진행중</span>
				    </li>
				</ul>
			</div>
		</div>
		<div class="text-center">
			<div class="form-group">
				<button class="btn btn-danger justify-content-center" type="button" ng-click="erpStoreScheduleDel()">ERP 출고예정정보 삭제</button>
			</div>
		</div>
		<h5>WMS 출고예정 목록 Total({{list.length}}건)</h5>
		<div class="form-row">
			<div class="form-group col-3">
				ReferenceNo :
				&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="ReferenceNo 입력" ng-model="selectItemSearch.referenceNo" id="inputBarcode">			
			</div>
		</div>
		<table class="table table-striped table-bordered custom-align-middle">
				<thead>
					<tr class="pointer text-center">
						<!-- 
						<th style="width:180px;">Tshipment</th>
						-->
						<th style="width:90%">배차번호</th>
						<th>액션</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<!-- 
						<td>
							<div class="form-group row"
							        dnd-dragstart="onDragstart(storeSchedule, event)"
							        dnd-moved="onMoved(storeSchedule)"
							        dnd-dragend="storeSchedule.dragging = false"
							        dnd-selected="item.selected = !item.selected"
							        ng-class="{'active': item.selected}"
							        ng-hide="storeSchedule.dragging && item.selected">
						        <div class="col-12">
									<input type="text" class="form-control" placeholder="referenceNo 입력" ng-model="data.referenceNo" id="inputReferenceNo">
								</div>
							</div>
							<div class="form-group row">
								<div class="col-12">
									<input type="text" class="form-control" placeholder="shipmentNo 입력" ng-model="data.key.shipmentNo" id="inputShipmentNo">
								</div>
							</div>
						</td>
						-->
						<td dnd-list dnd-drop="onSelectDrop(storeSchedule, item, index)">
							<div class="form-group row" ng-repeat="obj in data.tboxPickList" 
									dnd-dragstart="onDragstart(storeSchedule, event)"
							        dnd-moved="onMoved(storeSchedule)"
							        dnd-dragend="storeSchedule.dragging = false"
							        dnd-selected="item.selected = !item.selected"
							        ng-class="{'active': item.selected}"
							        ng-hide="storeSchedule.dragging && item.selected">
								<div class="col-2">
									<input type="text" class="form-control" placeholder="바코드 입력" ng-model="obj.orderQty">
								</div>
								<div class="col-9">
									<div class="form-group row">
										<div class="col-3">
											<input type="text" class="form-control" placeholder="스타일 입력" ng-model="obj.style">
										</div>
										<div class="col-1">
											<input type="text" class="form-control" placeholder="컬러 입력" ng-model="obj.color">
										</div>
										<div class="col-1">
											<input type="text" class="form-control" placeholder="사이즈 입력" ng-model="obj.size">
										</div>
										<div class="col-3">
											<input type="text" class="form-control" placeholder="레퍼런스 번호 입력" ng-model="obj.referenceNo">
										</div>
										<div class="col-3">
											<input type="text" class="form-control" placeholder="배차번호 입력" ng-model="obj.shipmentNo">
										</div>
										<div class="col-1">
											<input type="number" class="form-control" placeholder="출고예정 수량 입력" ng-model="obj.pickQty">
										</div>
									</div>
								</div>
								<div class="col-1">
									<button class="btn btn-danger btn-block" type="button" ng-click="tboxDel(obj, $index)">삭제</button>
								</div>
							</div>
						</td>
						<td>
							<div class="form-group row">
								<div class="col-12">
									<button class="btn btn-primary btn-block" type="button" ng-click="wmsShipmentAdd()">저장</button>
									<button class="btn btn-danger btn-block" type="button" ng-click="tboxAllDel()">초기화</button>
								</div>
							</div>
						</td>
					</tr>
					<tr ng-repeat="obj in list | filter:selectItemSearch" class="pointer" ng-if="list.length > 0">
						<!-- 
						<td>
							<div class="form-group row">
								<div class="col-12">
									<input type="text" class="form-control" placeholder="referenceNo 입력" ng-model="obj.referenceNo" id="inputReferenceNo" ng-readonly="true">
								</div>
							</div>
							<div class="form-group row">
								<div class="col-12">
									<input type="text" class="form-control" placeholder="shipmentNo 입력" ng-model="obj.key.shipmentNo" id="inputShipmentNo" ng-readonly="true">
								</div>
							</div>
						</td>
						-->
						<td>
							<div class="form-group row">
								<div class="col-2 align-middle">
									<input type="text" class="form-control" placeholder="바코드 입력" ng-model="obj.barcode" id="inputShipmentNo" ng-readonly="true">
								</div>
								<div class="col-9">	
									<div ng-repeat="tbox in obj.styleList" class="text-center" >
										<div class="form-group row">
											<div class="col-3">
												<input type="text" class="form-control" placeholder="스타일 입력" ng-model="tbox.style" id="inputStyle" ng-readonly="true">
											</div>
											<div class="col-1">
												<input type="text" class="form-control" placeholder="컬러 입력" ng-model="tbox.color" id="inputColor" ng-readonly="true">
											</div>
											<div class="col-1">
												<input type="text" class="form-control" placeholder="사이즈 입력" ng-model="tbox.size" id="inputSize" ng-readonly="true">
											</div>
											<div class="col-3">
												<input type="text" class="form-control" placeholder="레퍼런스 번호 입력" ng-model="tbox.referenceNo" id="inputReferenceNo" ng-readonly="true">
											</div>
											<div class="col-3">
												<input type="text" class="form-control" placeholder="배차번호 입력" ng-model="tbox.key.shipmentNo" id="inputShipmentNo" ng-readonly="true">
											</div>
											<div class="col-1">
												<input type="number" class="form-control" placeholder="수량 입력" ng-model="tbox.pickQty" id="inputPickQty" ng-readonly="true">
											</div>
										</div>
										<div class="form-group row">
											<div class="col-3">
												<button type="button" class="btn btn-success btn-block" ng-if="tbox.selectMappingList.length > 0">
												  	<span class="badge badge-light">{{tbox.selectMappingList.length}}</span>&nbsp;선택완료
												</button>
											</div>
											<div class="col-6">
												<button class="btn btn-primary btn-block" type="button" ng-click="mappingToggle(tbox)" ng-if="tbox.rfidFlag == 'N' && tbox.erpStoreSchedule != undefined && tbox.erpStoreSchedule.rfidYn == 'Y'">
													<i ng-class="{'xi-angle-down' : !tbox.openFlag, 'xi-angle-up' : tbox.openFlag}"></i>&nbsp;{{tbox.openFlag ? '맵핑 작업 닫기' : '맵핑 작업 열기' }}
												</button>
												<button class="btn btn-info btn-block" type="button" ng-if="tbox.rfidFlag == 'N' && tbox.erpStoreSchedule != undefined && tbox.erpStoreSchedule.rfidYn == 'N'">
													RFID 미부착 스타일
												</button>
												<button class="btn btn-warning btn-block" type="button" ng-if="tbox.rfidFlag == 'N' && tbox.erpStoreSchedule == undefined">
													ERP 출고 예정 정보 없음
												</button>
												<button class="btn btn-success btn-block" type="button" ng-if="tbox.rfidFlag == 'Y'">
													맵핑 작업 완료
												</button>
											</div>
											<div class="col-1">
											</div>
											<div class="col-2">
												<!-- 
												<button class="btn btn-danger btn-block" type="button" ng-click="wmsTboxDelete(tbox)">개별 삭제</button>
												-->
											</div>
										</div>
										<div class="form-group row" ng-if="tbox.openFlag">
											<div class="col-5">
												<ul class="list-unstyled list-group row" style="padding-left:2px;">
													<li class="list-item col-12 border pt-3">
														<div class="form-group row">
													        <div class="col-2">
													        	<div class="custom-control custom-checkbox">
																	<input class="custom-control-input" type="checkbox" ng-model="tbox.mappingAllCheckFlag" ng-click="allCheck(tbox, 'mapping')" id="allCheck"/>
																	<label class="custom-control-label" for="allCheck">체크</label>
																</div>
													        </div>
													        <div class="col-10">
													        	RFID 태그
													        </div>
												        </div>
													</li>
												    <li class="list-group-item col-12 border pt-3" ng-repeat="tag in tbox.mappingList | orderBy: 'rfidSeq'" ng-click="mappingClick(tag)">
												    	<div class="form-group row">
													        <div class="col-2">
													        	<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="tag.check">
																	<label class="custom-control-label" for="customCheck{{$index}}"></label>
																</div>
													        </div>
													        <div class="col-10">
													        	{{tag.rfidTag}} &nbsp;&nbsp; 
													        	<div ng-switch="tag.stat">
													        		<span ng-switch-when="1">입고 예정</span>
																	<span ng-switch-when="2">입고</span>
																	<span ng-switch-when="3">출고</span>
																	<span ng-switch-when="4">재발행대기</span>
																	<span ng-switch-when="5">재발행요청</span>
																	<span ng-switch-when="6">반품</span>
																	<span ng-switch-when="7">폐기</span>
													        	</div>
													        </div>
												        </div>
												    </li>
												</ul>
											</div>
											<div class="col-2">
												<br />
												<br />
												<button class="btn btn-info btn-block" type="button" ng-click="mappingSelect(tbox)">
													&nbsp;&nbsp;
													<i class="xi-angle-right"></i>
													&nbsp;&nbsp;
												</button>
												<br />
												<br />
												<button class="btn btn-info btn-block" type="button" ng-click="mappingReturn(tbox)">
													&nbsp;&nbsp;
													<i class="xi-angle-left"></i>
													&nbsp;&nbsp;
												</button>
											</div>
											<div class="col-5">
												<ul class="list-unstyled row" style="padding-left:2px;">
													<li class="list-item col-12 border pt-3">
														<div class="form-group row">
													        <div class="col-2">
													        	<div class="custom-control custom-checkbox">
																	<input class="custom-control-input" type="checkbox" ng-model="tbox.selectMappingAllCheckFlag" ng-click="allCheck(tbox, 'select')" id="selectCheck"/>
																	<label class="custom-control-label" for="selectCheck">체크</label>
																</div>
													        </div>
													        <div class="col-10">
													        	RFID 태그
													        </div>
												        </div>
													</li>
												    <li class="list-item col-12 border pt-3" ng-repeat="tag in tbox.selectMappingList | orderBy: 'rfidSeq'" ng-click="mappingClick(tag)">
												    	<div class="form-group row">
													        <div class="col-2">
													        	<div class="custom-control custom-checkbox">
																	<input type="checkbox" class="custom-control-input" id="selectCheck{{$index}}" ng-model="tag.check">
																	<label class="custom-control-label" for="selectCheck{{$index}}"></label>
																</div>
													        </div>
													        <div class="col-10">
													        	{{tag.rfidTag}} &nbsp;&nbsp; 
													        	<div ng-switch="tag.stat">
													        		<span ng-switch-when="1">입고 예정</span>
																	<span ng-switch-when="2">입고</span>
																	<span ng-switch-when="3">출고</span>
																	<span ng-switch-when="4">재발행대기</span>
																	<span ng-switch-when="5">재발행요청</span>
																	<span ng-switch-when="6">반품</span>
																	<span ng-switch-when="7">폐기</span>
													        	</div>
													        </div>
												        </div>
												    </li>
												</ul>						
											</div>
										</div>
									</div>
								</div>
								<div class="col-1">
									<button type="button" class="btn btn-primary btn-block" ng-click="wmsTboxTest(obj)">테스트</button>
									<button class="btn btn-danger btn-block" type="button" ng-click="wmsTboxDelete(obj)">삭제</button>
								</div>
							</div>
						</td>
						<td>
							<div class="form-group row">
								<div class="col-12">
									<!-- 
									<button class="btn btn-primary btn-block" type="button" ng-click="wmsTotalTest(obj)" ng-if="obj.status == '999'">종합 테스트</button>
									-->
									<button class="btn btn-primary btn-block" type="button" ng-click="wmsComplete(obj)" ng-if="obj.status == '999'">테스트 완료</button>
									<button class="btn btn-info btn-block" type="button" ng-click="wmsShipmentInit(obj)">초기화</button>
									<!-- 
									<button class="btn btn-danger btn-block" type="button" ng-click="wmsShipmentDel(obj)">Tshipment 삭제</button>
									-->
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
	</div>
	<div class="modal-footer">
		<!-- 
		<button class="btn" type="button" ng-click="popTest()">팝업열기</button>
		<button class="btn" type="button" ng-click="confirmTest()">컴펌열기</button>
		-->
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
    </div>
</html>