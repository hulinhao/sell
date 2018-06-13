package com.imooc.utils;

import com.imooc.enums.ResultEnum;
import com.imooc.vo.ProductVo;
import com.imooc.vo.ResultVo;

import java.util.List;
import java.util.Map;

public class ResultVoUtil {

    public static ResultVo success(Object obj){
        ResultVo result = new ResultVo();
        result.setCode(0);
        result.setMsg("成功");
        result.setResult(obj);
        return result;
    }

    public static ResultVo success(){
       return success(null);
    }

    public static ResultVo error(Integer code,String msg){
        ResultVo result = new ResultVo();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ResultVo error(ResultEnum resultEnum){
        ResultVo result = new ResultVo();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }
}
