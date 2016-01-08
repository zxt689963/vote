package com.ok8.common.global;

/**
 * 错误代码
 */
public class ErrorCode {
	
	/** 操作成功 */
	public static final int SUCCESS = 10000;
	/** 操作出错 */
	public static final int ERROR = 10001;
	/** JSON格式不正确 */
	public static final int ERR_JSON = 10002;
	/** 参数异常 */
	public static final int ERR_PARAMS = 10003;
	/** 同名错误 */
	public static final int ERR_SAME_NAME = 10004;
	/** 请求URL错误 */
	public static final int ERR_NO_ACTION = 10005;
	/** 新增成功 */
	public static final int SUCC_ADD = 10006;
	/** 修改成功 */
	public static final int SUCC_UPD = 10007;
	/** 删除成功 */
	public static final int SUCC_DEL = 10008;
	/** 保存成功 */
	public static final int SUCC_SAVE = 10009;
	/** 新增失败 */
	public static final int FAIL_ADD = 10010;
	/** 修改失败 */
	public static final int FAIL_UPD = 10011;
	/** 删除失败 */
	public static final int FAIL_DEL = 10012;
	/** 保存失败 */
	public static final int FAIL_SAVE = 10013;
	/** 操作失败 */
	public static final int FAILURE = 10014;
	/** 存在子项，无法直接删除 */
	public static final int FAIL_HAS_CHILD = 10015;
	/** 商品已有库存，无法直接删除 */
	public static final int FAIL_HAS_STOCK = 10016;
	/** 该类别已绑定相关商品，无法直接删除 */
	public static final int FAIL_HAS_BIND_OLD = 10017;
	/** 该类别已绑定相关商品，无法直接删除 */
	public static final int FAIL_HAS_BIND_NEW = 10018;
	/** 该类别已绑定相关业务，无法直接删除 */
	public static final int FAIL_HAS_BIND_BUSINESS = 10019;
	/** 您还未设置审批通道，请联系管理员 */
	public static final int ERR_NO_CHANNEL = 10020;
	/** 提交成功 */
	public static final int SUCC_SUBMIT = 10021;
	/** 提交失败 */
	public static final int FAIL_SUBMIT = 10022;
	/** 单号重复 */
	public static final int ERR_SAME_NUM = 10023;
	/** 审核成功 */
	public static final int SUCC_APPROVE = 10024;
	/** 审核失败 */
	public static final int FAIL_APPROVE = 10025;
	/** 编码重复 */
	public static final int ERR_SAME_CODE = 10026;
	/** 请先保存采购入库单，再进行串号扫描！ */
	public static final int ERR_NOT_SAME_COUNT = 10027;
	/** 您还未绑定角色，请联系管理员 */
	public static final int ERR_NO_ROLE = 10028;
	/** 付款成功 */
	public static final int SUCC_PAY = 10029;
	/** 付款失败 */
	public static final int FAIL_PAY = 10030;
	/** 取消成功 */
	public static final int SUCC_CANCEL = 10031;
	/** 取消失败 */
	public static final int FAIL_CANCEL = 10032;
	/** 串号输入有误 */
	public static final int ERR_SERIAL_NUM = 10033;
	/** 上传成功 */
	public static final int SUCC_UPLOAD = 10034;
	/** 上传失败 */
	public static final int FAIL_UPLOAD = 10035;
	/** 库存不存在*/
	public static final int STOCK_NOT_EXIST = 10036;
	/** 串号已被销售 */
	public static final int USED_SERIAL_NUM = 10037;
	/** 该单已经提交 */
	public static final int ORDER_SUBMITTED = 10038;
	/** 你已经投票过了 *///TODO添加
	public static final int ALREADYVOTE = 10039;
}
