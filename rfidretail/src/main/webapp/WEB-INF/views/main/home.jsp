<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html ng-app="myApp">
	<div class="starter-template" ng-show="authenticated">
		<div class="container-fluid">
			<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
				<div class="row">
					<uib-tabset active="activePill" vertical="true" type="pills" ng-if="userRole == 'admin' || userRole == 'publish' || userRole == 'publishAdmin'">
					    <uib-tab index="0" heading="바택 차트" ng-click="showTab('bartagChart')"></uib-tab>
					    <uib-tab index="1" heading="생산 차트" ng-click="showTab('productionChart')"></uib-tab>
					    <uib-tab index="2" heading="물류 차트" ng-click="showTab('distributionChart')"></uib-tab>
					    <uib-tab index="3" heading="매장 차트" ng-click="showTab('storeChart')"></uib-tab>
					    <!-- <uib-tab index="2" heading="사이드바 테스트" ng-click="sidebarTest()"></uib-tab> -->
					</uib-tabset>
					<uib-tabset active="activePill" vertical="true" type="pills" ng-if="userRole == 'production'">
					    <uib-tab index="1" heading="생산 차트" ng-click="showTab('productionChart')"></uib-tab>
					</uib-tabset>
					<uib-tabset active="activePill" vertical="true" type="pills" ng-if="userRole == 'distribution'">
					    <uib-tab index="1" heading="물류 차트" ng-click="showTab('distributionChart')"></uib-tab>
					</uib-tabset>
					<uib-tabset active="activePill" vertical="true" type="pills" ng-if="userRole == 'sales' || userRole == 'special'">
					    <uib-tab index="1" heading="매장 차트" ng-click="showTab('storeChart')"></uib-tab>
					</uib-tabset>
				</div>
				<div class="row" ng-show="currentTab == 'bartagChart'">
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">일자별 발주 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.dailyGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeDailyGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.dailyGroupStatsStartDate" is-open="search.dailyGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('dailyGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.dailyGroupStatsEndDate" is-open="search.dailyGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('dailyGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
					          		</div>
		          				</div>
		          				<div class="form-row">
									<div class="form-group form-inline">
										<button ng-click="dailyGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
								<canvas id="dailyGroupStatsLine" class="chart chart-line pointer" chart-data="dailyGroupStatsData"
								chart-labels="dailyGroupStatsLabels" chart-series="dailyGroupStatsSeries" chart-options="dailyGroupStatsOptions"
								chart-click="dailyGroupStatsOnClick">
								</canvas>
								<div ng-if="dailyGroupStatsList.length == 0" class="text-center" style="height:225px">
									<span>데이터가 없습니다.</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">업체별 발주 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.companyGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeCompanyGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.companyGroupStatsStartDate" is-open="search.companyGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('companyGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.companyGroupStatsEndDate" is-open="search.companyGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('companyGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
			          				</div>
			          			</div>
			          			<div class="form-row">
									<div class="form-group form-inline">
										<button ng-click="companyGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
								<canvas id="companyGroupStatsBase" class="chart-horizontal-bar pointer" chart-series="companyGroupStatsSeries" chart-data="companyGroupStatsData" 
										chart-labels="companyGroupStatsLabels" chart-options="companyGroupStatsOptions" chart-dataset-override="companyGroupStatsDatasetOverride" chart-click="companyGroupStatsOnClick"></canvas>
								<!-- 
								<div ng-if="companyGroupStatsList.length == 0" class="text-center" style="height:225px">
									<span>데이터가 없습니다.</span>								
								</div>
								-->
							</div>
						</div>
					</div>
				</div>
				<div class="row" ng-show="currentTab == 'bartagChart'">
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">업체 스타일별 발주 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.companyStyleGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeCompanyStyleGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.companyStyleGroupStatsStartDate" is-open="search.companyStyleGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('companyStyleGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.companyStyleGroupStatsEndDate" is-open="search.companyStyleGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('companyStyleGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;
		          					</div>
		          				</div>
		          				<div class="form-row">
									<div class="form-group form-inline">
										<input type="text" ng-model="search.companyStyleInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyStyleList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
										&nbsp;
										<button ng-click="companyStyleGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
		          				<!-- <div ng-style="search.companyStyleGroupStyle"> -->
		          				<div id="companyStyleGroupStatsDiv">
								<canvas id="companyStyleGroupStatsBase" class="chart-horizontal-bar pointer" chart-series="companyStyleGroupStatsSeries" chart-data="companyStyleGroupStatsData" 
										chart-labels="companyStyleGroupStatsLabels" chart-options="companyStyleGroupStatsOptions" chart-dataset-override="companyStyleGroupStatsDatasetOverride" chart-click="companyStyleGroupStatsOnClick"></canvas>
								</div>
								<div ng-if="companyStyleGroupStatsList.length == 0" class="text-center" style="height:225px">
									<span>데이터가 없습니다.</span>								
								</div>
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">연도/시즌별 발주 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										제품연도 :
						          		&nbsp;&nbsp;
										<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.bartagPieProductYy">
											<option value="all">전체</option>
											<option ng-repeat="select in selectList | filter:customFilter('productYy')" value="{{select.data}}">{{select.data}}</option>
										</select>
										&nbsp;&nbsp;
										시즌 : 
										&nbsp;&nbsp;
										<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.bartagPieProductSeason">
											<option value="all">전체</option>
											<option ng-repeat="select in selectList | filter:customFilter('season')" value="{{select.data}}">
												{{customSeasonFilter(select.data)}}
											</option>
										</select>
		          					</div>
		          				</div>
		          				<div class="form-row">
									<div class="form-group form-inline">
										<input type="text" ng-model="search.bartagPieInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyStyleList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
										&nbsp;
										<button ng-click="bartagPieGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
		          				<div id="bartagPieGroupStatsDiv">
									<canvas id="bartagPieGroupStatsBase" class="chart-pie pointer" chart-series="bartagPieGroupStatsSeries" chart-data="bartagPieGroupStatsData" 
										chart-labels="bartagPieGroupStatsLabels" chart-options="bartagPieGroupStatsOptions" chart-dataset-override="bartagPieGroupStatsDatasetOverride" chart-click="bartagPieGroupStatsOnClick"></canvas>
								</div>
								<div ng-if="bartagPieGroupStatsList.length == 0" class="text-center" style="height:225px">
									<span>데이터가 없습니다.</span>								
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row" ng-show="currentTab == 'productionChart'">
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">일자별 RFID 생산 요청 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.dailyBartagOrderGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeDailyBartagOrderGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.dailyBartagOrderGroupStatsStartDate" is-open="search.dailyBartagOrderGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('dailyBartagOrderGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.dailyBartagOrderGroupStatsEndDate" is-open="search.dailyBartagOrderGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('dailyBartagOrderGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
			          				</div>
			          			</div>
			          			<div class="form-row">
									<div class="form-group form-inline">
										<button ng-click="dailyBartagOrderGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
									</div>
								</div>
								<canvas id="dailyBartagOrderGroupStatsLine" class="chart chart-line pointer" chart-data="dailyBartagOrderGroupStatsData"
								chart-labels="dailyBartagOrderGroupStatsLabels" chart-series="dailyBartagOrderGroupStatsSeries" chart-options="dailyBartagOrderGroupStatsOptions"
								chart-click="dailyBartagOrderGroupStatsOnClick">
								</canvas>
								<div ng-if="dailyBartagOrderGroupStatsList.length == 0" class="text-center" style="height:225px">
									<span>데이터가 없습니다.</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">생산업체 스타일별 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.productionStyleGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeProductionStyleGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.productionStyleGroupStatsStartDate" is-open="search.productionStyleGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('productionStyleGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.productionStyleGroupStatsEndDate" is-open="search.productionStyleGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('productionStyleGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
		          					</div>
		          				</div>
		          				<div class="form-row">
									<div class="form-group form-inline">
										<input type="text" ng-model="search.productionStyleInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyStyleList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'">
										&nbsp;
										<button ng-click="productionStyleGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
		          				<!-- <div ng-style="search.companyStyleGroupStyle"> -->
		          				<div id="productionStyleGroupStatsDiv">
								<canvas id="productionStyleGroupStatsBase" class="chart-horizontal-bar pointer" chart-series="productionStyleGroupStatsSeries" chart-data="productionStyleGroupStatsData" 
										chart-labels="productionStyleGroupStatsLabels" chart-options="productionStyleGroupStatsOptions" chart-dataset-override="productionStyleGroupStatsDatasetOverride" chart-click="productionStyleGroupStatsOnClick"></canvas>
								</div>
								<div ng-if="productionStyleGroupStatsList.length == 0" class="text-center" style="height:225px">
									<span>데이터가 없습니다.</span>								
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row" ng-show="currentTab == 'productionChart'">
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">생산업체 출고 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.productionReleaseGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeproductionReleaseGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.productionReleaseGroupStatsStartDate" is-open="search.productionReleaseGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('productionReleaseGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.productionReleaseGroupStatsEndDate" is-open="search.productionReleaseGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('productionReleaseGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
		          					</div>
		          				</div>
		          				<div class="form-row">
									<div class="form-group form-inline">
										<input type="text" ng-model="search.productionReleaseInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyStyleList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'">
										&nbsp;
										<button ng-click="productionReleaseGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
		          				<div id="productionReleaseGroupStatsDiv">
								<canvas id="productionReleaseGroupStatsBase" class="chart-pie pointer" chart-series="productionReleaseGroupStatsSeries" chart-data="productionReleaseGroupStatsData" 
										chart-labels="productionReleaseGroupStatsLabels" chart-options="productionReleaseGroupStatsOptions" chart-dataset-override="productionReleaseGroupStatsDatasetOverride" chart-click="productionReleaseGroupStatsOnClick"></canvas>
								</div>
								<div ng-if="productionReleaseGroupStatsList.length == 0" class="text-center" style="height:225px">
									<span>데이터가 없습니다.</span>								
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--   물류차트 시작   -->
				<div class="row" ng-show="currentTab == 'distributionChart'">
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">업체별 입고 예정 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.distributionStorageGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeDistributionStorageGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.distributionStorageGroupStatsStartDate" is-open="search.distributionStorageGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('distributionStorageGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.distributionStorageGroupStatsEndDate" is-open="search.distributionStorageGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('distributionStorageGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
		          					</div>
		          				</div>
		          				<div class="form-row">
									<div class="form-group form-inline">
										기준 : &nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.distributionStorageStatsSearchGroup">
											<option value="box">박스</option>
											<option value="style">스타일</option>
											<option value="tag">태그</option>
										</select>
										&nbsp;
										<button ng-click="distributionStorageGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
		          				<div id="distributionStorageGroupStatsDiv">
								<canvas id="distributionStorageGroupStatsBase" class="chart-horizontal-bar pointer" chart-series="distributionStorageGroupStatsSeries" chart-data="distributionStorageGroupStatsData" 
										chart-labels="distributionStorageGroupStatsLabels" chart-options="distributionStorageGroupStatsOptions" chart-dataset-override="distributionStorageGroupStatsDatasetOverride" chart-click="distributionStorageGroupStatsOnClick"></canvas>
								</div>
								<div ng-if="distributionStorageGroupStatsList.length == 0" class="text-center" style="height:225px">
									<br><span>데이터가 없습니다.</span>								
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--   물류차트 끝   -->
				<!--   매장차트 시작   -->
				<div class="row" ng-show="currentTab == 'storeChart'">
					<div class="col-6">
						<div class="card">
							<h5 class="card-header">매장별 입고 예정 수량</h5>
							<div class="card-body">
								<div class="form-row">
									<div class="form-group form-inline">
										선택 : 
										&nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.storeStorageCompanyGroupStatsDefaultDate" ng-change="changeStatsSearchDate('changeStoreStorageCompanyGroup')">
											<option value="1">1달전</option>
											<option value="2">3달전</option>
											<option value="3">6달전</option>
											<option value="4">1년전</option>
										</select>
										&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.storeStorageCompanyGroupStatsStartDate" is-open="search.storeStorageCompanyGroupStatsStartDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('storeStorageCompanyGroupStatsStart')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
						          		&nbsp;~&nbsp;
										<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="search.storeStorageCompanyGroupStatsEndDate" is-open="search.storeStorageCompanyGroupStatsEndDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:120px;margin-right:5px;"/>
						          		<span class="input-group-btn" style="margin-right:5px;">
							            	<button type="button" class="btn btn-secondary" ng-click="statsOpen('storeStorageCompanyGroupStatsEnd')">
							              		&nbsp;<i class="xi-calendar"></i>&nbsp;
							            	</button>
						          		</span>
		          					</div>
		          				</div>
		          				<div class="form-row">
									<div class="form-group form-inline">
										기준 : &nbsp;
										<select class="custom-select" style="margin-right:5px;" ng-model="search.storeStorageCompanyGroupStatsSearchGroup">
											<option value="box">박스</option>
											<option value="style">스타일</option>
											<option value="tag">태그</option>
										</select>
										&nbsp;
										<button ng-click="storeStorageCompanyGroupStatsHeadSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>									
									</div>
								</div>
		          				<div id="storeStorageCompanyGroupStatsDiv">
								<canvas id="storeStorageCompanyGroupStatsBase" class="chart-horizontal-bar pointer" chart-series="storeStorageCompanyGroupStatsSeries" chart-data="storeStorageCompanyGroupStatsData" 
										chart-labels="storeStorageCompanyGroupStatsLabels" chart-options="storeStorageCompanyGroupStatsOptions" chart-dataset-override="storeStorageCompanyGroupStatsDatasetOverride" chart-click="storeStorageCompanyGroupStatsOnClick"></canvas>
								</div>
								<div ng-if="storeStorageCompanyGroupStatsList.length == 0" class="text-center" style="height:225px">
									<br><span>데이터가 없습니다.</span>								
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--   매장차트 끝   -->
			</div>
		</div>
	</div>
</html>