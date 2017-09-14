package cn.lhzs.common.result;

/**
 * 响应结果生成工具
 */
public final  class ResultGenerator {

    private  ResultGenerator(){}

    /**
     * 返回带httpcode结果
     * @param code httpcode
     * @return 返回给前端结果
     */
    public static Result genHttpCodeResult(HttpCode code) {
        return new Result().setHttpCode(code);
    }

    /**
     * 返回正确结果
     */
    public static Result genSuccessResult() {
        return genHttpCodeResult(HttpCode.OK);
    }

    /**
     * 返回正确结果-
     * @param data 返回给前端的结束数据
     * @return
     */
    public static Result genSuccessResult(Object data) {
        return new Result()
                .setHttpCode(HttpCode.OK)
                .setData(data);
    }

    public static Result genBadReqResult() {
        return  genHttpCodeResult(HttpCode.BAD_REQUEST);
    }

    public static Result genBadReqResult(String msg) {
        return new Result()
                .setHttpCode(HttpCode.FAIL)
                .setMsg(msg);
    }
    /**
     * 返回失败结果，
     * @param responCode
     * @param msg
     * @return
     */
    public static Result genBadReqResult(HttpCode responCode, String msg) {
        return new Result()
                .setHttpCode(responCode)
                .setMsg(msg);
    }
    /**
     * 返回没有权限结果
     */
    public static Result genForbiddenResult() {
        return  genHttpCodeResult(HttpCode.FORBIDDEN);
    }


    /**
     * 返回没有登录结果
     */
    public static Result genUnLoginResult() {
        return  genHttpCodeResult(HttpCode.UNAUTHORIZED);
    }

}
