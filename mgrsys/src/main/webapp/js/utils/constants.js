var PDD = PDD || {};
PDD.Cons = PDD.Cons || {};

PDD.Cons.COOKIE_USERID = "userId";
PDD.Cons.COOKIE_USERNAME = "username";
PDD.Cons.COOKIE_ISKF = "isKf";
PDD.Cons.COOKIE_ROLE = "role";

// 左侧功能树
PDD.Cons.LEFT_TREE_DATA = [ {
	"text" : "财务统计模块",
//	state : {
//		opened : true,
//		selected : true
//	},
	"children" : [ {
		"text" : "充值总览",
		openUrl : "./page/fin/inpour/home.html",
		openUrlFunc: "PDD.Fin.showInpourHome()",
		"children" : [ {
				"text" : "时间段内充值",
				openUrl : "./page/fin/inpour/detail.html",
				openUrlFunc: "PDD.Fin.showInpourDetail()",
				children: false
			}, {
				"text" : "充值奖励",
				openUrl : "./page/fin/inpour/reward.html",
				openUrlFunc: "PDD.Fin.showInpourReward()",
				children: false
			}, {
				"text" : "提现记录",
				openUrl : "./page/fin/inpour/withdraw.html",
				openUrlFunc: "PDD.Fin.showInpourWithdraw()",
				children: false
			}, {
				"text" : "待审核提现手续费",
				openUrl : "./page/fin/inpour/pending_withdraw.html",
				openUrlFunc: "PDD.Fin.showInpourPendingWithdraw()",
				children: false
			}, {
				"text" : "修改记录",
				openUrl : "./page/fin/inpour/revise.html",
				openUrlFunc: "PDD.Fin.showInpourRevise()",
				children: false
			}, {
				"text" : "续投奖励",
				openUrl : "./page/fin/inpour/continue.html",
				openUrlFunc: "PDD.Fin.showInpourContinue()",
				children: false
			}, {
				"text" : "用户资金变更记录",
				openUrl : "./page/fin/inpour/capital_change.html",
				openUrlFunc: "PDD.Fin.showInpourCapitalChange()",
				children: false
		} ]
	}, {
		"text" : "投标总览",
		openUrl : "./page/fin/invest/home.html",
		openUrlFunc: "PDD.Fin.showInvestHome()",
		"children" : [ {
				"text" : "客户统计",
				openUrl : "./page/fin/invest/customer.html",
				openUrlFunc: "PDD.Fin.showInvestCustomer()",
				children: false
			}, {
				"text" : "管理员统计",
				openUrl : "./page/fin/invest/admin.html",
				openUrlFunc: "PDD.Fin.showInvestAdmin()",
				children: false
			}, {
				"text" : "借款统计",
				openUrl : "./page/fin/invest/borrowor.html",
				openUrlFunc: "PDD.Fin.showInvestBorrowor()",
				children: false
			}, {
				"text" : "资金日程提醒",
				openUrl : "./page/fin/invest/remind.html",
				openUrlFunc: "PDD.Fin.showInvestRemind()",
				children: false
		} ]
	}, {
		"text" : "排行总览",
		openUrl : "./page/fin/top/home.html",
		openUrlFunc: "PDD.Fin.showTopHome()"
//		, "children" : [ {
//				"text" : "客户统计",
//				openUrl : "./page/fin/invest/customer.html",
//				openUrlFunc: "PDD.Fin.showInvestCustomer()",
//				children: false
//			}, {
//				"text" : "管理员统计",
//				openUrl : "./page/fin/invest/admin.html",
//				openUrlFunc: "PDD.Fin.showInvestAdmin()",
//				children: false
//			}, {
//				"text" : "借款统计",
//				openUrl : "./page/fin/invest/borrowor.html",
//				openUrlFunc: "PDD.Fin.showInvestBorrowor()",
//				children: false
//			}, {
//				"text" : "资金日程提醒",
//				openUrl : "./page/fin/invest/remind.html",
//				openUrlFunc: "PDD.Fin.showInvestRemind()",
//				children: false
//		} ]
	}, {
		"text" : "运营报表",
		openUrl : "./page/fin/report/home.html",
		openUrlFunc: "PDD.Fin.showReportHome()",
		"children" : [ {
				"text" : "发标统计",
				openUrl : "./page/fin/report/borrow.html",
				openUrlFunc: "PDD.Fin.showReportBorrow()",
				children: false
			}, {
				"text" : "秒（还）标统计",
				openUrl : "./page/fin/report/second.html",
				openUrlFunc: "PDD.Fin.showReportSecond()",
				children: false
			}, {
				"text" : "发标归档",
				openUrl : "./page/fin/report/filing.html",
				openUrlFunc: "PDD.Fin.showReportFiling()",
				children: false
		}]
	} ]
}, {
	"text" : "业务总览",
	"openUrl" : "./page/biz/home.html",
	openUrlFunc: "PDD.Biz.showHome()",
	"children" : [ {
		"text" : "抽奖奖品查询",
		openUrl : "./page/biz/lottery/log.html",
		openUrlFunc: "PDD.Biz.showLotteryLog()",
		children: false
	} ]
}, {
	"text" : "数据调整",
	"children" : [ {
		"text" : "投标积分修正",
		openUrl : "./page/adjust/integral.html",
		openUrlFunc: "PDD.Adj.showIntegral()",
		children: false
	} ]
} ];

//js 导出方式仅支持 ie，废弃
// 导出文件目录默认为桌面
//PDD.Cons.exportPath = "C:\\Documents and Settings\\Administrator\\Desktop";

/**
 * datatable 的一项配置中文化配置参数
 * 由于内容固定，遂将其变量化，以便节省代码行数……
 */
PDD.Cons.DATATABLE_OLANGUAGE = {
	"sProcessing" : "加载中，请稍候......",
	"sLengthMenu" : "每页显示 _MENU_ 条记录",
	"sZeroRecords" : "对不起，未找到对应数据。",
	"sEmptyTable" : "未找到数据。",
	"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	"sInfoFiltered" : "共 _MAX_ 条记录",
	"sSearch" : "搜索",
	"oPaginate" : {
		"sFirst" : "首页",
		"sPrevious" : "上一页",
		"sNext" : "下一页",
		"sLast" : "末页"
	}
};

//回款提醒模块，每个标点击后打开会员网站链接的前缀
PDD.Cons.INVEST_REMIND_LINK_STR = "http://www.pandadai.com/invest/##NUM.html";